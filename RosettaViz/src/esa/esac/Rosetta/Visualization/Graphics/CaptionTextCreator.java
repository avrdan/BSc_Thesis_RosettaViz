package esa.esac.Rosetta.Visualization.Graphics;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.control.BillboardControl;

import esa.esac.Rosetta.Visualization.GlobalTools;


/**
 * Creates the specified caption text for a particular 3d object.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 *
 */
public class CaptionTextCreator {
	private BitmapFont font;
	private BitmapText text;
	private Node bbNode;
	
	/**
	 * For internal use only.
	 */
	public CaptionTextCreator()
	{
		
	}
	
	/**
	 * Creates the caption text with the specified text, color and object coordinates.
	 * 
	 * @param caption 		the String which is displayed as a caption
	 * @param color   		the color of the caption text
	 * @param objCoords		the coordinates of the object which has this caption
	 */
	public CaptionTextCreator(String caption, ColorRGBA color, Vector3f objCoords)
	{
		// Display a line of text with a default font
	    
	    font = GlobalTools.assetManager.loadFont("Interface/Fonts/Default.fnt");
	    text = new BitmapText(font, false);
	    //text.setSize(font.getCharSet().getRenderedSize());
	    text.setSize(1.5f);
	    text.setText(caption);

	    
	    //cam.getScreenCoordinates(new Vector3f(0, 0, 0).getY();
	    text.setLocalTranslation(objCoords.getX() - (text.getLineWidth() / 2), (float) (objCoords.getY() + (text.getHeight() * 2.5)), 0f);
	    //text.setLocalTranslation(objCoords.getX() - (text.getLineWidth() / 2), objCoords.getY() + (Universe.getApplication().getCamera().getScreenCoordinates(objCoords).getY() + 5), 0);
	    text.setColor(color);
	    
	    text.setQueueBucket(Bucket.Transparent);
	    
	    
	    bbNode = new Node("Billboard Node");
	    
	    BillboardControl control=new BillboardControl();
	    
	    bbNode.addControl(control);
	    bbNode.attachChild(text);

	}
	
	/**
	 * Gets the "billboard" node, which in this case represents the caption node.
	 * Please note that any rotation applied here will not rotate the caption(billboard).
	 * 
	 * @return the node
	 */
	public Node getBillboardNode()
	{
		return bbNode;
	}
	
}
