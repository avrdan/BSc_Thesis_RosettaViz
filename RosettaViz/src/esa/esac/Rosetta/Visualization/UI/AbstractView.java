package esa.esac.Rosetta.Visualization.UI;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;

import esa.esac.Rosetta.Visualization.Graphics.VizObject;

public abstract class AbstractView {
	private Camera cam;
	private String controlDirection;
	private VizObject viewedObject, viewerObject;
	private Node scene;
	private CameraNode camNode;
	private Vector3f viewedAxis;
	private ViewPort view;
	
	
	
	/** Do not use this constructor. For internal use only.
	 * 
	 */
	public AbstractView()
	{
		
	}
	
	/**
	 * Represents an abstract view which can be subclassed later into either an "inner view" (a viewport inside the main OpenGL window) or an "off view" which is actually
	 * a view displayed in an entirely separate window (frame).
	 * 
	 * @param controlDirection  the direction of the camera - copies the coords either from spatial to the camera or from the camera to the spatial; possible values: "SpatialToCamera" and "CameraToSpatial"
	 * @param viewedObject 		the object which is viewed by the camera
	 * @param viewerObject 		the object which is the viewer and which has the camera attached
	 * @param scene 			the scene that is visible from this camera
	 */
	public AbstractView(String controlDirection, Vector3f viewedAxis, VizObject viewedObject, VizObject viewerObject, Node scene)
	{
		this.setControlDirection(controlDirection);
		
		if(viewedAxis != null)
			this.setViewedAxis(viewedAxis);
		
		if(viewedObject != null)
			this.setViewedObject(viewedObject);
		
		this.setViewerObject(viewerObject);
		this.setScene(scene);
	}
	
	/**
	 * Sets the camera node which has attached this camera and this view which subsequently attaches this scene.
	 * 
	 * @param camNode the camera node
	 */
	
	public void setCameraNode(CameraNode camNode)
	{
		this.camNode = camNode;
	}
	
	/**
	 * Gets the camera node. Used for updating the cameras direction and for cleaning up.
	 * 
	 * @return the camera node
	 */
	public CameraNode getCameraNode()
	{
		return camNode;
	}

	/**
	 * Gets the scene which is visible from this camera.
	 * 
	 * @return the node which represents this scene
	 */
	public Node getScene() {
		return scene;
	}

	/**
	 * Sets this scene. Objects which are not in this scene will not appear in this particular view.
	 * 
	 * @param scene the scene node
	 */
	public void setScene(Node scene) {
		this.scene = scene;
	}

	/**
	 * Gets this view's camera.
	 * 
	 * @return this view's camera
	 */
	public Camera getCam() {
		return cam;
	}

	/**
	 * Sets the camera for this view.
	 * 
	 * @param cam this view's camera
	 */
	public void setCam(Camera cam) {
		this.cam = cam;
	}

	/**
	 * Gets the control direction from this view in the form of a string.
	 * 
	 * @return a string which represents the control direction
	 */
	public String getControlDirection() {
		return controlDirection;
	}

	/**
	 * Sets the control direction.
	 * 
	 * @param controlDirection the string which is used to set the control direction internally
	 */
	public void setControlDirection(String controlDirection) {
		this.controlDirection = controlDirection;
	}

	/**
	 * Gets the viewed axis.
	 * 
	 * @return a JME Vector3f which represents the viewed axis
	 */
	public Vector3f getViewedAxis() {
		return viewedAxis;
	}

	/**
	 * Sets the viewed axis.
	 * 
	 * @param viewedAxis
	 */
	public void setViewedAxis(Vector3f viewedAxis) {
		this.viewedAxis = viewedAxis;
	}

	/**
	 * Gets the object from which the user is viewing the scene.
	 * @return the viewer object
	 */
	public VizObject getViewerObject() {
		return viewerObject;
	}

	/**
	 * Sets the viewer object, to a different object, which basically changes the view.
	 * 
	 * @param viewerObject a 3D object
	 */
	public void setViewerObject(VizObject viewerObject) {
		this.viewerObject = viewerObject;
	}

	/**
	 * Gets the object which the user is viewing from this view/window.
	 * 
	 * @return the viewed object
	 */
	public VizObject getViewedObject() {
		return viewedObject;
	}

	/**
	 * Sets the viewed object, to a different object, which basically focuses the view on the new object.
	 * 
	 * @param viewedObject a 3D object
	 */
	public void setViewedObject(VizObject viewedObject) {
		this.viewedObject = viewedObject;
	}

	/**
	 * Gets the viewport from this view/window.
	 * 
	 * @return the viewport
	 */
	public ViewPort getView() {
		return view;
	}

	/**
	 * Sets the viewport.
	 * 
	 * @param view a viewport
	 */
	public void setView(ViewPort view) {
		this.view = view;
	}
	

}
