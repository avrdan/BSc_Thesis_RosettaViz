package esa.esac.Rosetta.Visualization.UI;

import java.util.ArrayList;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl.ControlDirection;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;

/**
 * Represents views which are drawn inside the main OpenGL view.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 */
public class InnerView extends AbstractView{
	private ArrayList<Float> viewPortPlacement;
	
	/**
	 * Do not use.
	 */
	public InnerView()
	{
		super();
	}
	
	/**
	 * Creates a view inside the main viewport. The default camera is specified here.
	 * 
	 * @param cam						the camera (initially the default camera)
	 * @param controlDirection			the control direction of the camera (SpatialToCamera or CameraToSpatial)
	 * @param viewedObject				the object which is viewed by the camera
	 * @param viewedAxis				the axis where the camera looks to
	 * @param viewerObject				the objects that views the scene
	 * @param scene						the visible scene of objects
	 * @param viewPortPlacement			the viewport size in the main window
	 */
	public InnerView(Camera cam, String controlDirection, VizObject viewedObject, Vector3f viewedAxis, VizObject viewerObject, Node scene, ArrayList<Float> viewPortPlacement)
	{
		super(controlDirection, viewedAxis, viewedObject, viewerObject,scene);
		
		this.viewPortPlacement = viewPortPlacement;
		
		
		
		setCam(cam.clone());
		
		getCam().setFrustumPerspective(60, 1, 10, 1000);
		
		getCam().setViewPort(viewPortPlacement.get(0), viewPortPlacement.get(1), viewPortPlacement.get(2), viewPortPlacement.get(3));
		
		setView(GlobalTools.renderManager.createMainView(viewedObject.getShapeParams().getName() + " from " + viewerObject.getShapeParams().getName(), getCam()));
		getView().setClearFlags(true, true, true);
		getView().attachScene(scene);
		
		setCameraNode(new CameraNode("Camera Node", getCam()));
		getCameraNode().setControlDir(ControlDirection.valueOf(controlDirection));
		
		getCameraNode().lookAt(viewedObject.getPosition(), Vector3f.UNIT_Y);
		
		viewerObject.getNode().attachChild(getCameraNode());
		
	}
	
	
	


	/**
	 * Retrieves the viewport coordinates with respect to the main view
	 * 
	 * @return the array of coordinates
	 */
	public ArrayList<Float> getViewPortPlacement() {
		return viewPortPlacement;
	}

	/**
	 * Sets the placement of the viewport unto the screen; 4 coordinates need to be specified(left, right, bottom, top). 
	 * They take values from 0 to 1.
	 * 
	 * @param viewPortPlacement the array of coordinates
	 */
	public void setViewPortPlacement(ArrayList<Float> viewPortPlacement) {
		this.viewPortPlacement = viewPortPlacement;
	}
	
}
