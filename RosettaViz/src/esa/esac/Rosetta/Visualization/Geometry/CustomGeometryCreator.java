package esa.esac.Rosetta.Visualization.Geometry;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import esa.esac.Rosetta.Visualization.DataStructure.ShapeData;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;
import esa.esac.Rosetta.Visualization.Math.MathVect;
import esa.esac.Rosetta.Visualization.Math.TriangleI;



/**
 * Represents a custom geometry of a 3d object(VizObject). 
 * It always needs the same kind of data to create any type of custom geometry(vertices and triangle indices).
 * 
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class CustomGeometryCreator extends AbstractGeometry{
	private int total_points, total_triangles;
	private ArrayList<Vector3f> vertices;
	private ArrayList<TriangleI> triangles_i;
	private Vector3f[] normals;
	Mesh m;
	
	private ArrayList<Integer> primitiveRoiIndices;
	
	/**
	 *  Used by the  geometry factory(actually calls the constructor of the abstract geometry afterwards). For internal use only.
	 */
	public CustomGeometryCreator()
	{
		super();
	}
	
//	@SuppressWarnings("unchecked")
//	public CustomGeometryCreator(Array array, VizObject obj)
//	{
//		//array.g
//		//createMesh(array.getArray());
//		try {
//			ArrayList<Integer> primitiveRoiIndices = (ArrayList<Integer>) array.getArray();
//			createMesh(primitiveRoiIndices, obj);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	private void createMesh(ArrayList<Integer> primitiveRoiIndices, VizObject obj) {
//		ArrayList<Geometry> objGeomArr = obj.getGeometry();
//		
//		
//		
//		for(Geometry g : objGeomArr)
//		{
//			Mesh m = g.getMesh();
//			
//			for(int i = 0; i < m.getTriangleCount(); i++)
//			{
//				m.getTriangle(i, indices);
//				m.getTriangle(i, v1, v2, v3);
//				
//				m.getTriangle(index, tri);
//				m.getTr
//				Triangle tri;
//				tri.get
//				m.setBuffer(VertexBuffer.Type.)
//			}
//		}
//		
//		Vector3f [] vert_arr = new Vector3f[primitiveRoiIndices.size()*3];
//		
//		int[] indices = new int[3*primitiveRoiIndices.size()];
//		
//		for(int i = 0; i < primitiveRoiIndices.size(); i++)
//		{
//			//indices[i] = 
//			//vert_arr[i] = new Vector3f(vertices.get(i).getX(), vertices.get(i).getY(), vertices.get(i).getZ());
//		}
//	}

	/**
	 * @param sd the geometry data which is used to construct the mesh
	 */
	public void setGeometryData(ShapeData sd)
	{
		
		
		this.total_points = sd.getTotalPoints();
		this.total_triangles = sd.getTotalTriangles();
		this.vertices = sd.getVertices();
		this.triangles_i = sd.getTriangleIndices();
	}
	
	/*public Geometry getGeometry()
	{
		geom = new Geometry("Custom mesh", m);
		
		
		return geom;
	}*/
	
	/**
	 * Creates the mesh which will be transformed into geometry.
	 */
	public void createMesh()
	{
		
		m = new Mesh();
		
		Vector3f [] vert_arr = new Vector3f[total_points];
			
		int[] indexes = new int[3*total_triangles];
		
	
		for(int i = 0; i < total_points; i++)
		{
			
			vert_arr[i] = new Vector3f(vertices.get(i).getX(), vertices.get(i).getY(), vertices.get(i).getZ());
		}
		
		for (int i = 0; i < total_triangles; i++) {
			indexes[i*3] = triangles_i.get(i).getP1_i();
			indexes[i*3 + 1] = triangles_i.get(i).getP2_i();
			indexes[i*3 + 2] = triangles_i.get(i).getP3_i();
		}
	
		
		m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vert_arr));
		m.setBuffer(Type.Index,    1, BufferUtils.createIntBuffer(indexes));
		
		ObjCalcNormals(m);
		
		m.setBuffer(Type.Normal,    3, BufferUtils.createFloatBuffer(normals));
		m.updateBound();
	}
	
	/**
	 * Computes the normals for this custom geometry(the normals are added to the mesh).
	 * 
	 * @param p_object the mesh which the normals are computed for
	 */
	public void ObjCalcNormals(Mesh p_object)
    {
    	
    	int l_connections_qty[] = new int [2000];
    	
    	
    	// some local vectors   	    	
    	Vector3f l_vect1 = new Vector3f(); 
    	Vector3f l_vect2 = new Vector3f();
    	Vector3f l_vect3 = new Vector3f();
    	Vector3f l_vect_b1 = new Vector3f(); 
    	Vector3f l_vect_b2 = new Vector3f(); 
    	Vector3f l_normal = new Vector3f(); 
    	
    	
    	normals = new Vector3f[p_object.getVertexCount()];
    	
    	System.out.println("No. triangles: " + p_object.getTriangleCount());
    	System.out.println("No. verteces: " + p_object.getVertexCount());
    	//System.out.println("No. verteces: " + total_points);
    	
    	//Resetting the vertices' normals...
    	for (int i = 0; i < p_object.getVertexCount(); i++)
    	{
    		normals[i] = new Vector3f(0, 0, 0);
    		
    		l_connections_qty[i] = 0;
    		
    		
    	}
    	
    	
    	for (int i = 0; i < p_object.getTriangleCount(); i++)
    	{
    		
    		
    		p_object.getTriangle(i, l_vect1, l_vect2, l_vect3);
    		
    		
    		//System.out.println("index: " + i + ", v1: " + l_vect1 + ", v2: " + l_vect2 + ", v3: " + l_vect3);
    		 		
    		// Polygon Normal Calculation
    		
    		MathVect.VectCreate(l_vect1, l_vect2, l_vect_b1);
    		MathVect.VectCreate(l_vect1, l_vect3, l_vect_b2);
    		l_normal = MathVect.VectCrossProduct(l_vect_b1, l_vect_b2, l_normal);   		
    		MathVect.VectNormalize(l_normal);
    		
    		  		
    		int point1_i = p_object.getIndexBuffer().get(i*3);
    		int point2_i = p_object.getIndexBuffer().get(i*3 + 1);
    		int point3_i = p_object.getIndexBuffer().get(i*3 + 2);
    		
    		  		
    		
    		//System.out.println("Point 1: " + point1_i + " Point 2: " + point2_i + " Point 3: " + point3_i);
    		
    		
    		
    		l_connections_qty[point1_i]++;
    		l_connections_qty[point2_i]++;
    		l_connections_qty[point3_i]++;
    		
    		normals[point1_i].setX(normals[point1_i].getX() + l_normal.getX());
    		normals[point1_i].setY(normals[point1_i].getY() + l_normal.getY());
    		normals[point1_i].setZ(normals[point1_i].getZ() + l_normal.getZ());
    		
    		normals[point2_i].setX(normals[point2_i].getX() + l_normal.getX());
    		normals[point2_i].setY(normals[point2_i].getY() + l_normal.getY());
    		normals[point2_i].setZ(normals[point2_i].getZ() + l_normal.getZ());
    		
    		normals[point3_i].setX(normals[point3_i].getX() + l_normal.getX());
    		normals[point3_i].setY(normals[point3_i].getY() + l_normal.getY());
    		normals[point3_i].setZ(normals[point3_i].getZ() + l_normal.getZ());
    	}
    	
    	for (int i = 0; i < p_object.getVertexCount(); i++)
    	{
    		if (l_connections_qty[i] > 0)
    		{
    			normals[i].setX(normals[i].getX() / l_connections_qty[i]);
    			normals[i].setY(normals[i].getY() / l_connections_qty[i]);
    			normals[i].setZ(normals[i].getZ() / l_connections_qty[i]);
    		}
    	}
    }





	
	/**
	 * Sets the source from which this custom geometry is read. 
	 * The geometry can be created from different types of input data.
	 */
	/*public void setSource() {
		getShapeParams().setShapeData(VisualDataReader.getShapeData());
		
	}*/


	@Override
	public void setParameters(ObjectParams sp) {
		setShapeParams(sp);
		
		//setSource();
		setGeometryData(getShapeParams().getShapeData());
		createMesh();
		
		//Geometry g = new Geometry(getShapeParams().getName(), m);
		setGeom(new Geometry(getShapeParams().getName(), m));
		
	}


	/*@Override
	public Geometry create() {
		setSource();
		setGeometryData(getShapeParams().getShapeData());
		createMesh();
		setGeom(new Geometry(getShapeParams().getName(), m));
		return getGeom();
	}*/
}
