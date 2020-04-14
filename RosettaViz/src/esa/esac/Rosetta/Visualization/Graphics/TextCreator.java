package esa.esac.Rosetta.Visualization.Graphics;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;

import esa.esac.Rosetta.Visualization.GlobalTools;

/**
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class TextCreator {
	BitmapFont font;
	BitmapText text;
	
	/**
	 *  Do not use.
	 */
	public TextCreator()
	{
		
	}
	
	/**
	 * Creates text and displays it as a 2D object. Forms part of the HUD.
	 * 
	 * @param hudText			the string representing the actual text
	 * @param screenCoords		the screen coordinates
	 */
	public TextCreator(String hudText, Vector3f screenCoords, ColorRGBA color)
	{
		 // Display a line of text with a default font
	    
	    font = GlobalTools.assetManager.loadFont("Interface/Fonts/Default.fnt");
	    text = new BitmapText(font, false);
	    text.setSize(font.getCharSet().getRenderedSize());
	    text.setText(hudText);
	    
	    // Z is used to put some text in front of everything else or the reverse
	    text.setLocalTranslation(screenCoords.getX(), screenCoords.getY(), screenCoords.getZ());
	    
	    text.setColor(color);
	    
	    // attach to view
	    Node orthoNode = new Node("text ortho node");
	    orthoNode.setQueueBucket(Bucket.Gui);
	    orthoNode.setCullHint(CullHint.Never);
	    orthoNode.attachChild(text);
	    GlobalTools.viewNode.attachChild(orthoNode);
	}
	
	/**
	 * Gets the text.
	 * @return the final text object
	 */
	public BitmapText getText()
	{
		return text;
	}
}
