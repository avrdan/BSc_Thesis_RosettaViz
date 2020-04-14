package esa.esac.Rosetta.Visualization.Graphics;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

import esa.esac.Rosetta.Visualization.GlobalTools;

/**
 * Represents the boresight of an instrument.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class Boresight {
	
	private Line line;
	private Geometry lineGeom;
	private Node lineNode;
	
	/**
	 * Do not use this constructor. For internal use only. 
	 */
	public Boresight()
	{
		
	}
	
	/**
	 * Creates the boresight object.
	 * 
	 * @param start 		the point which represents the origin, usually the origin of the entity which the boresight is added to
	 * @param direction		the direction of the boresight
	 * @param length		the fixed length of the boresight
	 * @param color			the line's color
	 * @param lineWidth		the line's width
	 */
	public Boresight(Vector3f start, Vector3f direction, float length, ColorRGBA color, float lineWidth)
	{
		line = new Line(start, direction.normalizeLocal().multLocal(length).negateLocal());
		line.setStatic();
		line.setLineWidth(lineWidth);
		
		lineGeom = new Geometry("Boresight Line", line);
		
		Material mat = new Material(GlobalTools.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
				
		lineGeom.setMaterial(mat);
		
		lineNode = new Node("Line: " + "color - " + color.toString() + "width -  " + lineWidth + " boresight");
		lineNode.attachChild(lineGeom);		
	}
	
	/**
	 * Gets the node of this boresight.
	 * 
	 * @return the line's node
	 */
	public Node getNode()
	{
		return lineNode;
	}
}
