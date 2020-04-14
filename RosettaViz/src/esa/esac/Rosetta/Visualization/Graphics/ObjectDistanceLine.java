package esa.esac.Rosetta.Visualization.Graphics;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

import esa.esac.Rosetta.Visualization.GlobalTools;

/**
 * Represents 2 types of lines: "start - end" and "start - destination object" (2 point lines)
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class ObjectDistanceLine {
	
	private Line line;
	private Geometry lineGeom;
	private Node lineNode;
	private VizObject destObj;
	
	/**
	 * Do not use.
	 */
	public ObjectDistanceLine()
	{
		
	}
	
	/**
	 * Creates a 2-point line using a start and an end vector.
	 * 
	 * @param start			the start point
	 * @param end			the end point
	 * @param color			this line's color
	 * @param lineWidth		this line's width
	 */
	public ObjectDistanceLine(Vector3f start, Vector3f end, ColorRGBA color, float lineWidth)
	{
		line = new Line(start, end);
		line.setStreamed();
		line.setLineWidth(lineWidth);
		
		lineGeom = new Geometry("Object Distance Line", line);
		
		Material mat = new Material(GlobalTools.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		
		
		
		lineGeom.setMaterial(mat);
		
		lineNode = new Node("Line: " + "color - " + color.toString() + "width -  " + lineWidth + "obj dst line");
		lineNode.attachChild(lineGeom);
		
	}
	
	/**
	 * Creates a line that represents the distance between a start point and a destination object.
	 * 
	 * @param start			the start point
	 * @param destObj		the destination object
	 * @param color			this line's color
	 * @param lineWidth		this line's width
	 */
	public ObjectDistanceLine(Vector3f start, VizObject destObj, ColorRGBA color, float lineWidth)
	{
		this.destObj = destObj;
		System.out.println("DEST OBJECT FOR DISTANCE LINE COORDS:\n" + destObj.getPosition());
		line = new Line(start, destObj.getPosition());
		line.setStreamed();
		line.setLineWidth(lineWidth);
		
		lineGeom = new Geometry("Object Distance Line", line);
		
		Material mat = new Material(GlobalTools.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		
		
		
		lineGeom.setMaterial(mat);
		
		lineNode = new Node("Line: " + "color - " + color.toString() + "width -  " + lineWidth + "obj dst line");
		lineNode.attachChild(lineGeom);
		
	}
	
	/**
	 * Gets the destination object.
	 * 
	 * @return the destination object
	 */
	public VizObject getDestObj()
	{
		return destObj;
	}
	
	/**
	 * Get the line (as a classic line).
	 * 
	 * @return a line object
	 */
	public Line getLine()
	{
		return line;
	}
	
	/**
	 * Gets this line's node.
	 * 
	 * @return this line's node
	 */
	public Node getNode()
	{
		return lineNode;
	}
	
	/**
	 * Updates the line's points.
	 * 
	 * @param start		the start point
	 * @param destObj	the destination object
	 */
	public void updateLine(Vector3f start, VizObject destObj)
	{
		line.updatePoints(start.negateLocal(), destObj.getPosition());
		line.updateBound();
		line.updateCounts();
		lineGeom.setMesh(line);
		
	}
}
