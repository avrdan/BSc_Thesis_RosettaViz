package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jme3.math.Vector3f;

import esa.esac.Rosetta.Visualization.DataStructure.ShapeData;
import esa.esac.Rosetta.Visualization.Math.TriangleI;

public class ROIObjectReader {
	private ArrayList<TriangleI> triangles_i;
	private ArrayList<Vector3f> vertices;
	private int total_points = 0, total_triangles = 0;
	private ShapeData sd;
	
	private String modelFile;
	public ROIObjectReader(int roi_id, int id)
	{
		vertices = new ArrayList<Vector3f>();
		triangles_i = new ArrayList<TriangleI>();
		try {
			
			
			
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT " +
					"primitive_id FROM rosetta_roi_prtv " +
					"WHERE roi_id = " + roi_id);
					
			ResultSet primitiveResult = statement.executeQuery();
			
			while(primitiveResult.next())
			{
				//System.out.println("Primitive ID: " + primitiveResult.getInt(1));
				
				PreparedStatement indicesStatement = ConnectToDatabase.con.prepareStatement("SELECT *" +
						" FROM rosetta_vo_trg_i " +
						"WHERE vo_id = " + id + " AND primitive_id = " + primitiveResult.getInt(1));
				//SELECT * FROM rosetta_vo_trg_i WHERE vo_id = 2 AND primitive_id = 1
				
				ResultSet indicesResult = indicesStatement.executeQuery();
				
				while(indicesResult.next())
				{
					//System.out.println("Index ID: " + indicesResult.getInt(1));
					
					triangles_i.add(new TriangleI(indicesResult.getInt(3)-1, indicesResult.getInt(4)-1, indicesResult.getInt(5)-1));
					total_triangles++;
					
					PreparedStatement verticesStatement = ConnectToDatabase.con.prepareStatement("SELECT " +
							"x_coord, y_coord, z_coord FROM rosetta_vo_vertex " +
							"WHERE vo_id = "+ id + " AND  vertex_id IN (" + indicesResult.getInt(3) + ","  + 
							indicesResult.getInt(3) + "," + indicesResult.getInt(3) + ")");
					
					//SELECT x_coord, y_coord, z_coord FROM rosetta_vo_vertex WHERE vo_id = 2 AND  vertex_id IN (1,2,3)
					
					ResultSet verticesResult = verticesStatement.executeQuery();
					
					while(verticesResult.next())
					{
						//System.out.println("A VERTEX: " + new Vector3f(verticesResult.getFloat(1), verticesResult.getFloat(2), verticesResult.getFloat(3)));
						vertices.add(new Vector3f(verticesResult.getFloat(1), verticesResult.getFloat(2), verticesResult.getFloat(3)));
						total_points++;
					}
						
				}
			}
			
			//System.out.println("VERTICEEEEEEEEEEEEEEEEEEEES:" + vertices.size());
			
			
			
			
			
			if(total_points == 0 || total_triangles == 0 || vertices == null || triangles_i == null)
			{
				if (modelFile != null)
					sd = new ShapeData(modelFile);
				
				
			}
			else
			{
				
				sd = new ShapeData(total_points, total_triangles, vertices, triangles_i);
				
			}
			
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				    "Could not retrieve desired data.",
				    "Database query error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
				
	}
	public ShapeData getShapeData() {
		return sd;
	}
}
