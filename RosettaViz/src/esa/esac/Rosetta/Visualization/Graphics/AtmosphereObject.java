package esa.esac.Rosetta.Visualization.Graphics;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;

/**
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class AtmosphereObject extends VizObject {
	
	/**
	 * Do not use this constructor. For internal use only.
	 */
	public AtmosphereObject()
	{
		
	}
	
	/**
	 * Creates a new atmosphere with a specified id and with the parameters read from a ShapeParams(shape parameters) object.
	 * 
	 * @param id 	the unique id of this VizObject
	 * @param shape the shape parameters which define this object's shape attributes.
	 */
	public AtmosphereObject(ObjectParams shape)
	{
		super(shape);
	
	}
	
	
	@Override
	public void load()
	{
		// load the spc object
		
		GlobalTools.solarObjectNode.attachChild(getTranslationNode());
		//getTranslationNode().attachChild(getNode());
		//getTranslationNode().attachChild(getArrowTranslationNode());

	}

	@Override
	public void remove() {
		GlobalTools.solarObjectNode.detachChild(getTranslationNode());
		
	}

	@Override
	public void loadOrRemove()
	{
		if(!GlobalTools.solarObjectNode.hasChild(getTranslationNode()))
			GlobalTools.solarObjectNode.attachChild(getTranslationNode());
		else if(GlobalTools.solarObjectNode.hasChild(getTranslationNode()))
			GlobalTools.solarObjectNode.detachChild(this.getTranslationNode());
	}
}
