package esa.esac.Rosetta.Visualization.Graphics;

import java.util.ArrayList;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Mesh.Mode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import esa.esac.Rosetta.Visualization.GlobalTools;



/**
 * Represents a line objects which can be either trailing or non-trailing.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class TrajectoryLine
{

	private Mesh m;
	private Geometry geom;
	private boolean isTrailing;
	private ArrayList<Vector3f> vert_arr;
	private Material mat;
	private Node lineNode;
	
    /**
     * Constructs the line using an array of points.
     * 
     * @param vert_arr			the array of vertices which specify the lines mesh
     * @param color				the line's color
     * @param lineWidth			the line's width
     * @param isTrailing		toggles between a trailing and non-trailing line
     */
    public TrajectoryLine(ArrayList<Vector3f> vert_arr, ColorRGBA color, float lineWidth, boolean isTrailing)
	{
    	this.vert_arr = vert_arr;
    	Vector3f [] verts = new Vector3f[vert_arr.size()];

	
		for(int i = 0; i < vert_arr.size(); i++)
		{
			
			verts[i] = new Vector3f(vert_arr.get(i).getX(), vert_arr.get(i).getY(), vert_arr.get(i).getZ());
		}
		
    	
		m = new Mesh();
		m.setMode(Mode.LineStrip);
		m.setLineWidth(lineWidth);
		
		m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(verts));
		m.updateBound();
		
		geom = new Geometry("Line mesh", m);
		
		
		mat = new Material(GlobalTools.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		
		
		geom.setMaterial(mat);
		
		this.isTrailing = isTrailing;
		
		setTrailing(isTrailing);
		
		lineNode = new Node("Line: " + "color - " + color.toString() + "width -  " + lineWidth + "trailing - " + isTrailing);
		lineNode.attachChild(geom);
	}
    
    /**
     * Constructs a line with a pre-specified start and end point.
     * 
     * @param startPoint		the start point
     * @param destPoint			the destination point
     * @param color				the line's color
     * @param lineWidth			the "thickness" of the line
     */
    public TrajectoryLine(Vector3f startPoint, Vector3f destPoint, ColorRGBA color, float lineWidth)
    {
    	m = new Mesh();
    	
    	m.setMode(Mode.LineStrip);
		m.setLineWidth(lineWidth);
		
		Vector3f [] verts = new Vector3f[2];
		verts[0] = startPoint;
		verts[1] = destPoint;
		
		m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(verts));
		m.updateBound();
		
		geom = new Geometry("Line mesh", m);
		
		
		mat = new Material(GlobalTools.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
		
		
		geom.setMaterial(mat);
		
		setTrailing(false);
		
		lineNode = new Node("Line: " + "color - " + color.toString() + "width -  " + lineWidth + "trailing - " + isTrailing);
		lineNode.attachChild(geom);
    	
    }
    
    /**
     * Updates the two-point line.
     * 
     * @param startPoint the start point
     * @param destPoint  the end point
     */
    public void updateLine(Vector3f startPoint, Vector3f destPoint)    {
    	
    	Vector3f [] verts = new Vector3f[2];
    	verts[0] = startPoint;
    	verts[1] = destPoint;
		
		m.clearBuffer(Type.Position);
		m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(verts));
		m.updateBound();
		m.updateCounts();
		m.setStreamed();
		
    	geom.setMesh(m);
    }
    
    /**
     * Updates the line with a new vector every time it is needed(this vector is added to the array of points and the lines is recomputed).
     * 
     * @param vector
     */
    public void updateLine(Vector3f vector)
    {
    	vert_arr.add(vector);
    	Vector3f [] verts = new Vector3f[vert_arr.size()];
		
		for(int i = 0; i < vert_arr.size(); i++)
		{
			
			verts[i] = new Vector3f(vert_arr.get(i).getX(), vert_arr.get(i).getY(), vert_arr.get(i).getZ());
		}
		
		m.clearBuffer(Type.Position);
		m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(verts));
		m.updateBound();
		m.updateCounts();
		m.setStreamed();
		
    	geom.setMesh(m);
    }
    
    /**
     * Resets the array of points (this is used to repeat the simulation without glitches).
     */
    public void reset()
    {
    	vert_arr.removeAll(vert_arr);
    	
    }
    
    /**
     * Gets the mesh of this line.
     * 
     * @return the line mesh
     */
    public Mesh getMesh()
    {
    	return this.m;
    }
    
    /**
     * Gets the geometry of this line.
     * 
     * @return the line geometry
     */
    public Geometry getGeometry()
    {
    	return this.geom;
    }

	/**
	 * Retrieves the line trailing flag.
	 * 
	 * @return a boolean flag used to specify if the line is trailing
	 */
	public boolean isTrailing() {
		return isTrailing;
	}

	/**
	 * Sets the line mode (normal, trailing).
	 * 
	 * @param isTrailing a boolean value which specified the trailing capability of this line
	 */
	public void setTrailing(boolean isTrailing) {
		this.isTrailing = isTrailing;
	}
	
	/**
	 * Gets the line's node.
	 * 
	 * @return the line's node
	 */
	public Node getNode()
	{
		return lineNode;
	}
}