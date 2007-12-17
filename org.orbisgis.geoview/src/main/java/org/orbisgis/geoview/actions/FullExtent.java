package org.orbisgis.geoview.actions;

import org.orbisgis.geoview.GeoView2D;
import org.orbisgis.geoview.IGeoviewAction;
import org.orbisgis.geoview.MapControl;

public class FullExtent implements IGeoviewAction {

	public void actionPerformed(GeoView2D geoview) {
		MapControl mc = geoview.getMap();
		mc.setExtent(geoview.getViewContext().getRootLayer().getEnvelope());
	}

	public boolean isEnabled(GeoView2D geoView2D) {
		return geoView2D.getViewContext().getRootLayer().getLayerCount() > 0;
	}

	public boolean isVisible(GeoView2D geoView2D) {
		return true;
	}

}
