/**
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information.
 *
 * OrbisGIS is distributed under GPL 3 license. It is produced by the "Atelier SIG"
 * team of the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 *
 * Copyright (C) 2007-2014 IRSTV (FR CNRS 2488)
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisGIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.coremap.renderer;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.h2gis.utilities.SFSUtilities;
import org.h2gis.utilities.SpatialResultSet;
import org.h2gis.utilities.TableLocation;
import org.orbisgis.corejdbc.MetaData;
import org.orbisgis.coremap.layerModel.ILayer;
import org.orbisgis.commons.progress.ProgressMonitor;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

import javax.sql.DataSource;
import java.beans.EventHandler;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Standard select * from mytable query without cache or index.
 * @author Nicolas Fortin
 */
@Component
public class DefaultResultSetProviderFactory implements ResultSetProviderFactory {
    private static final int FETCH_SIZE = 300;
    private static final I18n I18N = I18nFactory.getI18n(DefaultResultSetProviderFactory.class);

    @Override
    public DefaultResultSetProvider getResultSetProvider(ILayer layer,String[] extraFields, ProgressMonitor pm) {
        return new DefaultResultSetProvider(layer.getDataManager().getDataSource(),extraFields, layer);
    }

    @Override
    public String getName() {
        return "Remote index";
    }

    public static class DefaultResultSetProvider implements ResultSetProvider {
        private DataSource dataSource;
        private ILayer layer;

        private Connection connection;
        private PreparedStatement st;
        private PropertyChangeListener cancelListener;
        private ProgressMonitor pm;
        private static final Logger LOGGER = LoggerFactory.getLogger(DefaultResultSetProvider.class);
        private String pkName = "";
        private final String[] extraFields;

        private DefaultResultSetProvider(DataSource dataSource,String[] extraFields, ILayer layer) {
            this.dataSource = dataSource;
            this.layer = layer;
            this.extraFields = extraFields;
            try(Connection conn = dataSource.getConnection()) {
                pkName = MetaData.getPkName(conn, layer.getTableReference(), true);
            } catch (SQLException ex) {
                LOGGER.error(ex.getLocalizedMessage(), ex);
            }
        }

        @Override
        public String getPkName() {
            return pkName;
        }

        @Override
        public SpatialResultSet execute(ProgressMonitor pm, Envelope extent) throws SQLException {
            this.pm = pm;
            connection = dataSource.getConnection();
            st = createStatement(connection);
            st.setFetchSize(FETCH_SIZE);
            st.setFetchDirection(ResultSet.FETCH_FORWARD);
            connection.setAutoCommit(false);
            cancelListener = EventHandler.create(PropertyChangeListener.class, st, "cancel");
            pm.addPropertyChangeListener(ProgressMonitor.PROP_CANCEL, cancelListener);
            GeometryFactory geometryFactory = new GeometryFactory();
            if(st.getParameterMetaData().getParameterCount() > 0) {
                st.setObject(1, geometryFactory.toGeometry(extent)); // Filter geometry by envelope
            }
            return st.executeQuery().unwrap(SpatialResultSet.class);
        }

        public String getQuery(Connection connection, boolean envelopeFilter) throws SQLException {
            List<String> geometryFields = SFSUtilities.getGeometryFields(connection, TableLocation.parse(layer.getTableReference()));
            List<String> rasterFields =SFSUtilities.getRasterFields(connection, TableLocation.parse(layer
                    .getTableReference()));
            if(geometryFields.isEmpty() && rasterFields.isEmpty()) {
                throw new SQLException(I18N.tr("Table {0} does not contains geometry fields",layer.getTableReference()));
            }
            String geometryField = !geometryFields.isEmpty() ? geometryFields.get(0) : rasterFields.get(0);
            String tableReference = layer.getTableReference();
            StringBuilder computedField = new StringBuilder("select " + pkName + ",*");
            if(extraFields.length != 0) {
                for (String extraField : extraFields) {
                    computedField.append(",");
                    computedField.append(extraField);
                }
            }
            computedField.append(" from ");
            computedField.append(tableReference);
            if(envelopeFilter) {
                computedField.append(" where ");
                computedField.append(geometryField);
                computedField.append(" && ?");
            }
            return computedField.toString();
        }


        private PreparedStatement createStatement(Connection connection) throws SQLException {
                return connection.prepareStatement(getQuery(connection, true),
                        ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        }

        @Override
        public void close() throws SQLException {
            if(cancelListener != null) {
                pm.removePropertyChangeListener(cancelListener);
            }
            if(st != null) {
                st.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
    }
}
