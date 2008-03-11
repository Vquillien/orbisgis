
package org.orbisgis.tools.instances.generated;

import java.awt.Graphics;
import java.net.URL;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.orbisgis.tools.Automaton;
import org.orbisgis.tools.DrawingException;
import org.orbisgis.tools.ViewContext;
import org.orbisgis.tools.FinishedAutomatonException;
import org.orbisgis.tools.NoSuchTransitionException;
import org.orbisgis.tools.ToolManager;
import org.orbisgis.tools.TransitionException;

public abstract class Selection implements Automaton {

	private static Logger logger = Logger.getLogger(Selection.class.getName());

	private String status = "Standby";

	private ViewContext ec;

	private ToolManager tm;

	public String[] getTransitionLabels() {
		ArrayList<String> ret = new ArrayList<String>();
		
		if ("Standby".equals(status)) {
			
		}
		
		if ("OnePoint".equals(status)) {
			
		}
		
		if ("OnePointLeft".equals(status)) {
			
		}
		
		if ("TwoPoints".equals(status)) {
			
		}
		
		if ("Selection".equals(status)) {
			
		}
		
		if ("PointWithSelection".equals(status)) {
			
		}
		
		if ("Movement".equals(status)) {
			
		}
		
		if ("MakeMove".equals(status)) {
			
		}
		
			ret.add(Messages.getString("cancel"));
			

		return ret.toArray(new String[0]);
	}

	public String[] getTransitionCodes() {
		ArrayList<String> ret = new ArrayList<String>();
		
		if ("Standby".equals(status)) {
			
		}
		
		if ("OnePoint".equals(status)) {
			
		}
		
		if ("OnePointLeft".equals(status)) {
			
		}
		
		if ("TwoPoints".equals(status)) {
			
		}
		
		if ("Selection".equals(status)) {
			
		}
		
		if ("PointWithSelection".equals(status)) {
			
		}
		
		if ("Movement".equals(status)) {
			
		}
		
		if ("MakeMove".equals(status)) {
			
		}
		
			ret.add("esc");
			

		return ret.toArray(new String[0]);
	}

	public void init(ViewContext ec, ToolManager tm) throws TransitionException, FinishedAutomatonException {
		logger.info("status: " + status);
		this.ec = ec;
		this.tm = tm;
		status = "Standby";
		transitionTo_Standby(ec, tm);
		if (isFinished(status)){
			throw new FinishedAutomatonException();
		}
	}

	public void transition(String code) throws NoSuchTransitionException, TransitionException, FinishedAutomatonException {
		logger.info("transition code: " + code);

		
		if ("Standby".equals(status)) {
			
			if ("point".equals(code)) {
				String preStatus = status;
				try {
					status = "OnePoint";
					logger.info("status: " + status);
					double[] v = tm.getValues();
					for (int i = 0; i < v.length; i++) {
						logger.info("value: " + v[i]);
					}
					transitionTo_OnePoint(ec, tm);
					if (isFinished(status)){
						throw new FinishedAutomatonException();
					}
					return;
				} catch (TransitionException e) {
					status = preStatus;
					throw e;
				}
			}
			
		}
		
		if ("OnePoint".equals(status)) {
			
			if ("selection".equals(code)) {
				String preStatus = status;
				try {
					status = "Selection";
					logger.info("status: " + status);
					double[] v = tm.getValues();
					for (int i = 0; i < v.length; i++) {
						logger.info("value: " + v[i]);
					}
					transitionTo_Selection(ec, tm);
					if (isFinished(status)){
						throw new FinishedAutomatonException();
					}
					return;
				} catch (TransitionException e) {
					status = preStatus;
					throw e;
				}
			}
			
			if ("no-selection".equals(code)) {
				String preStatus = status;
				try {
					status = "OnePointLeft";
					logger.info("status: " + status);
					double[] v = tm.getValues();
					for (int i = 0; i < v.length; i++) {
						logger.info("value: " + v[i]);
					}
					transitionTo_OnePointLeft(ec, tm);
					if (isFinished(status)){
						throw new FinishedAutomatonException();
					}
					return;
				} catch (TransitionException e) {
					status = preStatus;
					throw e;
				}
			}
			
			if ("init".equals(code)) {
				String preStatus = status;
				try {
					status = "Standby";
					logger.info("status: " + status);
					double[] v = tm.getValues();
					for (int i = 0; i < v.length; i++) {
						logger.info("value: " + v[i]);
					}
					transitionTo_Standby(ec, tm);
					if (isFinished(status)){
						throw new FinishedAutomatonException();
					}
					return;
				} catch (TransitionException e) {
					status = preStatus;
					throw e;
				}
			}
			
		}
		
		if ("OnePointLeft".equals(status)) {
			
			if ("point".equals(code)) {
				String preStatus = status;
				try {
					status = "TwoPoints";
					logger.info("status: " + status);
					double[] v = tm.getValues();
					for (int i = 0; i < v.length; i++) {
						logger.info("value: " + v[i]);
					}
					transitionTo_TwoPoints(ec, tm);
					if (isFinished(status)){
						throw new FinishedAutomatonException();
					}
					return;
				} catch (TransitionException e) {
					status = preStatus;
					throw e;
				}
			}
			
		}
		
		if ("TwoPoints".equals(status)) {
			
			if ("selection".equals(code)) {
				String preStatus = status;
				try {
					status = "Selection";
					logger.info("status: " + status);
					double[] v = tm.getValues();
					for (int i = 0; i < v.length; i++) {
						logger.info("value: " + v[i]);
					}
					transitionTo_Selection(ec, tm);
					if (isFinished(status)){
						throw new FinishedAutomatonException();
					}
					return;
				} catch (TransitionException e) {
					status = preStatus;
					throw e;
				}
			}
			
			if ("no-selection".equals(code)) {
				String preStatus = status;
				try {
					status = "Standby";
					logger.info("status: " + status);
					double[] v = tm.getValues();
					for (int i = 0; i < v.length; i++) {
						logger.info("value: " + v[i]);
					}
					transitionTo_Standby(ec, tm);
					if (isFinished(status)){
						throw new FinishedAutomatonException();
					}
					return;
				} catch (TransitionException e) {
					status = preStatus;
					throw e;
				}
			}
			
		}
		
		if ("Selection".equals(status)) {
			
			if ("point".equals(code)) {
				String preStatus = status;
				try {
					status = "PointWithSelection";
					logger.info("status: " + status);
					double[] v = tm.getValues();
					for (int i = 0; i < v.length; i++) {
						logger.info("value: " + v[i]);
					}
					transitionTo_PointWithSelection(ec, tm);
					if (isFinished(status)){
						throw new FinishedAutomatonException();
					}
					return;
				} catch (TransitionException e) {
					status = preStatus;
					throw e;
				}
			}
			
		}
		
		if ("PointWithSelection".equals(status)) {
			
			if ("in-handler".equals(code)) {
				String preStatus = status;
				try {
					status = "Movement";
					logger.info("status: " + status);
					double[] v = tm.getValues();
					for (int i = 0; i < v.length; i++) {
						logger.info("value: " + v[i]);
					}
					transitionTo_Movement(ec, tm);
					if (isFinished(status)){
						throw new FinishedAutomatonException();
					}
					return;
				} catch (TransitionException e) {
					status = preStatus;
					throw e;
				}
			}
			
			if ("out-handler".equals(code)) {
				String preStatus = status;
				try {
					status = "OnePoint";
					logger.info("status: " + status);
					double[] v = tm.getValues();
					for (int i = 0; i < v.length; i++) {
						logger.info("value: " + v[i]);
					}
					transitionTo_OnePoint(ec, tm);
					if (isFinished(status)){
						throw new FinishedAutomatonException();
					}
					return;
				} catch (TransitionException e) {
					status = preStatus;
					throw e;
				}
			}
			
		}
		
		if ("Movement".equals(status)) {
			
			if ("point".equals(code)) {
				String preStatus = status;
				try {
					status = "MakeMove";
					logger.info("status: " + status);
					double[] v = tm.getValues();
					for (int i = 0; i < v.length; i++) {
						logger.info("value: " + v[i]);
					}
					transitionTo_MakeMove(ec, tm);
					if (isFinished(status)){
						throw new FinishedAutomatonException();
					}
					return;
				} catch (TransitionException e) {
					status = preStatus;
					throw e;
				}
			}
			
		}
		
		if ("MakeMove".equals(status)) {
			
			if ("empty".equals(code)) {
				String preStatus = status;
				try {
					status = "Selection";
					logger.info("status: " + status);
					double[] v = tm.getValues();
					for (int i = 0; i < v.length; i++) {
						logger.info("value: " + v[i]);
					}
					transitionTo_Selection(ec, tm);
					if (isFinished(status)){
						throw new FinishedAutomatonException();
					}
					return;
				} catch (TransitionException e) {
					status = preStatus;
					throw e;
				}
			}
			
		}
		
		if ("esc".equals(code)) {
			status = "Standby";
			transitionTo_Standby(ec, tm);
			if (isFinished(status)){
				throw new FinishedAutomatonException();
			}
			return;
		}
		

		throw new NoSuchTransitionException(code);
	}

	public boolean isFinished(String status) {

		
		if ("Standby".equals(status)) {
			
				return false;
			
		}
		
		if ("OnePoint".equals(status)) {
			
				return false;
			
		}
		
		if ("OnePointLeft".equals(status)) {
			
				return false;
			
		}
		
		if ("TwoPoints".equals(status)) {
			
				return false;
			
		}
		
		if ("Selection".equals(status)) {
			
				return false;
			
		}
		
		if ("PointWithSelection".equals(status)) {
			
				return false;
			
		}
		
		if ("Movement".equals(status)) {
			
				return false;
			
		}
		
		if ("MakeMove".equals(status)) {
			
				return false;
			
		}
		

		throw new RuntimeException("Invalid status: " + status);
	}


	public void draw(Graphics g) throws DrawingException {
		
		if ("Standby".equals(status)) {
			drawIn_Standby(g, ec, tm);
		}
		
		if ("OnePoint".equals(status)) {
			drawIn_OnePoint(g, ec, tm);
		}
		
		if ("OnePointLeft".equals(status)) {
			drawIn_OnePointLeft(g, ec, tm);
		}
		
		if ("TwoPoints".equals(status)) {
			drawIn_TwoPoints(g, ec, tm);
		}
		
		if ("Selection".equals(status)) {
			drawIn_Selection(g, ec, tm);
		}
		
		if ("PointWithSelection".equals(status)) {
			drawIn_PointWithSelection(g, ec, tm);
		}
		
		if ("Movement".equals(status)) {
			drawIn_Movement(g, ec, tm);
		}
		
		if ("MakeMove".equals(status)) {
			drawIn_MakeMove(g, ec, tm);
		}
		
	}

	
	public abstract void transitionTo_Standby(ViewContext vc, ToolManager tm) throws FinishedAutomatonException, TransitionException;
	public abstract void drawIn_Standby(Graphics g, ViewContext vc, ToolManager tm) throws DrawingException;
	
	public abstract void transitionTo_OnePoint(ViewContext vc, ToolManager tm) throws FinishedAutomatonException, TransitionException;
	public abstract void drawIn_OnePoint(Graphics g, ViewContext vc, ToolManager tm) throws DrawingException;
	
	public abstract void transitionTo_OnePointLeft(ViewContext vc, ToolManager tm) throws FinishedAutomatonException, TransitionException;
	public abstract void drawIn_OnePointLeft(Graphics g, ViewContext vc, ToolManager tm) throws DrawingException;
	
	public abstract void transitionTo_TwoPoints(ViewContext vc, ToolManager tm) throws FinishedAutomatonException, TransitionException;
	public abstract void drawIn_TwoPoints(Graphics g, ViewContext vc, ToolManager tm) throws DrawingException;
	
	public abstract void transitionTo_Selection(ViewContext vc, ToolManager tm) throws FinishedAutomatonException, TransitionException;
	public abstract void drawIn_Selection(Graphics g, ViewContext vc, ToolManager tm) throws DrawingException;
	
	public abstract void transitionTo_PointWithSelection(ViewContext vc, ToolManager tm) throws FinishedAutomatonException, TransitionException;
	public abstract void drawIn_PointWithSelection(Graphics g, ViewContext vc, ToolManager tm) throws DrawingException;
	
	public abstract void transitionTo_Movement(ViewContext vc, ToolManager tm) throws FinishedAutomatonException, TransitionException;
	public abstract void drawIn_Movement(Graphics g, ViewContext vc, ToolManager tm) throws DrawingException;
	
	public abstract void transitionTo_MakeMove(ViewContext vc, ToolManager tm) throws FinishedAutomatonException, TransitionException;
	public abstract void drawIn_MakeMove(Graphics g, ViewContext vc, ToolManager tm) throws DrawingException;
	

	protected void setStatus(String status) throws NoSuchTransitionException {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public String getName() {
		return "Selection";
	}

	public String getMessage() {
		
		if ("Standby".equals(status)) {
			return Messages.getString("selection_standby");
		}
		
		if ("OnePoint".equals(status)) {
			return Messages.getString("");
		}
		
		if ("OnePointLeft".equals(status)) {
			return Messages.getString("selection_onepointleft");
		}
		
		if ("TwoPoints".equals(status)) {
			return Messages.getString("");
		}
		
		if ("Selection".equals(status)) {
			return Messages.getString("selection_selection");
		}
		
		if ("PointWithSelection".equals(status)) {
			return Messages.getString("");
		}
		
		if ("Movement".equals(status)) {
			return Messages.getString("selection_movement");
		}
		
		if ("MakeMove".equals(status)) {
			return Messages.getString("");
		}
		

		throw new RuntimeException();
	}

	public String getConsoleCommand() {
		return "select";
	}

	public String getTooltip() {
		return Messages.getString("selection_tooltip");
	}

	private String mouseCursor;

	public URL getMouseCursorURL() {
		if (mouseCursor != null) {
			return this.getClass().getResource(mouseCursor);
		} else {
			return null;
		}
	}

	public void setMouseCursor(String mouseCursor) {
		this.mouseCursor = mouseCursor;
	}

	public void toolFinished(ViewContext vc, ToolManager tm) throws NoSuchTransitionException, TransitionException, FinishedAutomatonException {
		
		if ("Standby".equals(status)) {
			
		}
		
		if ("OnePoint".equals(status)) {
			
		}
		
		if ("OnePointLeft".equals(status)) {
			
		}
		
		if ("TwoPoints".equals(status)) {
			
		}
		
		if ("Selection".equals(status)) {
			
		}
		
		if ("PointWithSelection".equals(status)) {
			
		}
		
		if ("Movement".equals(status)) {
			
		}
		
		if ("MakeMove".equals(status)) {
			
		}
		
	}

	public java.awt.Point getHotSpotOffset() {
		
		return new java.awt.Point(8, 8);
		
	}

}
