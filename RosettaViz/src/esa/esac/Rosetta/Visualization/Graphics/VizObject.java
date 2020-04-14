package esa.esac.Rosetta.Visualization.Graphics;


import java.io.IOException;
import java.sql.Array;
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
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Line;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.PlotToggleControl;
import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;
import esa.esac.Rosetta.Visualization.File.FileReader;
import esa.esac.Rosetta.Visualization.Geometry.CustomGeometryCreator;
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
public abstract class VizObject extends AbstractObject
{


	private Node translationNode, arrowTranslationNode;
	
	private ArrayList<PointerArrow> pointerArrowArr;
	private ArrayList<ObjectDistanceLine> objDistLineArr;
	private TrajectoryLine trailingLine;

	private boolean isConnected = false;
	/**
	 * Do not use.
	 */
	public VizObject()
	{
		
	}
	
	/**
	 * Creates the common properties of a future object. 
	 * The new object shall be defined by a unique id and some shape parameters.
	 * 
	 * @param id		a unique id
	 * @param shape		some shape parameters
	 */
	public VizObject(ObjectParams shape)
	{
		super(shape);	
		pointerArrowArr = new ArrayList<PointerArrow>();
		objDistLineArr = new ArrayList<ObjectDistanceLine>();
		
		translationNode = new Node(super.getShapeParams().getName() + " translation node");
		
		arrowTranslationNode = new Node(super.getShapeParams().getName() + " arrow translation node");
		
		setScale(shape.getScale());
		
		connectNodes();
		
		load();	
	}
	
	/**
	 * Gets these shape parameters.
	 * 
	 * @return these shape parameters
	 */
	public ObjectParams getShapeParams()
	{
		return super.getShapeParams();
	}
	
	
	/**
	 * Sets the material of the object(to the geometry) by supplying some material parameters.
	 * 
	 * @param mp these material parameters
	 */
	public void setMaterial(MaterialParams mp)
	{
		super.setMaterial(mp);
	}
	
	/**
	 * Gets the array of geometries which define the complete geometry of this object.
	 * 
	 * @return the geometry array
	 */
	public ArrayList<Geometry> getGeometry()
	{
		return super.getGeometry();
	}
	
	/**
	 * Gets the object node of the current object.
	 * 
	 * @return this object's node
	 */
	public Node getNode()
	{
		return super.getNode();
	}
	
	/**
	 * Connects the node in a tree so that the object will be set up properly.
	 */
	public void connectNodes()
	{
		translationNode.attachChild(super.getNode());
		translationNode.attachChild(arrowTranslationNode);
	}
	
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
		
		if(getTrailingLine() != null)
		{
			trailingLine.updateLine(new Vector3f(x, y, z));
		}
	}
	
	/**
	 * Sets the current position of this object.
	 * 
	 * @param vector	the vector which defines the current position
	 */
	public void setPosition(Vector3f vector)
	{
		for (ObjectDistanceLine odl : objDistLineArr)
		{
			odl.updateLine(getPosition(), odl.getDestObj());
		}
		this.translationNode.setLocalTranslation(vector);
		
		if(getTrailingLine() != null)
		{
			trailingLine.updateLine(vector);
		}
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
		this.getNode().setLocalRotation(getRotation().mult(rotation));
	}
	
	public void setRotation(Matrix3f rotation) {
		getNode().setLocalRotation(rotation);
		
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
	 * Sets the scale of the object.
	 * 
	 * @param s		the scale factor on all axis'
	 */
	public void setScale(float s)
	{
		this.translationNode.setLocalScale(s);
	}
	
	/**
	 * Removes the object from the scene.
	 */
	public abstract void remove();
	
	/**
	 * Loads the object into the scene. 
	 */
	public abstract void load();
	
	public abstract void loadOrRemove();

	public int getId() {
		return super.getId();
	}
	
	

	public void setId(int id) {
		super.setId(id);
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
		
		super.getNode().attachChild(lm.getNode());
	}
	
	/**
	 * 
	 * 
	 * @param destObj	the destination object(the end point of the line)
	 * @param color		the line's color
	 * @param f			the line's width
	 */
	public void addLine(VizObject vizObject, ColorRGBA color, float f) {
		
		ObjectDistanceLine odl = new ObjectDistanceLine(getPosition(), vizObject, color, f);

		getTranslationNode().attachChild(odl.getNode());
		//getNode().attachChild(odl.getNode());
		objDistLineArr.add(odl);
		
		//objNode.attachChild(odl.getNode());		
		//getArrowTranslationNode().attachChild(odl.getNode());
	}
	
	/**
	 * 
	 * 
	 * @param destObj	the destination object(the end point of the line)
	 * @param color		the line's color
	 * @param f			the line's width
	 */
	public void addLine(ArrayList<Vector3f> trajectory, ColorRGBA color, float width, int trailing) {
		TrajectoryLine tl;
		
		if(trailing != 0)
		{
			tl = new TrajectoryLine(trajectory, color, width, true);
			trailingLine = tl;
		}
		else
			tl = new TrajectoryLine(trajectory, color, width, false);

		//GlobalTools.sunNode.attachChild(tl.getNode());		
		//getArrowTranslationNode().attachChild(tl.getNode());
	}
	 
	public void addTrajectoryLine(Vector3f startPoint, Vector3f destPoint, ColorRGBA color, float lineWidth)
	{
		TrajectoryLine tl;
		
		tl = new TrajectoryLine(startPoint, destPoint, color, lineWidth);
		
		GlobalTools.sunNode.attachChild(tl.getNode());
	}
	
	public TrajectoryLine getTrailingLine()
	{
		return trailingLine;
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
		super.getNode().attachChild(cArr.getArrow());	
		super.getNode().setLocalRotation(cArr.getArrowDirection());
	}
	
	public void addPointerArrow(VizObject destObj, String modelFile, ColorRGBA color)
	{
		PointerArrow pArr = new PointerArrow(modelFile, color, destObj, this);
		this.arrowTranslationNode.attachChild(pArr.getArrow());
		pointerArrowArr.add(pArr);
	}
	
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
	
	}

	public void addROI(Array array, ColorRGBA color) {
		//new CustomGeometryCreator().getGeom()
		Material mat = new Material(GlobalTools.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
	}
	
	public boolean isConnected()
	{
		return isConnected;
	}
	
	public void setConnected(boolean isConnected)
	{
		this.isConnected = isConnected;
	}

	public void addControl(Control spatialControl)
	{
		this.getTranslationNode().addControl(spatialControl);
	}
	
	public PlotToggleControl getControl()
	{
		
		return this.getTranslationNode().getControl(PlotToggleControl.class);
	}
}