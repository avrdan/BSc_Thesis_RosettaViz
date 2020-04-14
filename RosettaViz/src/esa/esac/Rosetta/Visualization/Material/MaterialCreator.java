package esa.esac.Rosetta.Visualization.Material;

import com.jme3.material.Material;

import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;


/**
 * Provides an interface for creating a material.
 * 
 * @author Dan Adrian Avram
 *
 */
public interface MaterialCreator {

	/**
	 * Creates the material and returns it.
	 * @return the material
	 */
	public Material create();

	/**
	 * Sets the material parameters which define the actual appearance of the final material.
	 * 
	 * @param mp the material parameters
	 */
	public void setParameters(MaterialParams mp);
	
}
