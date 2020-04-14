package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JOptionPane;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;


import esa.esac.Rosetta.Visualization.Graphics.Landmark;
import esa.esac.Rosetta.Visualization.Graphics.SPCObject;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;

public class TrajectoryLineReader {
	
	private Timestamp start, end;
	
	public TrajectoryLineReader(VizObject vizObj, Date startDate, Date endDate)
	{
		Vector3f sp = null, dp = null;
		
		try {
			start = new Timestamp(startDate.getTime());
			end = new Timestamp(endDate.getTime());
			
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_trj_line WHERE vo_id = " + vizObj.getId());
			
			// Creating a variable to execute the query
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				//System.out.println("WRITING TRAJECTORY LINE");
				
				PreparedStatement getTrajectoryStatement = 
					ConnectToDatabase.con.prepareStatement("SELECT pos_x, pos_y, pos_z " +
							"FROM rosetta_pos WHERE vo_id = " + vizObj.getId() +" AND (pos_time = '" + start + 
							"' OR '" + end + "')");
				//PreparedStatement getTrajectoryStatement = ConnectToDatabase.con.prepareStatement
				//("SELECT pos_x, pos_y, pos_z FROM rosetta_pos WHERE vo_id = " + vizObj.getId());
				
				ResultSet getTrajectoryResult = getTrajectoryStatement.executeQuery();
				
				if(getTrajectoryResult.next())
				{
					sp = new Vector3f(getTrajectoryResult.getFloat("pos_x"), 
							getTrajectoryResult.getFloat("pos_y"), getTrajectoryResult.getFloat("pos_z"));
					//System.out.println("THE TRAJECTOY LINE START POSITION: " + sp);
				}
				
				if(getTrajectoryResult.next())
				{
					dp = new Vector3f(getTrajectoryResult.getFloat("pos_x"), 
							getTrajectoryResult.getFloat("pos_y"), getTrajectoryResult.getFloat("pos_z"));
				
					//System.out.println("THE TRAJECTOY LINE END POSITION: " + dp);
				}
				
				vizObj.addTrajectoryLine(sp, dp, new ColorRGBA(result.getFloat("colorR"), 
						result.getFloat("colorG"), result.getFloat("colorB"), result.getFloat("colorA")), result.getFloat("width"));
				//vizObj.addLine(trajectory, color, width, trailing)
				
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
