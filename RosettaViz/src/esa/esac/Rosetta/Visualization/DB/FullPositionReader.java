package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;

import javax.swing.JOptionPane;

import com.jme3.math.Vector3f;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.Universe;
import esa.esac.Rosetta.Visualization.DataStructure.PositionData;
import esa.esac.Rosetta.Visualization.DataStructure.ShapeData;
import esa.esac.Rosetta.Visualization.Math.TriangleI;

public class FullPositionReader {

	private ResultSet result;

	private int id;
	
	private Timestamp start, end;
	
	public FullPositionReader()
	{
		
	}
	
	public FullPositionReader(int id,Date startDate, Date endDate)
	{
		start = new Timestamp(startDate.getTime());
		end = new Timestamp(endDate.getTime());
		this.id = id;	
		
	
		try {
			
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT pos_x, pos_y, pos_z, pos_time " +
					"FROM rosetta_pos WHERE vo_id = " + id +" AND (pos_time BETWEEN '" + start + "' AND '" + end + "') " +
							"AND (pos_id % " + Universe.getApplication().simSpeed+") = 0");// AND (pos_id % 4 = 0)");// AND ((pos_id % 100) = 1)");
			
			result = statement.executeQuery();
		
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				    "Could not retrieve desired data.",
				    "Database query error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	
	}
	
    public void start(Date startDate, Date endDate)
    {
    	PreparedStatement statement;
		try {
			statement = ConnectToDatabase.con.prepareStatement("SELECT " +
					"pos_x, pos_y, pos_z, pos_time FROM rosetta_pos " +
					"WHERE vo_id = "+ id +" AND (pos_time BETWEEN '" + startDate + "' AND '" + endDate + "')");
			
			result = statement.executeQuery();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				    "Could not retrieve desired data.",
				    "Database query error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
    	
    	
    }
	
	
	
	public String toString()
	{
		return "Object id: " + id;
	}
	
	public ResultSet getResultSet()
	{
		return result;
	}

	

	
}
