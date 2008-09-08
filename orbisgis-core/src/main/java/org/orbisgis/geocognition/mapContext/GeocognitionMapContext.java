package org.orbisgis.geocognition.mapContext;

import org.orbisgis.geocognition.AbstractExtensionElement;
import org.orbisgis.geocognition.GeocognitionElementContentListener;
import org.orbisgis.geocognition.GeocognitionElementFactory;
import org.orbisgis.geocognition.GeocognitionExtensionElement;
import org.orbisgis.layerModel.DefaultMapContext;
import org.orbisgis.layerModel.ILayer;
import org.orbisgis.layerModel.LayerCollectionEvent;
import org.orbisgis.layerModel.LayerException;
import org.orbisgis.layerModel.LayerListener;
import org.orbisgis.layerModel.LayerListenerEvent;
import org.orbisgis.layerModel.MapContext;
import org.orbisgis.layerModel.MapContextListener;
import org.orbisgis.layerModel.SelectionEvent;
import org.orbisgis.progress.IProgressMonitor;
import org.orbisgis.progress.NullProgressMonitor;

public class GeocognitionMapContext extends AbstractExtensionElement implements
		GeocognitionExtensionElement {

	private MapContext mapContext;
	private Object revertStatus;
	private ChangeListener changeListener;
	private GeocognitionElementContentListener elementListener;

	public GeocognitionMapContext(MapContext map,
			GeocognitionElementFactory factory) {
		super(factory);
		this.mapContext = map;
	}

	public GeocognitionMapContext(Object xmlObject,
			GeocognitionElementFactory factory) {
		super(factory);
		mapContext = new DefaultMapContext();
		mapContext.setJAXBObject(xmlObject);
		revertStatus = xmlObject;
	}

	@Override
	public Object getJAXBObject() {
		return mapContext.getJAXBObject();
	}

	@Override
	public Object getObject() throws UnsupportedOperationException {
		return mapContext;
	}

	@Override
	public String getTypeId() {
		return GeocognitionMapContextFactory.ID;
	}

	@Override
	public void close(IProgressMonitor progressMonitor)
			throws UnsupportedOperationException {
		mapContext.removeMapContextListener(changeListener);
		mapContext.getLayerModel().removeLayerListenerRecursively(
				changeListener);
		mapContext.close(progressMonitor);
		mapContext.setJAXBObject(revertStatus);
	}

	@Override
	public void open(IProgressMonitor progressMonitor)
			throws UnsupportedOperationException, GeocognitionException {
		try {
			if (revertStatus == null) {
				revertStatus = mapContext.getJAXBObject();
			}

			mapContext.open(progressMonitor);
			changeListener = new ChangeListener();
			mapContext.addMapContextListener(changeListener);
			mapContext.getLayerModel().addLayerListenerRecursively(
					changeListener);
		} catch (LayerException e) {
			throw new GeocognitionException("Cannot open map", e);
		}
	}

	@Override
	public void save() throws UnsupportedOperationException {
		revertStatus = mapContext.getJAXBObject();
	}

	public boolean isModified() {
		ILayer[] layers = mapContext.getLayerModel().getLayersRecursively();
		for (ILayer layer : layers) {
			if (layer.getDataSource().isModified()) {
				return true;
			}
		}

		return false;
	}

	private final class ChangeListener implements MapContextListener,
			LayerListener {
		@Override
		public void layerSelectionChanged(MapContext mapContext) {
			fireContentChanged();
		}

		@Override
		public void activeLayerChanged(ILayer previousActiveLayer,
				MapContext mapContext) {
			fireContentChanged();
		}

		@Override
		public void layerAdded(final LayerCollectionEvent e) {
			for (final ILayer layer : e.getAffected()) {
				layer.addLayerListenerRecursively(changeListener);
			}
			fireContentChanged();
		}

		@Override
		public void layerMoved(LayerCollectionEvent e) {
			fireContentChanged();
		}

		@Override
		public void layerRemoved(LayerCollectionEvent e) {
			for (final ILayer layer : e.getAffected()) {
				layer.removeLayerListenerRecursively(changeListener);
			}
			fireContentChanged();
		}

		@Override
		public void nameChanged(LayerListenerEvent e) {
			fireContentChanged();
		}

		@Override
		public void selectionChanged(SelectionEvent e) {
			fireContentChanged();
		}

		@Override
		public void styleChanged(LayerListenerEvent e) {
			fireContentChanged();
		}

		@Override
		public void visibilityChanged(LayerListenerEvent e) {
			fireContentChanged();
		}

	}

	private void fireContentChanged() {
		this.elementListener.contentChanged();
	}

	@Override
	public Object getRevertJAXBObject() {
		return revertStatus;
	}

	@Override
	public void setElementListener(GeocognitionElementContentListener listener) {
		this.elementListener = listener;
	}

	@Override
	public void setJAXBObject(Object jaxbObject) throws GeocognitionException {
		// Set the content
		boolean isOpen = mapContext.isOpen();
		if (isOpen) {
			mapContext.close(new NullProgressMonitor());
		}
		Object previousJAXBObject = mapContext.getJAXBObject();
		mapContext.setJAXBObject(jaxbObject);
		if (isOpen) {
			try {
				mapContext.open(null);
			} catch (IllegalStateException e) {
				mapContext.setJAXBObject(previousJAXBObject);
				throw new GeocognitionException("Cannot reopen map", e);
			} catch (LayerException e) {
				mapContext.setJAXBObject(previousJAXBObject);
				throw new GeocognitionException("Cannot reopen map", e);
			}
		}
	}
}
