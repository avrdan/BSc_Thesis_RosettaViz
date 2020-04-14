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

public class OneRotationReader {
	
	private ResultSet result;

	private int id;
	
	public OneRotationReader(int id)
	{
		
		
		this.id = id;	
		
		try {
			
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT " +
					"axisX, axisY, axisZ, speed, startTime FROM rosetta_rot " +
					"WHERE vo_id = "+ id);
		
			result = statement.executeQuery();
			
			/*System.out.println("FullPositionReader " + id + ":");
			while(result.next())
			{
				System.out.println(new Vector3f(Float.parseFloat(result.getString(1)), 
						Float.parseFloat(result.getString(2)), Float.parseFloat(result.getString(3))));
			}
			System.out.println("does it work again??");
			while(result.next())
			{
				System.out.println(new Vector3f(Float.parseFloat(result.getString(1)), 
						Float.parseFloat(result.getString(2)), Float.parseFloat(result.getString(3))));
			}*/
			
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
