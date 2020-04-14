package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class PositionReader{
	//private HashMap<Integer, PositionData> posMap;
	
	private ArrayList<Vector3f> positions;
	private ArrayList<Float> distances;
	private ArrayList<Date> datesTime;
	
	private PositionData pd;
	
	//private int startIndex, endIndex;

	private int id;
	
	public PositionReader(int id)
	{
		this.id = id;	
		
		positions = new ArrayList<Vector3f>();
		distances = new ArrayList<Float>();
		datesTime = new ArrayList<Date>();
		//startIndex = 0;
		//endIndex = GlobalTools.readLimit;
		
		pd = new PositionData(id);
	
		//this.startIndex = startIndex;
		//this.endIndex   = endIndex;
	}	
		
		
		
	
	public void readDBPositionData(int startIndex, int endIndex)
	{
		startIndex *= 3;
		endIndex = startIndex + 3*1000;
		positions.clear();
		distances.clear();
		datesTime.clear();
		
		System.out.println("Positions size: " + positions.size());
		System.out.println("Time size: " + datesTime.size());
		//pd.getPositions().clear();
		//pd.getDatesAndTime().clear();
		//pd.setPositions(null);
		//pd.setDatesAndTime(null);
		try {
			/*PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT " +
					"pos_x, pos_y, pos_z, pos_time FROM rosetta_pos " +
					"WHERE vo_id = "+ id +" LIMIT " + startIndex + ", " + endIndex);
			*/
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT " +
					"pos_x, pos_y, pos_z, pos_time FROM rosetta_pos " +
					"WHERE (pos_id >= " + startIndex + " AND pos_id <= " + endIndex + 
					") AND vo_id = " + id);
			
			ResultSet result = statement.executeQuery();

			
			while(result.next())
			{
				positions.add(new Vector3f(Float.parseFloat(result.getString(1)), 
						Float.parseFloat(result.getString(2)), Float.parseFloat(result.getString(3))));
				datesTime.add(result.getTimestamp(4));
				
				//System.out.println("A Vector: " + new Vector3f(Float.parseFloat(result.getString(1)), 
						//Float.parseFloat(result.getString(2)), Float.parseFloat(result.getString(3))).toString());
			}
			
			
			pd.setPositions(positions);
			pd.setDatesAndTime(datesTime);
			System.out.println("PD Positions size: " + pd.getPositions().size());
			System.out.println("PD Time size: " + pd.getPositions().size());
			//startIndex += GlobalTools.readLimit;
			//endIndex   += GlobalTools.readLimit;
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				    "Could not retrieve desired data.",
				    "Database query error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	
	public int getId()
	{
		return id;
	}
	
	public String toString()
	{
		return "Object id: " + id + ", No. of positions: " + positions.size();
	}
	
	public PositionData getDBPositionData()
	{
		return pd;
	}

}
