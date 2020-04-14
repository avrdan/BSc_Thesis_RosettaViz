package esa.esac.Rosetta.Visualization.Graphics;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

import esa.esac.Rosetta.Visualization.GlobalTools;

/**
 * Represents the landmarks used to denote a point of interest on a particular 3d object.
 * 
 * @author Dan Adrian Avram 
 * 
 * @version PreAlpha v0.21
 */
public class Landmark {
	private Line line;
	private Geometry lineGeom;
	private Node lineNode;
	/**
	 * Do not use.
	 */
	public Landmark()
	{
		
	}
	
	/**
	 * Constructs the landmark line. 
	 * 
	 * @param start				the start point of the line
	 * @param latitude			the latitude 
	 * @param longitude			the longitude
	 * @param length			the length of the line
	 * @param color				the color of the line
	 * @param lineWidth			the "thickness" of the line
	 */
	public Landmark(Vector3f start, float latitude, float longitude, float length, ColorRGBA color, float lineWidth)
	{
		Vector3f direction = new Vector3f();
		
		// Transformation from spherical to cartesian coordinates is needed.
		FastMath.sphericalToCartesian(new Vector3f(length, longitude, latitude), direction);
		
		line = new Line(start, direction);
		line.setStatic();
		line.setLineWidth(lineWidth);
		
		lineGeom = new Geometry("Landmark Line", line);
		
		Material mat = new Material(GlobalTools.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		
		
		
		
		
		lineGeom.setMaterial(mat);
		
		lineNode = new Node("Line: " + "color - " + color.toString() + "width -  " + lineWidth + " landmark");
		lineNode.attachChild(lineGeom);		
	}
	
	/**
	 * Returns the landmark node.
	 * 
	 * @return the landmark node
	 */
	public Node getNode()
	{
		return lineNode;
	}
}

