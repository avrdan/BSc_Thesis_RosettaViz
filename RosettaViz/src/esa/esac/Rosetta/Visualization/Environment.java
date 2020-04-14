package esa.esac.Rosetta.Visualization;

import java.util.ArrayList;

import com.jme3.app.Application;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.control.Control;

import esa.esac.Rosetta.Visualization.Graphics.MaskObject;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;
import esa.esac.Rosetta.Visualization.UI.AbstractView;
import esa.esac.Rosetta.Visualization.UI.InnerView;
import esa.esac.Rosetta.Visualization.UI.OffView;

/**
 * Represents the environment that contains all the views and windows. 
 * It basically is a "view/window manager controller". 
 * 
 * @author davram
 *
 * @version PreAlpha v0.21
 */
public class Environment {
	private Camera cam;
	private ArrayList<AbstractView> views;
	
	/**
	 *  Creates an empty array of views.
	 *  Does not modify the default camera.
	 *  
	 *  Ready to add views after calling this method.
	 */
	public Environment()
	{
		views = new ArrayList<AbstractView>();
	}
	
	/**
	 * Creates an empty array of views.
	 * Sets the default camera to a particular location and direction.
	 * 
	 * Ready to add views after calling this method.
	 * 
	 * @param cam the camera representing the "default camera" of the unvierse app
	 */
	public Environment(Camera cam)
	{		
		//this.cam = cam;
		
		// Setup first view
      
        cam.setLocation(new Vector3f(0, 0, 10));
 
        // set camera orientation
		cam.setAxes(new Vector3f(0, 1, 0), new Vector3f(1, 0, 0), new Vector3f(0, 0, 1));

		this.setCam(cam);

		views = new ArrayList<AbstractView>();
      
	}
	
	/**
	 * Adds an inner view to the environment.
	 * 
	 * @param width					the camera width
	 * @param height				the camera height
	 * @param controlDirection		the camera control direction
	 * @param viewedObject			the viewed object
	 * @param viewedAxis			the viewed axis
	 * @param viewerObject			the viewer object
	 * @param scene					the visible scene
	 */
	public void addView(int id,int width, int height, String controlDirection, VizObject viewedObject, 
			Vector3f viewedAxis, VizObject viewerObject, Node scene, int location, MaskObject mask)
	{
	
		// creates the new view and adds it to the array
		views.add(new OffView(id, width, height, controlDirection, viewedObject, viewedAxis, viewerObject, scene, location, mask));
	}
	
	/**
	 * Adds an off view to the environment.
	 * 
	 * @param viewPortPlacement			the viewport coordinates
	 * @param controlDirection			the camera control direction
	 * @param viewedObject				the viewed object
	 * @param viewedAxis				the viewed axis
	 * @param viewerObject				the viewer object
	 * @param scene						the visible scene
	 */
	public void addView(ArrayList<Float> viewPortPlacement, String controlDirection, VizObject viewedObject, Vector3f viewedAxis, VizObject viewerObject, Node scene)
	{
		
		// creates the new view and adds it to the array
		views.add(new InnerView(cam, controlDirection, viewedObject, viewedAxis, viewerObject, scene, viewPortPlacement));
	}
	
	/**
	 * Adds a new off view to the environment (a view in a separate window).
	 * @param view the view to be added
	 */
	public void addOffView(OffView view)
	{
		views.add(view);
	}

	/**
	 * Gets the main view camera.
	 * 
	 * @return the default camera
	 */
	public Camera getCam() {
		return cam;
	}

	/**
	 * Sets a new camera that replaces the old one for the main view.
	 * @param cam the new camera
	 */
	public void setCam(Camera cam) {
		this.cam = cam;
	}

	
	/**
	 * Gets the views/windows from the environment
	 * 
	 * @return the views/windows available
	 */
	public ArrayList<AbstractView> getWindows()
	{
		return views;
	}
	
	/**
	 * Used in the universe application's update method to update the direction of all camera views(the object they look at) at each frame.
	 * Also takes into account if what is viewed is an axis or a 3d object.
	 * If the axis is specified it will be used, otherwise the object will automatically be used.
	 */
	public void update()
	{
		for (AbstractView v : views)
		{
			if(v.getViewedAxis() != null)
				v.getCameraNode().lookAt(v.getViewedAxis().normalizeLocal(), Vector3f.UNIT_Y);
			else v.getCameraNode().lookAt(v.getViewedObject().getPosition(), Vector3f.UNIT_Y);
						
		}

	}
	
	/**
	 * Cleans up closed views from the view collection to free up occupied resources.
	 * 
	 * @param v the view to be removed
	 */
	public void removeView(AbstractView v)
	{
		views.remove(v);
	}
}
