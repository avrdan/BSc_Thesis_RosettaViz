package esa.esac.Rosetta.Visualization.Graphics;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;

/**
 * Represents a solar object. A 3D object which is an avatar for a typical cosmic object (only includes natural objects).
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class SolarObject extends VizObject
{
	/**
	 * Do not use.
	 */
	public SolarObject()
	{
		
	}
	
	/**
	 * Creates the solar object with a unique id and the specified parameters.
	 * 
	 * @param id		this object's unique id
	 * @param shape		this object's parameters that define its shape
	 */
	public SolarObject(ObjectParams shape)
	{
		super(shape);
	
	}
	
	@Override
	public void load()
	{
		// load the solar object	
		if(!GlobalTools.solarObjectNode.hasChild(getTranslationNode()))
		{
			GlobalTools.solarObjectNode.attachChild(getTranslationNode());
			setConnected(true);
		}
		
	}

	@Override
	public void remove() {
		if(GlobalTools.solarObjectNode.hasChild(getTranslationNode()))
		{
			GlobalTools.solarObjectNode.detachChild(getTranslationNode());
			setConnected(false);
		}
		
	}
	
	@Override
	public void loadOrRemove()
	{
		if(!GlobalTools.solarObjectNode.hasChild(getTranslationNode()))
		{
			GlobalTools.solarObjectNode.attachChild(getTranslationNode());
			setConnected(true);
		}
		else if(GlobalTools.solarObjectNode.hasChild(getTranslationNode()))
		{
			GlobalTools.solarObjectNode.detachChild(this.getTranslationNode());
			setConnected(false);
		}
	}
}
