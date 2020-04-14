package esa.esac.Rosetta.Visualization.Geometry;

import java.util.ArrayList;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;
import esa.esac.Rosetta.Visualization.Universe;

public class MaskCreator extends AbstractGeometry{
	//private int total_points;
	private ArrayList<Vector3f> vertices;
	private ArrayList<Vector3f> indices;
	Mesh m;
	private int width, height;
	/**
	 *  Used by the  geometry factory(actually calls the constructor of the abstract geometry afterwards). For internal use only.
	 */
	public MaskCreator()
	{
		super();
	}
	
	/**
	 * @param sd the geometry data which is used to construct the mesh
	 */
	public void setGeometryData()
	{	
		width = Universe.getApplication().getCamera().getWidth();
		height = Universe.getApplication().getCamera().getHeight();
		//this.total_points = sd.getTotalPoints();	
		//this.vertices = sd.getVertices2D();
		
		 // Vertex positions in space
        //vertices = new Vector3f[4];
        vertices = new ArrayList<Vector3f>(8);
        
        vertices.add(new Vector3f(0,height, 0));
        vertices.add(new Vector3f(width, height, 0));
        vertices.add(new Vector3f(width, 0, 0));
        vertices.add(new Vector3f(0, 0, 0));
        
        //vertices.add(new Vector3f(width - 200,height - 100,0));
        //vertices.add(new Vector3f(width - 100,height - 100,0));
        //vertices.add(new Vector3f(width - 100,height - 200,0));
        //vertices.add(new Vector3f(width - 200,height - 200,0));
        
       /* vertices.add(new Vector3f(300, height - 300,0));
        vertices.add(new Vector3f(width - 300, height - 300,0));
        vertices.add(new Vector3f(width - 300, 300,0));
        vertices.add(new Vector3f(300, 300,0));
        */
        
//        vertices.add(new Vector3f(200, 400,0));
//        vertices.add(new Vector3f(400, 400,0));
//        vertices.add(new Vector3f(400, 200,0));
//        vertices.add(new Vector3f(200, 200,0));
        vertices.add(new Vector3f(50, 430,0));
        vertices.add(new Vector3f(590, 430,0));
        vertices.add(new Vector3f(590, 50,0));
        vertices.add(new Vector3f(50, 50,0));
        
        /*vertices[0] = new Vector3f(0,0,0);
        vertices[1] = new Vector3f(3,0,0);
        vertices[2] = new Vector3f(0,3,0);
        vertices[3] = new Vector3f(3,3,0);*/
        
        // indices
        indices = new ArrayList<Vector3f>(8);
        
        indices.add(new Vector3f(1, 2, 5));
        indices.add(new Vector3f(5, 2, 6));
        indices.add(new Vector3f(2, 6, 3));
        indices.add(new Vector3f(6, 3, 7));
        indices.add(new Vector3f(3, 7, 4));
        indices.add(new Vector3f(7, 4, 8));
        indices.add(new Vector3f(4, 8, 1));
        indices.add(new Vector3f(8, 1, 5));
        
        System.out.println("THE CAMERA RES IS !!!!!!!!!!! : WIDTH: " + width + " AND HEIGHT: " + height);
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
		
		Vector3f [] vert_arr = new Vector3f[8];
			
		Vector3f [] indices_arr = new Vector3f[8];
		
//		int [] ind = {1, 2, 5,  
//				      5, 2, 6,
//				      2, 6, 3,
//				      6, 3, 7,
//				      3, 7, 4,
//				      7, 4, 8,
//				      4, 8, 1,
//				      8, 1, 5};

//		int [] ind = {1, 4, 8,
//			      4, 7, 8,
//			      4, 3, 7,
//			      3, 6, 7,
//			      3, 2, 6,
//			      6, 2, 5,
//			      5, 2, 1,
//			      5, 1, 8};
		
//		int [] ind = {1, 4, 8,
//	      4, 7, 8,
//	      4, 3, 7,
//	      3, 6, 7,
//	      3, 2, 6,
//	      6, 2, 5,
//	      5, 2, 1,
//	      5, 1, 8};
		
//		int [] ind = {1, 4, 8,
//			      8, 4, 7,
//			      7, 4, 3,
//			      3, 6, 7,
//			      3, 2, 6,
//			      2, 5, 6,
//			      5, 2, 1,
//			      1, 8, 5};
		
		int [] ind = {0, 3, 7,
			      7, 3, 6,
			      6, 3, 2,
			      2, 5, 6,
			      2, 1, 5,
			      1, 4, 5,
			      4, 1, 0,
			      0, 7, 4};
						 
		
	
		for(int i = 0; i < 8; i++)
		{
			
			vert_arr[i] = new Vector3f(vertices.get(i).getX(), vertices.get(i).getY(), vertices.get(i).getZ());
			indices_arr[i] = new Vector3f(indices.get(i).getX(), indices.get(i).getY(), indices.get(i).getZ());
		}
		
		
	
		m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vert_arr));
		m.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(ind));
		m.updateBound();
		
	}
	
	@Override
	public void setParameters(ObjectParams sp) {
		setShapeParams(sp);
		
		
		setGeometryData();
		createMesh();
		
		//Geometry g = new Geometry(getShapeParams().getName(), m);
		setGeom(new Geometry(getShapeParams().getName(), m));
		
	}
	
}
