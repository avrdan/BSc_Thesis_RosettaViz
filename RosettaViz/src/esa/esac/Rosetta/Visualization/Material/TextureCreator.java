package esa.esac.Rosetta.Visualization.Material;

import com.jme3.texture.Texture;

import esa.esac.Rosetta.Visualization.GlobalTools;



/**
 * Creates a texture based on a file specified by the user.
 * 
 * @author Dan Adrian avram
 *
 * @version PreAlpha v0.21
 */
public class TextureCreator{
	private Texture tex;
	
	/**
	 * Loads the texture into an appropriate "JME/Texture" object based 
	 * on the file path.
	 * 
	 * @param texFile a String representing the texture file path
	 */
	public TextureCreator(String texFile)
	{
		if (texFile != null)
			tex = GlobalTools.assetManager.loadTexture(texFile);
	}
	
	
	
	/**
	 * Gets the texture.
	 * 
	 * @return the texture
	 */
	public Texture getTexture()
	{
		return tex;
	}
		
}
