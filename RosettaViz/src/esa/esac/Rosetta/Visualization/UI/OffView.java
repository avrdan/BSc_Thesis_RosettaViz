package esa.esac.Rosetta.Visualization.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.SceneProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.system.awt.AwtPanel;
import com.jme3.system.awt.PaintMode;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image.Format;
import com.jme3.util.BufferUtils;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.Universe;
import esa.esac.Rosetta.Visualization.Graphics.MaskObject;
import esa.esac.Rosetta.Visualization.Graphics.TextCreator;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;



/**
 * Represents the previews(secondary views which show a particular point of interest, rather than the whole scene).
 * Also known as offscreen views.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class OffView extends AbstractView{
    private AwtPanel panel;
    
    private int id;
	
    /**
     *  Do not use.
     */
    public OffView()
	{
		super();

	}
    
	/**
	 * Creates the offview. The user must specify the size of the window as well as the relationship between viewer and viewed objects.
	 * 
	 * @param width					the window width
	 * @param height				the window height
	 * @param controlDirection		the control direction (SpatialToCamera, CameraToSpatial)
	 * @param viewedObject			the viewed object
	 * @param viewedAxis			the viewed axis
	 * @param viewerObject			the viewer object (we see the scene from the viewer's POV)
	 * @param scene					the scene for this view, which is usually a sub-scene of the main view
	 */
	public OffView (int id, int width, int height, String controlDirection, VizObject viewedObject, 
			Vector3f viewedAxis, VizObject viewerObject, Node scene, int location, final MaskObject mask)
	{
		super(controlDirection, viewedAxis, viewedObject, viewerObject, scene);
		this.id = id;
		//this.width = width;
		//this.height = height;
			
		setCam(new Camera(width, height));
		getCam().setFrustumPerspective(60, 1, 10, 1000);
		//getCam().setLocation(new Vector3f(0f, 0f, 10f));
		
		// create a pre-view. a view that is rendered before the main view
		setView(GlobalTools.renderManager.createPreView("Offscreen View", getCam()));
		//setView(GlobalTools.renderManager.createPostView("Offscreen View", getCam()));
		getView().setBackgroundColor(ColorRGBA.Black);
		getView().setClearFlags(true, true, true);
		
		// attach the scene to the viewport to be rendered
        getView().attachScene(scene);
        setCameraNode(new CameraNode("Camera Node", getCam()));
        getCameraNode().setControlDir(ControlDirection.valueOf(controlDirection));
        viewerObject.getNode().attachChild(getCameraNode());
        panel = GlobalTools.ctx.createPanel(PaintMode.Accelerated);
        panel.attachTo(getView());        
        
        
        
        
        panel.setPreferredSize(new Dimension(width, height));
        
        if(viewedObject == null)
        {
        	GlobalTools.createWindowForPanel(panel, location, "Boresight", getView());
        }
        else
        {
        	GlobalTools.createWindowForPanel(panel, location, viewedObject.getNode().getName(), getView());
        }

	}
	
	public void addText(String name, Vector3f location, ColorRGBA color)
	{
		new TextCreator(name, location, color);
	}
	
	public void update()
	{
		if(this.getViewedAxis() != null)
			this.getCameraNode().lookAt(this.getViewedAxis().normalizeLocal(), Vector3f.UNIT_Y);
		else this.getCameraNode().lookAt(this.getViewedObject().getPosition(), Vector3f.UNIT_Y);
						
	}


	public int getId() {
		return id;
	}

	
}
