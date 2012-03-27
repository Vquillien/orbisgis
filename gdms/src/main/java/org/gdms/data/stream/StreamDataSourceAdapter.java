/**
 * The GDMS library (Generic Datasource Management System)
 * is a middleware dedicated to the management of various kinds of
 * data-sources such as spatial vectorial data or alphanumeric. Based
 * on the JTS library and conform to the OGC simple feature access
 * specifications, it provides a complete and robust API to manipulate
 * in a SQL way remote DBMS (PostgreSQL, H2...) or flat files (.shp,
 * .csv...).
 *
 * Gdms is distributed under GPL 3 license. It is produced by the "Atelier SIG"
 * team of the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 *
 * Copyright (C) 2007-2012 IRSTV FR CNRS 2488
 *
 * This file is part of Gdms.
 *
 * Gdms is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Gdms is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Gdms. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 *
 * or contact directly:
 * info@orbisgis.org
 */
package org.gdms.data.stream;

import java.util.List;

import org.apache.log4j.Logger;
import org.orbisgis.progress.NullProgressMonitor;

import org.gdms.data.DataSource;
import org.gdms.data.DriverDataSource;
import org.gdms.data.edition.Commiter;
import org.gdms.data.edition.DeleteEditionInfo;
import org.gdms.data.edition.EditionInfo;
import org.gdms.data.edition.PhysicalRowAddress;
import org.gdms.driver.*;
import org.gdms.source.CommitListener;
import org.gdms.source.DefaultSourceManager;
import org.gdms.source.Source;

/**
 * Adapter to the DataSource interface for stream drivers
 * 
 * @author Vincent Dépériers
 */
public class StreamDataSourceAdapter extends DriverDataSource implements Commiter, CommitListener {

    private StreamDriver driver;
    private StreamSource def;
    private static final Logger LOG = Logger.getLogger(StreamDataSourceAdapter.class);

    /**
     * Creates a new StreamDataSourceAdapter
     *
     *
     * @param src
     * @param def
     * @param driver
     */
    public StreamDataSourceAdapter(Source src, StreamSource def, StreamDriver driver) {
        super(src);
        this.def = def;
        this.driver = driver;
        LOG.trace("Constructor");
    }

    @Override
    public void open() throws DriverException {
        LOG.trace("Opening");
        //Il y aura des arguments pour open dont on aura la valeur grace à def :)
        driver.open(def);
        fireOpen(this);
        DefaultSourceManager sm = (DefaultSourceManager) getDataSourceFactory().getSourceManager();
        sm.addCommitListener(this);
    }

    @Override
    public void close() throws DriverException {
        LOG.trace("Closing");
        driver.close();
        fireCancel(this);
        DefaultSourceManager sm = (DefaultSourceManager) getDataSourceFactory().getSourceManager();
        sm.removeCommitListener(this);
    }

    @Override
    public void saveData(DataSet ds) throws DriverException {
        LOG.trace("Saving Data");
        ((StreamReadWriteDriver) driver).write(ds, new NullProgressMonitor());
    }

    @Override
    public Driver getDriver() {
        return driver;
    }

    @Override
    public void syncWithSource() throws DriverException {
        sync();
    }

    @Override
    public void commitDone(String name) throws DriverException {
        sync();
    }

    private void sync() throws DriverException {
        driver.close();
        //Faire ce dont on a besoin en parametre de close et open cf(DBTableDataSourceAdapter)
        driver.open(def);

    }

    @Override
    public boolean commit(List<PhysicalRowAddress> rowsDirections, String[] fieldNames, List<EditionInfo> schemaActions, List<EditionInfo> editionActions, List<DeleteEditionInfo> deletedPKs, DataSource modifiedSource) throws DriverException {
        LOG.trace("Commiting");
        boolean changed = ((StreamReadWriteDriver) driver).write(modifiedSource, new NullProgressMonitor());
        try {
            driver.close();
        } catch (DriverException e) {
            throw new DriverException("Cannot free resources: stream writen ...", e);
        }
        driver.open(def);

	fireCommit(this);
        
        return changed;
    }

    @Override
    public void isCommiting(String name, Object source) throws DriverException {
    }
}
