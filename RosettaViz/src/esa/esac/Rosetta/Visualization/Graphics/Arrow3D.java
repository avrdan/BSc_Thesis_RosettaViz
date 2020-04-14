package esa.esac.Rosetta.Visualization.Graphics;

import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Spatial;

import esa.esac.Rosetta.Visualization.GlobalTools;





/**
 * Represents an abstract arrow type which will be subclassed later. It sets all common properties of different arrows.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class Arrow3D {
	Spatial arrow;
	private ColorRGBA color;
	private String model;
	Material mat;
	private Matrix3f arrowDirection;
	
	/** 
	 * Do not use this constructor. For internal use only.
	 */
	public Arrow3D()
	{
		
	}
	
	/**
	 * Constructs the abstract arrow. Actually sets some parameters. Called automatically from a concrete arrow type.
	 * 
	 * @param model the path to the physical model created with a modelling tool (for example: Blender)
	 * @param color the color of the arrow
	 */
	public Arrow3D(String model, ColorRGBA color)
	{
		this.color = color;
		this.model = model;
		this.arrowDirection = new Matrix3f();
		createArrow();
	}
	
	/**
	 *  Sets the actual arrow parameters. 
	 *  First it sets the 3D model and then it applies the supplied color on an unshaded material.
	 *  It also sets up the arrow for transparency. Very useful to hide and show different arrows.
	 */
	private void createArrow() {
		this.arrow = GlobalTools.assetManager.loadModel(model);
		
		
		this.mat = new Material(GlobalTools.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		
		// important for transp
		this.mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		this.arrow.setQueueBucket(Bucket.Transparent);
		
		// set the material
		this.arrow.setMaterial(mat);
				
	}
	

	/**
	 * Makes the arrow invisible(transparent).
	 */
	public void hide()
	{
		

		this.mat.setColor("Color", this.getColor().add(new ColorRGBA(0, 0, 0, -1.0f)));
		this.arrow.setMaterial(this.mat);
	}
	
	/**
	 * Makes the arrow visible in the scene.
	 */
	public void show()
	{

		this.mat.setColor("Color", getColor().add(new ColorRGBA(0, 0, 0, +1.0f)));
		
		this.arrow.setMaterial(this.mat);
	}
	

	/**
	 * Sets this arrows's color to a different one.
	 * 
	 * @param r		red channel
	 * @param g		green channel
	 * @param b		blue channel
	 * @param a		alpha channel
	 */
	public void setColor(float r, float g, float b, float a)
	{
		mat.setColor("Color", new ColorRGBA(r, g, b, a));
	}
	
	/**
	 * Sets this arrows's color to a different one.
	 * 
	 * @param color the new color of the arrow
	 */
	public void setColor(ColorRGBA color)
	{
		mat.setColor("Color", color);
	}
	
	/**
	 * Gets the color of this arrow from the applied material.
	 * 
	 * @return the color of this arrow
	 */
	public ColorRGBA getColor()
	{
		return (ColorRGBA) mat.getParam("Color").getValue();
	}
	
	/**
	 * Gets the arrow direction of this arrow.
	 * 
	 * @return the arrow direction matrix
	 */
	public Matrix3f getArrowDirection()
	{
		return this.arrowDirection;
	}
	
	/** Gets the actual arrow.
	 * 
	 * @return the spatial which represents this arrow
	 */
	public Spatial getArrow()
	{
		return this.arrow;
	}
	
	
}
