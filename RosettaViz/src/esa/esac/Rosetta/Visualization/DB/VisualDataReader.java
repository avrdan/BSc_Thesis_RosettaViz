package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jme3.math.Vector3f;

import esa.esac.Rosetta.Visualization.DataStructure.ShapeData;
import esa.esac.Rosetta.Visualization.Math.TriangleI;

public class VisualDataReader {
	private ArrayList<TriangleI> triangles_i;
	private ArrayList<Vector3f> vertices;
	private int total_points, total_triangles;
	private ShapeData sd;
	
	private String modelFile;
	public VisualDataReader(int id)
	{
		vertices = new ArrayList<Vector3f>();
		triangles_i = new ArrayList<TriangleI>();
		try {
			PreparedStatement statement1 = ConnectToDatabase.con.prepareStatement("SELECT " +
					"x_coord, y_coord, z_coord FROM rosetta_vo_vertex " +
					"WHERE vo_id = '"+ id +"'");
			PreparedStatement statement2 = ConnectToDatabase.con.prepareStatement("SELECT " +
					"point_index1, point_index2, point_index3 FROM rosetta_vo_trg_i " +
					"WHERE vo_id = " + id);

			PreparedStatement statement3 = ConnectToDatabase.con.prepareStatement("SELECT " +
					"total_points, total_primitives, model_file FROM rosetta_vo_data " +
					"WHERE vo_id = " + id);
			
			ResultSet result1 = statement1.executeQuery();
			ResultSet result2 = statement2.executeQuery();
			ResultSet result3 = statement3.executeQuery();
			
			while(result1.next())
			{
				vertices.add(new Vector3f(result1.getFloat(1), result1.getFloat(2), result1.getFloat(3)));
				
			}
			//System.out.println("VERTICEEEEEEEEEEEEEEEEEEEES:" + vertices.size());
			
			while(result2.next())
			{
				triangles_i.add(new TriangleI(result2.getInt(1)-1, result2.getInt(2)-1, result2.getInt(3)-1));
				
			}
			
			while(result3.next())
			{
				total_points = result3.getInt(1);
				total_triangles = result3.getInt(2);
				
				if(result3.getString(3) != null)
					modelFile = result3.getString(3);
				

			}
			
			if(total_points == 0 || total_triangles == 0 || vertices == null || triangles_i == null)
			{
				if (modelFile != null)
					sd = new ShapeData(modelFile);
				/*else
				{
					JOptionPane.showMessageDialog(null,
						    "Not enough information!",
						    "Object parameters invalid",
						    JOptionPane.ERROR_MESSAGE);
				}*/
					
			}
			else
			{
				//ShapeData sd = new ShapeData();
				sd = new ShapeData(total_points, total_triangles, vertices, triangles_i);
				
			}
			
			
			/*
			PreparedStatement statement0 = ConnectToDatabase.con.prepareStatement("SELECT vo_id FROM rosetta_vo");
			
			
			
			// Creating a variable to execute the query
			ResultSet result0 = statement0.executeQuery();
			
			while(result0.next())
			{
				PreparedStatement statement1 = ConnectToDatabase.con.prepareStatement("SELECT " +
						"x_coord, y_coord, z_coord FROM rosetta_vo_vertex " +
						"WHERE vo_id = '"+result0.getInt(1)+"'");
				PreparedStatement statement2 = ConnectToDatabase.con.prepareStatement("SELECT " +
						"point_index1, point_index2, point_index3 FROM rosetta_vo_trg_i " +
						"WHERE vo_id = " + result0.getInt(1));

				PreparedStatement statement3 = ConnectToDatabase.con.prepareStatement("SELECT " +
						"total_points, total_triangles FROM rosetta_vo_data " +
						"WHERE vo_id = " + result0.getInt(1));
				
				ResultSet result1 = statement1.executeQuery();
				ResultSet result2 = statement2.executeQuery();
				ResultSet result3 = statement3.executeQuery();
				
				while(result1.next())
				{
					vertices.add(new Vector3f(result1.getFloat(1), result1.getFloat(2), result1.getFloat(3)));
				}
				
				while(result2.next())
				{
					triangles_i.add(new TriangleI(result2.getInt(1), result2.getInt(2), result2.getInt(3)));
					
				}
				
				while(result3.next())
				{
					total_points = result3.getInt(1);
					total_triangles = result3.getInt(2);
				}
				
				if(total_points == 0 || total_triangles == 0 || vertices == null || triangles_i == null)
				{
					// do something
				}
				else
				{
					//ShapeData sd = new ShapeData();
					sd = new ShapeData(total_points, total_triangles, vertices, triangles_i);
				}

			}
			
*/						
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
