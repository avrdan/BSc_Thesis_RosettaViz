package esa.esac.Rosetta.Visualization.Material;

import com.jme3.material.Material;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;

/**
 * Represents a type of material used only for particle effects.
 * There are different textures which can be used for these effects.
 * For a complete list please go to: JME3 documentation
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 */
public class ParticleMaterial extends AbstractMaterial{
	/**
	 *  Used by the material factory to create a particle material, to be applied only on particle emitters.
	 */
	public ParticleMaterial()
	{
		super();
	}
	
	@Override
	public void setParameters(MaterialParams mp) {
		setMaterialParams(mp);
		
		

		setMat(new Material(GlobalTools.assetManager, "Common/MatDefs/Misc/Particle.j3md"));
		
		super.updateDiffuseTextureProperty("Texture");
		
	}
}
