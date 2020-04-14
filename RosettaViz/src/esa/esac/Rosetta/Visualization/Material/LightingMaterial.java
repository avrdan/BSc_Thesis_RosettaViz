package esa.esac.Rosetta.Visualization.Material;

import com.jme3.material.Material;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;


/**
 * Represents a material which takes into account light calculation for computing the final appearance.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class LightingMaterial extends AbstractMaterial{

	
/**
 *  Initializes itself. Used by the material factory.
 */
public LightingMaterial()
{
	super();
}

@Override
public void setParameters(MaterialParams mp) {
	setMaterialParams(mp);
	Material material = new Material(GlobalTools.assetManager, "Common/MatDefs/Light/Lighting.j3md");
	setMat(material);	
	
	try
	{
		if(mp.getTexture() == null)
			getMat().setBoolean("m_UseMaterialColors", true);
	
		if(mp.getAmbColor() != null)
			getMat().setColor("m_Ambient", mp.getAmbColor());
		if(mp.getDiffuseColor() != null)
	getMat().setColor("m_Diffuse", mp.getDiffuseColor());
		if(mp.getSpecColor() != null)
	getMat().setColor("m_Specular", mp.getSpecColor());
		if(mp.getShininess() != 0)
	getMat().setFloat("m_Shininess", mp.getShininess());
	
	}
	catch(Exception e)
	{
		System.out.println("Fatal error");
	}

	
	super.updateDiffuseTextureProperty("m_DiffuseMap");
	
}





	
}
