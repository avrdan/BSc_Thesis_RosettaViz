package esa.esac.Rosetta.Visualization.Material;

import com.jme3.material.Material;
import com.jme3.texture.Texture;

import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;




/**
 * Represents an abstract material which will be subclassed and instantiated into a physical material.
 * This material will define the appearance of a 3D object geometry.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public abstract class AbstractMaterial implements MaterialCreator{
	
	private com.jme3.material.Material mat;
	private String name;
	
	private MaterialParams mp;
	
	/** Used by the material factory in order to create a new type of material.
	 * 
	 */
	public AbstractMaterial()
	{
		
	}
	
	
	/** Sets the material parameters, which define this material. 
	 *   
	 * @param mp the material parameters for this material; this value is used in concrete material classes
	 */
	public void setMaterialParams(MaterialParams mp)
	{
		this.mp = mp;
		setName(mp.getName());
	}
	
	/** Gets the material parameters of this material.
	 * 
	 * @return the material parameters for this material
	 */
	public MaterialParams getMaterialParams()
	{
		return mp;
	}
	

	/**
	 * Gets this material for use later on a geometry.
	 * 
	 * @return this material
	 */
	public Material getMat() {
		return mat;
	}

	/**
	 *  Sets the actual JME material
	 * @param mat the JME material
	 */
	public void setMat(Material mat) {
		this.mat = mat;
	}

	/**
	 * Gets the name of this material
	 * 
	 * @return the name of this material
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 
	 * Sets a texture on this material.
	 * Searches for a texture provided by the following parameter:
	 * 
	 * @param texParam Represents a string which defines the location of the texture.
	 * 				   Note that the rest of the path must be added to your classpath or you cand add it to the JME assetManager.
	 */
	public void updateDiffuseTextureProperty(String texParam)
	{
		Texture tex = new TextureCreator(this.mp.getTexture()).getTexture();

		if(tex != null)
			getMat().setTexture(texParam, tex);
		//else System.out.println("The texture is null");
		//System.out.println("The texture is NOT null");
		
		//System.out.println(getMat().getParams());
	}
	

	@Override
	public Material create() {
		return mat;
	}


	
}
