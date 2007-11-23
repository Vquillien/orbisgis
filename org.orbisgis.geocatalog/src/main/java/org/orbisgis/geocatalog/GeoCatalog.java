package org.orbisgis.geocatalog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import org.orbisgis.core.IWindow;
import org.orbisgis.core.actions.EPActionHelper;
import org.orbisgis.core.actions.IAction;
import org.orbisgis.core.actions.IActionFactory;
import org.orbisgis.core.actions.ISelectableAction;
import org.orbisgis.core.actions.MenuTree;
import org.orbisgis.core.actions.ToolBarArray;
import org.orbisgis.geocatalog.resources.EPResourceWizardHelper;

/**
 * Graphical interface for the Geo Catalog This file mainly contains user
 * interface stuff
 *
 * @author Samuel Chemla
 * @version beta1
 */

public class GeoCatalog implements IWindow {

	/**
	 * The frame is made of a vertical BoxLayout, which contains : 1-a menu bar
	 * 2-a tool bar 3-a scroll pane with a grid layout inside with a tree inside
	 */

	// Let you set the size of the frame
	private final Dimension FrameSize = new Dimension(250, 640);

	// The frame containing everything.
	private JFrame jFrame = null;

	private static Catalog myCatalog = null; // See Catalog.java

	public GeoCatalog() {

		jFrame = new JFrame();

		// be instantied now or the listener won't work...
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jFrame.setSize(FrameSize);
		jFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				EPGeocatalogActionHelper.executeAction(myCatalog,
						"org.orbisgis.geocatalog.Exit");
			}

		});

		java.net.URL url = this.getClass().getResource("mini_orbisgis.png");
		jFrame.setIconImage(new ImageIcon(url).getImage());

		jFrame.setTitle("OrbisGIS : GeoCatalog");
		JMenuBar menuBar = new JMenuBar();
		JToolBar toolBar = new JToolBar();
		myCatalog = new Catalog();
		MenuTree menuTree = new MenuTree();
		ToolBarArray toolBarArray = new ToolBarArray();
		GeocatalogActionFactory actionFactory = new GeocatalogActionFactory();
		EPActionHelper.configureParentMenusAndToolBars(
				"org.orbisgis.geocatalog.Action", menuTree, toolBarArray);
		EPActionHelper.configureMenuAndToolBar(
				"org.orbisgis.geocatalog.Action", actionFactory, menuTree,
				toolBarArray);
		EPResourceWizardHelper.addWizardMenus(menuTree,
				new ResourceWizardActionFactory(myCatalog));
		JComponent[] menus = menuTree.getJMenus();
		for (int i = 0; i < menus.length; i++) {
			menuBar.add(menus[i]);
		}
		for (JToolBar toolbar : toolBarArray.getToolBars()) {
			toolBar.add(toolbar);
		}

		jFrame.setJMenuBar(menuBar); // Add the menu bar
		jFrame.getContentPane().setLayout(new BorderLayout());
		jFrame.getContentPane().add(toolBar, BorderLayout.PAGE_START);

		jFrame.getContentPane().add(myCatalog, BorderLayout.CENTER);

		jFrame.setVisible(true);
	}

	/** Restore and show the GeoCatalog */
	public void show() {
		jFrame.setExtendedState(JFrame.NORMAL);
		jFrame.toFront();
	}

	public void showWindow() {
		show();
	}

	public Catalog getCatalog() {
		return myCatalog;
	}

	private final class GeocatalogActionFactory implements IActionFactory {

		public IAction getAction(Object action) {
			return new IGeocatalogActionDecorator(action);
		}

		public ISelectableAction getSelectableAction(Object action) {
			return new IGeocatalogActionDecorator(action);
		}
	}

	private final class IGeocatalogActionDecorator implements IAction, ISelectableAction {

		private IGeocatalogAction action;

		public IGeocatalogActionDecorator(Object action) {
			this.action = (IGeocatalogAction) action;
		}

		public boolean isVisible() {
			return action.isVisible(GeoCatalog.this);
		}

		public boolean isEnabled() {
			return action.isEnabled(GeoCatalog.this);
		}

		public void actionPerformed() {
			action.actionPerformed(myCatalog);
		}

		public boolean isSelected() {
			return ((IGeocatalogSelectableAction) action).isSelected(myCatalog);
		}
	}
}