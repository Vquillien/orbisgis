/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able
 * to manipulate and create vector and raster spatial information. OrbisGIS
 * is distributed under GPL 3 license. It is produced  by the geo-informatic team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/>, CNRS FR 2488:
 *    Erwan BOCHER, scientific researcher,
 *    Thomas LEDUC, scientific researcher,
 *    Fernando GONZALEZ CORTES, computer engineer.
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OrbisGIS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult:
 *    <http://orbisgis.cerma.archi.fr/>
 *    <http://sourcesup.cru.fr/projects/orbisgis/>
 *
 * or contact directly:
 *    erwan.bocher _at_ ec-nantes.fr
 *    fergonco _at_ gmail.com
 *    thomas.leduc _at_ cerma.archi.fr
 */
package org.contrib.algorithm.jgrapht;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * This interface is implemented by every object used as a Node in a graph
 * composed with JUMP features or JTS geometries.
 * INodes may correspond to features in a feature collection (ex. crossroads) or
 * to feature boundaries (ex. start point and end point of a linear road
 * segment).<br>
 * 0.1 (2006-06-01) : initial release<br>
 * 0.2 (2007-04-20) : add the static geometryFactory used in implementation classes<br>
 * @author Michael Michaud
 * @version 0.2 (2007-04-20)
 */
public interface INode {
    
    public static final GeometryFactory DEFAULT_GEOMETRY_FACTORY = new GeometryFactory();
    
   /**
    * Return the coordinate of this Node.
    */
    public Coordinate getCoordinate();

   /**
    * Return a Geometry representing this Node.
    */
    public Geometry getGeometry();

}

