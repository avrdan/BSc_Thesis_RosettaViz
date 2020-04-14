package esa.esac.Rosetta.Visualization.Material;

import com.jme3.material.Material;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;

/**
 * Represents a material which does not take light into account when creating the object's final appearance.
 * Light does not affect objects with this material type applied.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class UnshadedMaterial extends AbstractMaterial{
	
	/**
	 * Initializes itself. Used by the material factory.
	 */
	public UnshadedMaterial()
	{
		super();
	}
	

	@Override
	public void setParameters(MaterialParams mp) {
		setMaterialParams(mp);
		
		

		setMat(new Material(GlobalTools.assetManager, "Common/MatDefs/Misc/Unshaded.j3md"));
		
		if(mp.getTexture() == null)
		getMat().setColor("Color", mp.getDiffuseColor());
		
		super.updateDiffuseTextureProperty("ColorMap");
		
	}

}
