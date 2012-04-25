/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information. OrbisGIS is
 * distributed under GPL 3 license. It is produced by the "Atelier SIG" team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/> CNRS FR 2488.
 *
 *
 * Team leader : Erwan BOCHER, scientific researcher,
 *
 * User support leader : Gwendall Petit, geomatic engineer.
 *
 * Previous computer developer : Pierre-Yves FADET, computer engineer, Thomas LEDUC, 
 * scientific researcher, Fernando GONZALEZ CORTES, computer engineer.
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * Copyright (C) 2010 Erwan BOCHER, Alexis GUEGANNO, Maxence LAURENT, Antoine GOURLAY
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
 *
 * or contact directly:
 * info@orbisgis.org
 */
package org.gdms.sql.function.spatial.geometry.properties;

import org.gdms.data.DataSourceFactory;
import org.gdms.data.types.Type;
import org.gdms.data.types.TypeFactory;
import org.gdms.data.values.Value;
import org.gdms.data.values.ValueFactory;
import org.gdms.sql.function.FunctionException;

import com.vividsolutions.jts.geom.Geometry;
import org.gdms.geometryUtils.GeometryTypeUtil;

/**
 * Calculate the compacity of each building's geometry compared
 * to the circle (the one that as the area of the building)
 */
public final class ST_CircleCompacity extends AbstractSpatialPropertyFunction {

        private static final double DPI = 2 * Math.PI;

        @Override
        public Value evaluateResult(DataSourceFactory dsf, Value[] args) throws FunctionException {
                final Geometry geomBuild = args[0].getAsGeometry();
                if (GeometryTypeUtil.isPolygon(geomBuild)) {
                        final double sBuild = geomBuild.getArea();
                        final double pBuild = geomBuild.getLength();
                        final double correspondingCircleRadius = Math.sqrt(sBuild / Math.PI);
                        final double pCircle = DPI * correspondingCircleRadius;
                        return ValueFactory.createValue(pCircle / pBuild);
                }
                return ValueFactory.createNullValue();
        }

        @Override
        public String getDescription() {
                return "Calculate the compacity of a polygon compared "
                        + "to the circle with the same area. If the geometry is not a polygon, returns a NULL value.";
        }

        @Override
        public String getName() {
                return "ST_CircleCompacity";
        }

        @Override
        public Type getType(Type[] argsTypes) {
                return TypeFactory.createType(Type.DOUBLE);
        }

        @Override
        public String getSqlOrder() {
                return "select ST_CircleCompacity(the_geom) from polygonTable;";
        }
}