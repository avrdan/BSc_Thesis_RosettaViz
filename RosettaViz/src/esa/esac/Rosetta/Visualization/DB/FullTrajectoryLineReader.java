package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import esa.esac.Rosetta.Visualization.Graphics.VizObject;

public class FullTrajectoryLineReader {
	
	private Timestamp start, end;
	ArrayList<Vector3f> line, trail;
	
	public FullTrajectoryLineReader(VizObject vizObj)
	{
		line = new ArrayList<Vector3f>();
		trail = new ArrayList<Vector3f>();
		try {
			
			
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_trj_line WHERE vo_id = " + vizObj.getId());
			
			// Creating a variable to execute the query
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				if(result.getBoolean("trailing"))
				{
					//System.out.println("WRITING TRAILING LINE");
					trail.add(vizObj.getPosition());
					vizObj.addLine(trail, new ColorRGBA(result.getFloat("colorR"), 
							result.getFloat("colorG"), result.getFloat("colorB"), result.getFloat("colorA")), result.getFloat("width"), 1);
					trail.clear();
				}
				else
				{
					
					//System.out.println("WRITING TRAJECTORY LINE");
					
					PreparedStatement trjStmt = 
						ConnectToDatabase.con.prepareStatement("SELECT pos_x, pos_y, pos_z " +
								"FROM rosetta_trj_pos WHERE vo_id = " + vizObj.getId());
					//PreparedStatement getTrajectoryStatement = ConnectToDatabase.con.prepareStatement
					//("SELECT pos_x, pos_y, pos_z FROM rosetta_pos WHERE vo_id = " + vizObj.getId());
					
					ResultSet trjResult = trjStmt.executeQuery();
					
					while(trjResult.next())
					{
						line.add(new Vector3f(trjResult.getFloat("pos_x"),
								trjResult.getFloat("pos_y"), trjResult.getFloat("pos_z")));
					}
					
					vizObj.addLine(line, new ColorRGBA(result.getFloat("colorR"), 
							result.getFloat("colorG"), result.getFloat("colorB"), result.getFloat("colorA")), result.getFloat("width"), 0);
					line.clear();
				}
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				    "Could not retrieve desired data.",
				    "Database query error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
