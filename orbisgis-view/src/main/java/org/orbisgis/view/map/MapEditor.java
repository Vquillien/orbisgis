/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information. OrbisGIS is
 * distributed under GPL 3 license. It is produced by the "Atelier SIG" team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/> CNRS FR 2488.
 * 
 *
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
 * info _at_ orbisgis.org
 */
package org.orbisgis.view.map;

import java.awt.Component;
import java.beans.EventHandler;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import org.orbisgis.utils.I18N;
import org.orbisgis.view.docking.DockingPanel;
import org.orbisgis.view.docking.DockingPanelParameters;
import org.orbisgis.view.icons.OrbisGISIcon;

/**
 * @brief The Map Editor Panel
 */
public class MapEditor extends JPanel implements DockingPanel   {
        DockingPanelParameters dockingPanelParameters;
    
    /**
     * Constructor
     */
    public MapEditor() {
        dockingPanelParameters = new DockingPanelParameters();
        dockingPanelParameters.setDockingArea("toc_map");
        dockingPanelParameters.setTitle(I18N.getString("orbisgis.view.map.MapEditorTitle"));
        dockingPanelParameters.setTitleIcon(OrbisGISIcon.getIcon("map"));
    }
    /**
     * Must be called after construction of mapeditor, 
     * and before showing the element
     */
    public void initListeners() {
        //Set a localized name and a specific icon to the toc_map docking area
        dockingPanelParameters.addPropertyChangeListener(
                DockingPanelParameters.PROP_DOCKINGAREAPARAMETERS,
                EventHandler.create(PropertyChangeListener.class,this,
                "onDockingAreaParametersAvaible"));        
    }
    /**
     * Give information on the behaviour of this panel related to the current
     * docking system
     * @return The panel parameter instance
     */
    public DockingPanelParameters getDockingParameters() {
        return dockingPanelParameters;
    }

    /**
     * When the docking area is ready, this method set the area title name
     */
    public void onDockingAreaParametersAvaible() {
        dockingPanelParameters.getDockingAreaParameters().setAreaTitle(
                I18N.getString("orbisgis.view.map.TocAndMapAreaTitle"));
    }
    
    /**
     * Return the content of the view.
     * @return An awt content to show in this panel
     */
    public Component getComponent() {
        return this;
    }
}
