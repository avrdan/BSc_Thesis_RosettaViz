package esa.esac.Rosetta.Visualization.DataStructure;

import java.io.File;
import java.util.ArrayList;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;


/**
 * Herein the shape parameters are defined. 
 * These parameters are used by the factories to create concrete shapes.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class ObjectParams {
	// general
	private int id;
	private String type;
	private String name;
	private String geometryType;
	
	// custom
	private File file;
	private File posFile;
	
	// sphere data
	private float radius;
	
	// arrows
	boolean xAVisible = false, yAVisible = false, zAVisible = false;
	
	// arrow color
	String arrowColor;
	//VizObject destinationObject;
	
	// end obj id
	// start object known	
	ArrayList<String> colorArray = new ArrayList<String>();
	//ArrayList<VizObject> objectArray = new ArrayList<VizObject>();
	
	//lines
	//ArrayList<LineObject> lineArray = new ArrayList<LineObject>();
	
	// THE SHAPE DATA!
	private ShapeData sd;
	
	// THE POSITION DATA!
	private PositionData pd;
	
	// the material params
	private MaterialParams mp;
	
	// text
	private String caption;
	private ColorRGBA captionColor;
	
	//scale
	private float scaleFactor;

	/**
	 * Do not use.
	 */
	public ObjectParams()
	{
		
	}
	
	/**
	 * Creates the data structure that contains all the shape's parameters.
	 * 
	 * @param geometryType			the type of shape
	 * @param radius		the radius of the shape (if spherical)
	 * @param filename		the input file for the shape (if needed)
	 * @param material		the shape's material
	 * @param texture		the shape's texture
	 */
	public ObjectParams(int id, String name, String type, String geometryType, float radius, String filename, ShapeData sd, PositionData pd, MaterialParams mp, float scaleFactor)
	{
		this.setId(id);
		this.name = name;
		this.type = type;
		this.geometryType = geometryType;
		this.radius = radius;
		
		if(filename != null)
			this.file = new File(filename);
		
		if(sd != null)
			this.sd = sd;
		
		if(pd != null)
			this.pd = pd;
		
		this.mp = mp;
		
		this.scaleFactor = scaleFactor;
		
	}
	
	public ObjectParams(int id, String name, String type, String geometryType, float radius, String filename, PositionData pd, MaterialParams mp, float scaleFactor)
	{
		this.setId(id);
		this.name = name;
		this.type = type;
		this.geometryType = geometryType;
		this.radius = radius;
		
		if(filename != null)
			this.file = new File(filename);
		
		if(pd != null)
			this.pd = pd;
		
		this.mp = mp;
		
		this.scaleFactor = scaleFactor;
	}
	
	public ObjectParams(int id, String name, String type, String geometryType, ShapeData sd, MaterialParams mp)
	{
		this.setId(id);
		this.name = name;
		this.type = type;
		this.geometryType = geometryType;
		
		if(sd != null)
			this.sd = sd;
			
		this.mp = mp;
	}
	
	/**
	 * Sets the name.
	 * 
	 * @param name	a string representing the name of the shape
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Gets the name.
	 * 
	 * @return the string which represents the name of the shape
	 */
	public String getName()
	{
		return this.name;
	}
	
	
	/**
	 * Sets the shape's type.
	 * 
	 * @param type	the type
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	
	/**
	 * Gets the shape's type.
	 * 
	 * @return 		the type
	 */
	public String getType()
	{
		return this.type;
	}
	
	/**
	 * Sets the shape's type.
	 * 
	 * @param type	the type
	 */
	public void setGeometryType(String geometryType)
	{
		this.geometryType = geometryType;
	}
	
	/**
	 * Gets the shape's type.
	 * 
	 * @return 		the type
	 */
	public String getGeometryType()
	{
		return this.geometryType;
	}
	
	/**
	 * Sets the input file for the shape.
	 * 
	 * @param file the file path
	 */
	public void setFile(File file)
	{
		this.file = file;
	}
	
	/**
	 * Gets the input file which defines the shape.
	 * 
	 * @return 	the file path
	 */
	public File getFile()
	{
		return this.file;
	}
	
	/**
	 * Gets the input file's extension.
	 * 
	 * @param afile	the input file
	 * 
	 * @return		the file's extension
	 */
	public String getFileExtension(File afile) 
	  {
		  String extension = afile.getName();
		  
		  System.out.println(extension);
		  
		  
		  int dotPos = extension.lastIndexOf(".");
		  extension = extension.substring(dotPos+1);
		  
		  System.out.println(extension);
		  
		  return extension;
	  }
	

	
	/**
	 * Sets the radius of this shape if it is spherical.
	 * 
	 * @param radius	the radius
	 */
	public void setRadius(float radius)
	{
		this.radius = radius;
	}
	
	/**
	 * Gets the shape's radius.
	 *  
	 * @return the radius
	 */
	public float getRadius()
	{
		return this.radius;
	}
	
	/**
	 * Checks if the y axis is visible.
	 * 
	 * @return this axis' visibility
	 */
	public boolean isyAVisible()
	{
		return this.yAVisible;
	}
	
	/**
	 * Sets the visibility of the y axis.
	 * @param visible	the axis' visibility
	 */
	public void setyAVisible(boolean visible)
	{
		this.yAVisible = visible;
	}
	
	/**
	 * Checks if the z axis is visible.
	 * 
	 * @return this axis' visibility
	 */
	public boolean iszAVisible()
	{
		return this.zAVisible;
	}
	
	/**
	 * Sets the visibility of the z axis.
	 * @param visible	the axis' visibility
	 */
	public void setzAVisible(boolean visible)
	{
		this.zAVisible = visible;
	}
	
	/**
	 * Checks if the x axis is visible.
	 * 
	 * @return this axis' visibility
	 */
	public boolean isxAVisible()
	{
		return this.xAVisible;
	}
	
	/**
	 * Sets the visibility of the x axis.
	 * @param visible	the axis' visibility
	 */
	public void setxAVisible(boolean visible)
	{
		this.xAVisible = visible;
	}
	
	/**
	 * Adds a pointing arrow specification to this data structure.
	 * 
	 * @param color					the arrow's color
	 * @param destinationObject		the destination object
	 */
/*	public void addPtrArrow(String color, VizObject destinationObject)
	{
		
		colorArray.add(color);
		
		objectArray.add(destinationObject);
		
	}
*/	
	/**
	 * Gets the array of pointing arrows' color.
	 * 
	 * @return the array of colors as strings
	 */
	public ArrayList<String> getColorArray()
	{
		return this.colorArray;
	}
	
	/**
	 * Gets the array of pointing arrows' 3d models.
	 * 
	 * @return the array of objects
	 */
/*	public ArrayList<VizObject> getObjectArray()
	{
		return this.objectArray;
	}
*/	
	/**
	 * Sets the input file for the position.
	 * 
	 * @param file	the input file that defines position
	 */
	public void setPosFile(File file)
	{
		this.posFile = file;
	}
	
	/**
	 * Gets the position input file.
	 * 
	 * @return	the input file that defines position
	 */
	public File getPosFile()
	{
		return this.posFile;
	}
	
	/**
	 * Sets the shape data parameter which defines the basic geometry/mesh of the object.
	 * .
	 * @param sd the shape data structure
	 */
	public void setShapeData(ShapeData sd)
	{
		this.sd = sd;
	}
	
	/**
	 * Gets the shape data structure.
	 * 
	 * @return the shape data structure
	 */
	public ShapeData getShapeData()
	{
		return this.sd;
	}
	
	/**
	 * Adds a trailing or non-trailing trajectory line to the object if specified.
	 * 
	 * @param vector			the array of vector which defines all the points that make up the line
	 * @param color				the line's color
	 * @param lineWidth			the line's width
	 * @param isTrailing		specifies if the line is trailing or not
	 */
/*	public void addLine(ArrayList<Vector3f> vector, ColorRGBA color, float lineWidth, boolean isTrailing)
	{
		lineArray.add(new LineObject(vector, color, lineWidth, isTrailing));
		
	}
*/	
	/**
	 * Adds a line defined by two points(start and end).
	 * 
	 * @param startPoint		the line's start point
	 * @param endPoint			the line's end point
	 * @param color				the line's color
	 * @param lineWidth			the line's width
	 */
/*	public void addLine(Vector3f startPoint, Vector3f endPoint, ColorRGBA color, float lineWidth)
	{
		lineArray.add(new LineObject(startPoint, endPoint, color, lineWidth));
		
	}
*/	
	/**
	 * Gets the trajectory lines.
	 * 
	 * @return an array of line objects
	 */
/*	public ArrayList<LineObject> getLines()
	{
		return lineArray;
	}
*/	
	/**
	 * Gets the position data structure
	 * 
	 * @return	the position data structure
	 */
/*	public PositionData getPositionData()
	{
		return this.pd;
	}
*/	
	/**
	 * Sets the position data.
	 * 
	 * @param pd	the position data
	 */
/*	public void setPositionData(PositionData pd)
	{
		this.pd = pd;
	}
*/
	/**
	 * Gets the shape's caption
	 * 
	 * @return the caption text as a string
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * Sets the caption text for this shape.
	 * 
	 * @param caption the caption  text
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * Gets the caption text's color.
	 * 
	 * @return the caption's color
	 */
	public ColorRGBA getCaptionColor() {
		return captionColor;
	}

	/**
	 * Sets the caption's color.
	 * 
	 * @param captionColor the color applied to the text
	 */
	public void setCaptionColor(ColorRGBA captionColor) {
		this.captionColor = captionColor;
	}
	
	@Override
	public String toString()
	{
		// not good because params may be null
		if(sd != null)
			return "This shape's parameters:\n" + "Name: " + name + ", Type: " + type + 
					", Radius: " + radius + ", File: " + file + "\n" + sd.toString();
		else
			return "This shape's parameters:\n" + "Name: " + name + ", Type: " + type + 
					", Radius: " + radius + ", File: " + file + "\n" + "Shape data: N/A";
	}

	public PositionData getPositionData() {
		return pd;
	}

	public void setPositionData(PositionData pd) {
		this.pd = pd;
	}

	public void setMaterialParameters(MaterialParams mp) {
		this.mp = mp;
	}

	public MaterialParams getMaterialParameters() {
		return mp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getScale() {
		// TODO Auto-generated method stub
		return scaleFactor;
	}
	
}
