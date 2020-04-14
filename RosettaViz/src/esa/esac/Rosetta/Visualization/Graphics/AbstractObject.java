package esa.esac.Rosetta.Visualization.Graphics;

import java.util.ArrayList;

import com.jme3.material.Material;

import com.jme3.math.Matrix3f;

import com.jme3.math.Vector3f;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;
import esa.esac.Rosetta.Visualization.Geometry.GeometryCreator;
import esa.esac.Rosetta.Visualization.Geometry.GeometryFactory;
import esa.esac.Rosetta.Visualization.Material.MaterialCreator;
import esa.esac.Rosetta.Visualization.Material.MaterialFactory;

/**
 * Represents an abstract type which has all the common properties of SPC, Solar & Sun objects.
 * All of the aforementioned object types must extend this class.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 *
 */
public abstract class AbstractObject
{
	private int id;
	
	private ObjectParams objParams;
	
	private Node objNode;
	
	private ArrayList<Geometry> geomArray;
	
	private String name;
	
	/**
	 * Do not use.
	 */
	public AbstractObject()
	{
		
	}
	
	/**
	 * Creates the common properties of a future object. 
	 * The new object shall be defined by a unique id and some shape parameters.
	 * 
	 * @param id		a unique id
	 * @param shape		some shape parameters
	 */
	public AbstractObject(ObjectParams objParams)
	{

		this.objParams = objParams;
		this.id = objParams.getId();
		this.name = objParams.getName();
		init();
	}
	
	/**
	 * Initializes the geometry factory with the supplied shape parameters 
	 * in order to create the future object's specific geometry.
	 */
	public void init()
	{	
		if(objParams.getShapeData() != null && objParams.getShapeData().getModelFile() != null)
		{
			
				objNode =(Node) GlobalTools.assetManager.loadModel(objParams.getShapeData().getModelFile().getName());
				objNode.setName(objParams.getName());
			
		}
		else
		{
			GeometryFactory factory = GeometryFactory.getInstance();
			factory.setShapeParameters(this.objParams);
			GeometryCreator creator = factory.getCreator();
			
	        geomArray = creator.create();
	        
	        setMaterial(objParams.getMaterialParameters());
	        
	        for(Geometry g : geomArray)
			{
				g.scale(objParams.getScale());
			}
	        
	        constructNode(objNode);
		}	
	}
	
	/**
	 * Gets these shape parameters.
	 * 
	 * @return these shape parameters
	 */
	public ObjectParams getShapeParams()
	{
		return this.objParams;
	}
	
	
	/**
	 * Sets the material of the object(to the geometry) by supplying some material parameters.
	 * 
	 * @param mp these material parameters
	 */
	public void setMaterial(MaterialParams mp)
	{
		Material material;
		
		MaterialFactory factory = MaterialFactory.getInstance();
		factory.setMaterialParameters(mp);
		MaterialCreator creator = factory.getCreator();
		
        material = creator.create();
	
		for(Geometry g : geomArray)
		{
			g.setMaterial(material);
		}
	}
	
	/**
	 * Gets the array of geometries which define the complete geometry of this object.
	 * 
	 * @return the geometry array
	 */
	public ArrayList<Geometry> getGeometry()
	{
		return geomArray;
	}
	
	/**
	 * Gets the object node of the current object.
	 * 
	 * @return this object's node
	 */
	public Node getNode()
	{
		return this.objNode;
	}
	
	/**
	 * Creates the object and other nodes of the current object.
	 * @param objNode this object's object node
	 */
	public void constructNode(Node objNode){

		if (this.objNode == null)
		{
			
			this.objNode = new Node(this.objParams.getName());

			for(Geometry g : geomArray)
			{
				if(g != null)
					this.objNode.attachChild(g);
			}
		}

	}
	
	/**
	 * Removes the object from the scene.
	 */
	public abstract void remove();
	
	/**
	 * Loads the object into the scene. 
	 */
	public abstract void load();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public abstract void connectNodes();

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
}