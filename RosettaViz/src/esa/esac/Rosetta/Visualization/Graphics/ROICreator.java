package esa.esac.Rosetta.Visualization.Graphics;

import java.sql.Array;
import java.util.ArrayList;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.Geometry.CustomGeometryCreator;

public class ROICreator {
	private Line line;
	private ArrayList<Geometry> roiGeomArr;
	private Node roiNode;
	
	
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
	public ROICreator(Array array, ColorRGBA color)
	{
		roiGeomArr = new CustomGeometryCreator().getGeom();
		
		Material mat = new Material(GlobalTools.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
    
		roiNode = new Node("ROI: " + "color - " + color.toString() + "object:");
		
		for(Geometry g : roiGeomArr)
		{
			g.setMaterial(mat);
			roiNode.attachChild(g);
		}
		
				
	}
	
	/**
	 * Returns the landmark node.
	 * 
	 * @return the landmark node
	 */
	public Node getNode()
	{
		return roiNode;
	}
}
