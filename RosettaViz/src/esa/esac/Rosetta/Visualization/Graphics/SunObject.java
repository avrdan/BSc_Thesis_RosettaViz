package esa.esac.Rosetta.Visualization.Graphics;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;

/**
 * Represents a sun object. A 3D object which is an avatar for a sun(or star of interest).
 * This objects can be included neither in SPC nor Solar categories because of additional lighting and other properties. 
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 */
public class SunObject extends VizObject {
	
	private AmbientLight ambSun;
	private DirectionalLight sunLight;
	
	/**
	 * Do not use.
	 */
	public SunObject()
		{
			
		}
		
	/**
	 * Creates the sun object with a unique id and the specified parameters.
	 * 
	 * @param id		this object's unique id
	 * @param shape		this object's parameters that define its shape
	 */
	public SunObject(ObjectParams shape)
		{
			super(shape);
			
			// create the lights
			ambSun = new AmbientLight();
			sunLight = new DirectionalLight();
			
			// set the lights		
			setAmbientLight(new ColorRGBA(4.0f, 4.0f, 4.0f, 1.0f));
			setDirectionalSunLight(ColorRGBA.White, new Vector3f(0, 1, 1));
	
		}
		
	/**
	 * Sets the ambient light of the sun, in order to make the sun visible in the scene.
	 * Otherwise the light from inside the sun could not illuminate the sun itself.
	 * 
	 * @param lightColor	the color of the light
	 */
	public void setAmbientLight(ColorRGBA lightColor)
		   {
			   ambSun.setColor(lightColor);	   
			   super.getGeometry().get(0).addLight(ambSun);
		   }
	
	/**
	 * Sets the sun's directional light which is used to illuminate the rest of the universe.
	 * 
	 * @param lightColor			the color of the light
	 * @param lightDirection		the direction of the light
	 */
	public void setDirectionalSunLight(ColorRGBA lightColor, Vector3f lightDirection)
	{
		
		sunLight.setColor(new ColorRGBA(0.6f, 0.6f, 0.6f, 1));
		sunLight.setDirection(lightDirection);
		
		// load the light
		GlobalTools.sunLightNode.addLight(sunLight);
	}
	
	/**
	 * Gets the sun's directional light.
	 * 
	 * @return	the directional light
	 */
	public DirectionalLight getDirectionalSunLight()
	{
		return sunLight;
	}
	  
	@Override
	public void load()
	{
		// load the sun
		if(!GlobalTools.sunNode.hasChild(getTranslationNode()))
		{
			GlobalTools.sunNode.attachChild(getTranslationNode());
			setConnected(true);
		}
	}


	@Override
	public void remove() {
		if(GlobalTools.sunNode.hasChild(getTranslationNode()))
		{
			GlobalTools.sunNode.detachChild(this.getTranslationNode());
			setConnected(false);
		}
		
	}
	
	@Override
	public void loadOrRemove()
	{
		if(!GlobalTools.sunNode.hasChild(getTranslationNode()))
		{
			GlobalTools.sunNode.attachChild(getTranslationNode());
			setConnected(true);
		}
		else if(GlobalTools.sunNode.hasChild(getTranslationNode()))
		{
			GlobalTools.sunNode.detachChild(this.getTranslationNode());
			setConnected(false);
		}
	}
	
	/**
	 * Sets the current position of this object.
	 * 
	 * @param position		the position vector
	 */
	@Override
	public void setPosition(Vector3f position)
	{
		getTranslationNode().setLocalTranslation(position);
		sunLight.setDirection(new Vector3f(position).normalizeLocal().negateLocal());
	}
	   
	   
	 
	
	 }
