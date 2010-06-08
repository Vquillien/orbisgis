package org.orbisgis.core.ui.plugins.toc;

import org.orbisgis.core.DataManager;
import org.orbisgis.core.Services;
import org.orbisgis.core.images.IconNames;
import org.orbisgis.core.layerModel.ILayer;
import org.orbisgis.core.layerModel.LayerException;
import org.orbisgis.core.layerModel.MapContext;
import org.orbisgis.core.ui.pluginSystem.AbstractPlugIn;
import org.orbisgis.core.ui.pluginSystem.PlugInContext;
import org.orbisgis.core.ui.pluginSystem.PlugInContext.SelectionAvailability;
import org.orbisgis.core.ui.pluginSystem.PlugInContext.LayerAvailability;
import org.orbisgis.core.ui.pluginSystem.workbench.Names;
import org.orbisgis.core.ui.pluginSystem.workbench.WorkbenchContext;
import org.orbisgis.core.ui.pluginSystem.workbench.WorkbenchFrame;

public class GroupLayersPlugIn extends AbstractPlugIn {

	public boolean execute(PlugInContext context) throws Exception {

		MapContext mapContext = getPlugInContext().getMapContext();
		ILayer[] layers = mapContext.getSelectedLayers();

		DataManager dataManager = (DataManager) Services
				.getService(DataManager.class);
		ILayer col = dataManager.createLayerCollection("group"
				+ System.currentTimeMillis());
		ILayer parent = layers[0].getParent();
		try {
			parent.addLayer(col);
			for (ILayer layer : layers) {
				layer.moveTo(col);
			}
		} catch (LayerException e) {
			throw new RuntimeException("bug!");
		}

		return true;
	}

	public void initialize(PlugInContext context) throws Exception {
		WorkbenchContext wbContext = context.getWorkbenchContext();
		WorkbenchFrame frame = wbContext.getWorkbench().getFrame().getToc();
		context.getFeatureInstaller().addPopupMenuItem(frame, this,
				new String[] { Names.POPUP_TOC_LAYERS_GROUP_PATH1 },
				Names.POPUP_TOC_LAYERS_GROUP_GROUP, false,
				getIcon(IconNames.POPUP_TOC_LAYERS_GROUP_ICON), wbContext);
	}

	public boolean isEnabled() {
		MapContext mapContext = getPlugInContext().getMapContext();
		ILayer[] layers = null;
		if (mapContext != null)
			layers = mapContext.getSelectedLayers();
		if(layers==null) return false;
		for (int i = 0; i < layers.length - 1; i++) {
			if (!layers[i].getParent().equals(layers[i + 1].getParent())) {
				return false;
			}
		}
		return getPlugInContext().checkLayerAvailability(
				new SelectionAvailability[]{ SelectionAvailability.SUPERIOR , SelectionAvailability.ACTIVE_MAPCONTEXT},
				1,
				new LayerAvailability[]{});
	}
}
