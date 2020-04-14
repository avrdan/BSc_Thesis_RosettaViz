package esa.esac.Rosetta.Visualization.Graphics;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;


/**
 * Represents a spacecraft(SPC) object. A 3D object which is an avatar for a typical spacecraft object (only includes artificial man-made objects).
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 */
public class SPCObject extends VizObject
{
	/**
	 * Do not use.
	 */
	public SPCObject()
	{
		
	}
	
	/**
	 * Creates the spacecraft object with a unique id and the specified parameters.
	 * 
	 * @param id		this object's unique id
	 * @param shape		this object's parameters that define its shape
	 */
	public SPCObject(ObjectParams shape)
	{
		super(shape);
	
	}
	
	
	@Override
	public void load()
	{
		// load the spc object
		if(!GlobalTools.spcObjectNode.hasChild(getTranslationNode()))
		{
			GlobalTools.spcObjectNode.attachChild(getTranslationNode());
			setConnected(true);
		}
	}

	@Override
	public void remove() {
		if(GlobalTools.spcObjectNode.hasChild(getTranslationNode()))
		{
			GlobalTools.spcObjectNode.detachChild(getTranslationNode());
			setConnected(false);
		}
		
	}
	
	@Override
	public void loadOrRemove()
	{
		if(!GlobalTools.spcObjectNode.hasChild(getTranslationNode()))
		{
			GlobalTools.spcObjectNode.attachChild(getTranslationNode());
			setConnected(true);
		}
		else if(GlobalTools.spcObjectNode.hasChild(getTranslationNode()))
		{
			GlobalTools.spcObjectNode.detachChild(this.getTranslationNode());
			setConnected(false);
		}
	}
	
	/**
	 * Adds a boresight to a 3D object which can be used to represent the boresight of a specific instrument
	 * on a spacecraft.
	 * 
	 * @param direction		the direction of the boresight
	 * @param length		the line's length
	 * @param color			the line's color
	 * @param lineWidth		the line's width
	 */
	public void addBoresight(Vector3f direction, float length, ColorRGBA color, float lineWidth)
	{
		Boresight bs = new Boresight(getPosition(), direction, length, color, lineWidth);
		
		getNode().attachChild(bs.getNode());
	}

	public void setRotation(Matrix3f rotation) {
		getNode().setLocalRotation(rotation);
		
	}

	
}