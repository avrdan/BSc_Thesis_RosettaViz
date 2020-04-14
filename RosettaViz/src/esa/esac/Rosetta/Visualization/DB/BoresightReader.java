package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import esa.esac.Rosetta.Visualization.DataStructure.AdditionalParams;
import esa.esac.Rosetta.Visualization.Graphics.Boresight;
import esa.esac.Rosetta.Visualization.Graphics.SPCObject;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;

public class BoresightReader {
	private Boresight boreSight;
	public BoresightReader(SPCObject spcObj)
	{
		try {
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_boresight WHERE vo_id = " + spcObj.getId());
			
			// Creating a variable to execute the query
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				//Boresight bor = new Boresight(new Vector3f(result.getFloat(3), result.getFloat(4), result.getFloat(5)), 
					//	direction, length, color, lineWidth));
				
						//addBoresight(new Vector3f(result.getFloat(3), result.getFloat(4), result.getFloat(5), result.getFloat(6), 
							//	new ColorRGBA(result.getFloat(7), result.getFloat(8), result.getFloat(9), result.getFloat(10))), result.getFloat(11));
				//ap = new AdditionalParams(new Vector3f(result.getFloat(3), result.getFloat(4), result.getFloat(5)), 
				//		result.getFloat(6), new ColorRGBA(result.getFloat(7), result.getFloat(8), result.getFloat(9), result.getFloat(10)), 
				//		result.getFloat(11));
				
				spcObj.addBoresight(new Vector3f(result.getFloat(3), result.getFloat(4), result.getFloat(5)), result.getFloat(6), new ColorRGBA(result.getFloat(7), result.getFloat(8), result.getFloat(9), result.getFloat(10)), result.getFloat(11));
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				    "Could not retrieve desired data.",
				    "Database query error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	public Boresight getBoresight() {
		return boreSight;
	}
}
