package esa.esac.Rosetta.Visualization.DataStructure;

import java.io.File;
import java.util.ArrayList;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import esa.esac.Rosetta.Visualization.Math.TriangleI;

/**
 * Represents a data structure holding all of the shape data (triangle indices, vertices etc...).
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class ShapeData {
	private int total_points, total_triangles;
	private ArrayList<TriangleI> triangles_i;
	private ArrayList<Vector3f> vertices;
	
	private File modelFile;
	
	public ShapeData(int total_points, int total_triangles, ArrayList<Vector3f> vertices, ArrayList<TriangleI> triangles_i)
	{
		this.total_points = total_points;
		this.total_triangles = total_triangles;
		this.vertices = vertices;
		this.triangles_i = triangles_i;
	}
	
	public ShapeData(String filename)
	{
		this.modelFile = new File(filename);
	}
	
	// getters
	/**
	 * Gets the total points of the future mesh.
	 * 
	 * @return the total points
	 */
	public int getTotalPoints()
	{
		return total_points;
	}
	
	/**
	 * Gets the total triangles of the future mesh.
	 * 
	 * @return the total triangles
	 */
	public int getTotalTriangles()
	{
		return total_triangles;
	}
	
	/**
	 * Gets the vertices of the future mesh.
	 * 
	 * @return the array of vertices
	 */
	public ArrayList<Vector3f> getVertices()
	{
		return vertices;
	}
	
	/**
	 * Gets the triangle indices of the future mesh.
	 * 
	 * @return the triangle indices
	 */
	public ArrayList<TriangleI> getTriangleIndices()
	{
		return triangles_i;
	}
	

	
	// setters
	/**
	 * Sets the total points of the future mesh.
	 * 
	 * @param total_points the number of points in the mesh
	 */
	public void setTotalPoints(int total_points)
	{
		this.total_points = total_points;
	}
	
	/**
	 * Sets the total triangles of the future mesh.
	 * 
	 * @param total_triangles the number of triangles in the mesh
	 */
	public void setTotalTriangles(int total_triangles)
	{
		this.total_triangles = total_triangles;
	}
	
	/**
	 * Sets the vertices of the future mesh.
	 * 
	 * @param vertices an array of vertices
	 */
	public void setVertices(ArrayList<Vector3f> vertices)
	{
		this.vertices = vertices;
	}
	

	/**
	 * Sets the triangle indices of the future mesh.
	 * 
	 * @param triangles_i an array of triangle indices
	 */
	public void setTriangleIndices(ArrayList<TriangleI> triangles_i)
	{
		this.triangles_i = triangles_i;
	}
	
	@Override
	public String toString()
	{
		return "Shape data:\nTotal points: " + total_points + ", Total triangles: " + total_triangles +
				 "\nVertices: " + vertices.toString() + "\nTriangle indices: " + triangles_i.toString();
	}

	public File getModelFile() {
		return modelFile;
	}

	public void setModelFile(String inputFile) {
		this.modelFile = new File(inputFile);
	}
	
}
