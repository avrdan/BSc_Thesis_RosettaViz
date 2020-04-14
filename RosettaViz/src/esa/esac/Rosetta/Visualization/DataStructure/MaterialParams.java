package esa.esac.Rosetta.Visualization.DataStructure;

import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

/**
 * Herein the material parameters are defined. 
 * These parameters are used by the factories to create concrete materials.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class MaterialParams {
	
	private String type;
	private String name;
	private float shininess;
	
	private ColorRGBA diffuseColor, ambColor, specColor, glowColor;
	
	private String texture;
	
	public MaterialParams(String name, String type, String texture)
	{
		this.name = name;
		this.type = type;
		if(texture != null)
			this.texture = texture;
	}
	
	public MaterialParams(String name, String type)
	{
		this.name = name;
		this.type = type;
	}
	
	/**
	 * Gets the type of the material (for example: lighting, unshaded ...)
	 * 
	 * @return the material type 
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the material type.
	 * 
	 * @param type the material type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Gets the name of the material.
	 * 
	 * @return a string representing the material name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the material.
	 * 
	 * @param name the name string
	 */
	public void setName(String name) {
		this.name = name;
	}
		
	/**
	 * Gets the shininess property.
	 * 
	 * @return the shininess
	 */
	public float getShininess() {
		return shininess;
	}
	
	/**
	 * Sets the shininess property (defines how "shiny" the material will be).
	 * 
	 * @param shininess the shininess
	 */
	public void setShininess(float shininess) {
		this.shininess = shininess;
	}
	
	/**
	 * Gets the diffuse color of this material.
	 * In the case of unshaded materials, this is the only color used.
	 * 
	 * @return the diffuse color of this material
	 */
	public ColorRGBA getDiffuseColor() {
		return diffuseColor;
	}

	/**
	 * Sets the diffuse color of this material.
	 * 
	 * @param r 	the red channel
	 * @param g 	the green channel
	 * @param b 	the blue channel
	 * @param a 	the alpha channel
	 */
	public void setDiffuseColor(float r, float g, float b, float a) {
		this.diffuseColor = new ColorRGBA(r, g, b, a);
	}

	/**
	 * Gets the ambient color of this material.
	 * 
	 * @return the ambient color
	 */
	public ColorRGBA getAmbColor() {
		return ambColor;
	}

	/**
	 * Sets the ambient color of this material.
	 * 
	 * @param r 	the red channel
	 * @param g 	the green channel
	 * @param b 	the blue channel
	 * @param a 	the alpha channel
	 */
	public void setAmbColor(float r, float g, float b, float a) {
		this.ambColor = new ColorRGBA(r, g, b, a);
	}

	/**
	 * Gets the glow color. 
	 * This is usually used for effects(such as bloom & glow).
	 * 
	 * @return the glow color
	 */
	public ColorRGBA getGlowColor() {
		return glowColor;
	}

	/**
	 * Sets the glow color of this material.
	 * 
	 * @param r 	the red channel
	 * @param g 	the green channel
	 * @param b 	the blue channel
	 * @param a 	the alpha channel
	 */
	public void setGlowColor(float r, float g, float b, float a) {
		this.glowColor =  new ColorRGBA(r, g, b, a);
	}

	/**
	 * Gets the specular color of this material.
	 * 
	 * @return the specular color
	 */
	public ColorRGBA getSpecColor() {
		return specColor;
	}

	/**
	 * Sets the specular color of this material.
	 * 
	 * @param r 	the red channel
	 * @param g 	the green channel
	 * @param b 	the blue channel
	 * @param a 	the alpha channel
	 */
	
	public void setSpecColor(float r, float g, float b, float a) {
		this.specColor  = new ColorRGBA(r, g, b, a);
	}

	/**
	 * Gets the texture of this material.
	 * 
	 * @return the texture
	 */
	public String getTexture() {
		return texture;
	}

	/**
	 * Sets the texure of this material based on a string which specifies the file path.
	 * 
	 * @param texture the string representing the texture's file path
	 */
	public void setTexture(String texture) {
		this.texture = texture;
	}
	
	@Override
	public String toString()
	{
		if(texture != null)
			return "Name: " + name + ", Type: " + type + ", Texture: " + texture;
		else
			return "Name: " + name + ", Type: " + type + ", Texture: is null";
	}
	
}
