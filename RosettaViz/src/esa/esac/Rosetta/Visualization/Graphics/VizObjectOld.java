package esa.esac.Rosetta.Visualization.Graphics;


import java.io.IOException;
import java.util.ArrayList;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Line;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;
import esa.esac.Rosetta.Visualization.File.FileReader;
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
public abstract class VizObjectOld
{
	private int id;
	
	private ObjectParams objParams;
	private Geometry geom;
	
	//private AbstractMaterial material;
	
	private Node objNode, translationNode, arrowTranslationNode, orthoNode;
	
	
	


	
	private ArrayList<Geometry> geomArray;
	
	private ArrayList<PointerArrow> pointerArrowArr;


	/**
	 * Do not use.
	 */
	public VizObjectOld()
	{
		
	}
	
	/**
	 * Creates the common properties of a future object. 
	 * The new object shall be defined by a unique id and some shape parameters.
	 * 
	 * @param id		a unique id
	 * @param shape		some shape parameters
	 */
	public VizObjectOld(ObjectParams shape)
	{

		this.objParams = shape;
		this.id = shape.getId();
	
		init();
		createHierarchy();
		load();	
	}
	

	
	
	/**
	 * Initializes the geometry factory with the supplied shape parameters 
	 * in order to create the future object's specific geometry.
	 */
	public void init()
	{
		
		pointerArrowArr = new ArrayList<PointerArrow>();
		
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
		}
		
		
	}
	
	/**
	 * NOT IMPLEMENTED!
	 * Adds geometries to the geometry array.
	 */
	public void addGeometry()
	{
		for (Geometry g : geomArray)
		{
			// add
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
	 * Creates an internal node hierachy which is used for all SPC, Solar & Sun objects 
	 * and perhaps for future object types.
	 */
	public void createHierarchy()
	{
		constructNode(objNode);
	    connectNodes();
	}
	
	/*public Geometry getGeometry()
	{
		return geom;
	}*/
	
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
		
		translationNode = new Node(this.objParams.getName() + " translation node");
		arrowTranslationNode = new Node(this.objParams.getName() + " arrow translation node");
		orthoNode = new Node(objParams.getName() + "text ortho node");
		
			
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
	 * Connects the node in a tree so that the object will be set up properly.
	 */
	public void connectNodes()
	{
		translationNode.attachChild(objNode);
		translationNode.attachChild(arrowTranslationNode);
		
		translationNode.attachChild(orthoNode);
		orthoNode.setQueueBucket(Bucket.Gui);
		orthoNode.setCullHint(CullHint.Never);
	}

	
	


	
	/**
	 * NOT IMPLEMENTED YET!
	 * 
	 * @param type		the arrow's type
	 * @param color		the arrow's color
	 */
	public abstract void addArrow(String type, String color);
	
	/**
	 * Creates arrows that point to a specific object in the Universe.
	 * For example, an arrow pointing from a spacecraft to the Sun.
	 * 
	 * @param colorArray	the array which defines all the pointing arrow's color
	 * @param objectArray	the array which defines all the pointing arrow's destination object
	 */

		
		
	

	
	
	
	/**
	 * Gets this object's translation node.
	 * This node is ONLY affected by translation.
	 * Rotation has no effect on any Spatial(object) attached here.
	 * 
	 * @return	the translation node
	 */
	public Node getTranslationNode()
	{
		return this.translationNode;
	}
	
	/**
	 * Like the translation node, object rotation does not affect arrows that are attached here.
	 * Used for the pointing arrows.
	 * 
	 * @return the arrow translation node
	 */
	public Node getArrowTranslationNode()
	{
		return this.arrowTranslationNode;
	}
	
	/**
	 * Sets the current position of this object.
	 * 
	 * @param x		the x position
	 * @param y		the y position
	 * @param z		the z position
	 */
	public void setPosition(float x, float y, float z)
	{
		this.translationNode.setLocalTranslation(x, y, z);
		
	}
	
	/**
	 * Sets the current position of this object.
	 * 
	 * @param vector	the vector which defines the current position
	 */
	public void setPosition(Vector3f vector)
	{
		
		this.translationNode.setLocalTranslation(vector);
	}
	
	
	

	
	/**
	 * Sets the current rotation for this object, in local coordinate space.
	 * Must be specified in radians.
	 * 
	 * @param yaw		rotation on x
	 * @param roll		rotation on y
	 * @param pitch		rotation on z
	 */
	public void rotate(float yaw, float roll, float pitch)
	{
		this.getNode().rotate(yaw, roll, pitch);
	}
	
	/**
	 * Sets the current rotation for this object, in local coordinate space.
	 * Must specify the rotation angle and axis.
	 * @param rotation
	 */
	public void setRotation(float angle, Vector3f axis)
	{
		Matrix3f rotation = new Matrix3f();
		rotation.fromAngleAxis(angle, axis);
		//rotationMatrix.
		//getRotation().fromAngleAxis(angle, axis);
		this.getNode().setLocalRotation(getRotation().mult(rotation));
		
	}
	
	/**
	 * Gets the current rotation of this object.

	 * @return a matrix representing the rotation
	 */
	public Matrix3f getRotation()
	{
		return this.getNode().getLocalRotation().toRotationMatrix();
	}
	
	/**
	 * Gets this object's current position.
	 * 
	 * @return the current position
	 */
	public Vector3f getPosition()
	{
		return this.translationNode.getLocalTranslation();
	}
	

	
	/**
	 * Sets the scale of the object.
	 * The default values for x,y,z are 1.
	 * 
	 * @param x		the scale factor on x
	 * @param y		the scale factor on y
	 * @param z		the scale factor on z
	 */
	public void setScale(float x, float y, float z)
	{
		this.translationNode.setLocalScale(x, y, z);
	}

	/**
	 * Gets this object's material.
	 * 
	 * @return the applied material
	 */
	/*public AbstractMaterial getMaterial() {
		return material;
	}*/
	

	
	
	


	
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
	
	/**
	 * Adds a landmark to a 3D object which represents a point of interest.
	 * 
	 * @param latitude		latitude coordinate: [-90, 90]
	 * @param longitude		longitude coordinate: [-180, 180]
	 * @param length		the line's length
	 * @param color			the line's color
	 * @param lineWidth		the line's width
	 */
	public void addLandmark(float latitude, float longitude, float length, ColorRGBA color, float lineWidth)
	{
		Landmark lm = new Landmark(getPosition(), latitude, longitude, length, color, lineWidth);
		
		objNode.attachChild(lm.getNode());
	}
	
	/**
	 * 
	 * 
	 * @param destObj	the destination object(the end point of the line)
	 * @param color		the line's color
	 * @param f			the line's width
	 */
	public void addLine(VizObject vizObject, ColorRGBA color, float f) {
		
		ObjectDistanceLine odl = new ObjectDistanceLine(getPosition(), vizObject.getPosition(), color, f);
		//objDistLineArr.add(odl);
		
		GlobalTools.sunLightNode.attachChild(odl.getNode());		
	}
	
	/**
	 * Adds caption text to an object.
	 */
	public void addCaption(String text, ColorRGBA color)
	{	
		translationNode.attachChild(new CaptionTextCreator(text, color, translationNode.getLocalTranslation()).getBillboardNode());
		
	}
	
	/**
	 * Add a coordinate arrow.
	 * 
	 * @param modelFile			3d Arrow model location
	 * @param color				color
	 * @param angle				angle
	 * @param direction			direction
	 */
	public void addCoordinateArrow(String modelFile, ColorRGBA color, float angle, Vector3f direction)
	{
		CoordinateArrow cArr = new CoordinateArrow(modelFile, color, angle, direction);
		objNode.attachChild(cArr.getArrow());	
		objNode.setLocalRotation(cArr.getArrowDirection());
	}
	
//	public void addPointerArrow(VizObject destObj, String modelFile, ColorRGBA color)
//	{
//		PointerArrow pArr = new PointerArrow(modelFile, color, destObj, this);
//		this.arrowTranslationNode.attachChild(pArr.getArrow());
//		pointerArrowArr.add(pArr);
//	}
//	
	/**
	 * Sets the direction of the pointing arrows.
	 * This needs to be updated every frame in an animated scene.
	 */
	public void setPtrArrowDirection(Spatial pointerArrow)
	{
		Vector3f start; 
		Vector3f endPoint;
		Vector3f startPoint;
		Vector3f end = new Vector3f();
		Matrix3f rotation = new Matrix3f();
		start = new Vector3f(0, 0, 1);
		
		startPoint = (new Vector3f(this.translationNode.getWorldTranslation().getX(), 
					this.translationNode.getWorldTranslation().getY(), this.translationNode.getWorldTranslation().getZ()).normalize());
			
		endPoint = (new Vector3f(pointerArrow.getWorldTranslation().getX(), pointerArrow.getWorldTranslation().getY(), 
				pointerArrow.getWorldTranslation().getZ()));
				
		
		
		end = startPoint.subtractLocal(endPoint).normalizeLocal();
			
		rotation.fromStartEndVectors(start, end);
		pointerArrow.setLocalRotation(rotation);
		//arrowPtrArray.get(i).setArrowDirection(start, end, arrowTranslationNode, i);
		
		/*for(int i = 0; i < arrowPtrArray.size(); i++)
		{	
			start = new Vector3f(0, 0, 1);
			
			startPoint = (new Vector3f(this.translationNode.getWorldTranslation().getX(), 
						this.translationNode.getWorldTranslation().getY(), this.translationNode.getWorldTranslation().getZ()).normalize());
				
			endPoint = (new Vector3f(this.shape.getObjectArray().get(i).getGeometry().get(0).getWorldTranslation().getX(), this.shape.getObjectArray().get(i).getGeometry().get(0).getWorldTranslation().getY(), 
					this.shape.getObjectArray().get(i).getGeometry().get(0).getWorldTranslation().getZ() ));

			
			end = startPoint.subtractLocal(endPoint).normalize();
				
			
			arrowPtrArray.get(i).setArrowDirection(start, end, arrowTranslationNode, i);
					
		}*/	
	}
	
	/**
	 * Sets the direction of the pointing arrows.
	 * This needs to be updated every frame in an animated scene.
	 */
	public void setPtrArrowDirection()
	{
		Vector3f start; 
		Vector3f endPoint;
		Vector3f startPoint;
		Vector3f end = new Vector3f();
		Matrix3f rotation = new Matrix3f();
		start = new Vector3f(0, 0, 1);
		
		for(PointerArrow pArr: pointerArrowArr)
		{
			startPoint = (new Vector3f(this.translationNode.getWorldTranslation().getX(), 
					this.translationNode.getWorldTranslation().getY(), this.translationNode.getWorldTranslation().getZ()).normalize());
			
			endPoint = (new Vector3f(pArr.getDestObj().getPosition().getX(), pArr.getDestObj().getPosition().getY(), 
					pArr.getDestObj().getPosition().getZ()));
				
			end = startPoint.subtractLocal(endPoint).normalizeLocal();
			
			pArr.setArrowDirection(start, end, arrowTranslationNode);
		}
		
		
		
			
		//rotation.fromStartEndVectors(start, end);
		//pointerArrow.setLocalRotation(rotation);
		//arrowPtrArray.get(i).setArrowDirection(start, end, arrowTranslationNode, i);
		
		/*for(int i = 0; i < arrowPtrArray.size(); i++)
		{	
			start = new Vector3f(0, 0, 1);
			
			startPoint = (new Vector3f(this.translationNode.getWorldTranslation().getX(), 
						this.translationNode.getWorldTranslation().getY(), this.translationNode.getWorldTranslation().getZ()).normalize());
				
			endPoint = (new Vector3f(this.shape.getObjectArray().get(i).getGeometry().get(0).getWorldTranslation().getX(), this.shape.getObjectArray().get(i).getGeometry().get(0).getWorldTranslation().getY(), 
					this.shape.getObjectArray().get(i).getGeometry().get(0).getWorldTranslation().getZ() ));

			
			end = startPoint.subtractLocal(endPoint).normalize();
				
			
			arrowPtrArray.get(i).setArrowDirection(start, end, arrowTranslationNode, i);
					
		}*/	
	}


}