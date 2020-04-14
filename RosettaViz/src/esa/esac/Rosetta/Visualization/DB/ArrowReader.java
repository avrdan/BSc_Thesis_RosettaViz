package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;


import esa.esac.Rosetta.Visualization.Universe;
import esa.esac.Rosetta.Visualization.Graphics.SPCObject;
import esa.esac.Rosetta.Visualization.Graphics.SolarObject;
import esa.esac.Rosetta.Visualization.Graphics.SunObject;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;

public class ArrowReader {

	public ArrowReader(VizObject vizObj)
	{
		try {
			PreparedStatement statement1 = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_cs_arrows WHERE vo_id = " + vizObj.getId());
			PreparedStatement statement2 = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_ptr_arrows WHERE vo_id = " + vizObj.getId());
			
			// Creating a variable to execute the query
			ResultSet result1 = statement1.executeQuery();
			ResultSet result2 = statement2.executeQuery();
			
			while(result1.next())
			{
				
	
				vizObj.addCoordinateArrow(result1.getString(3), new ColorRGBA(result1.getFloat(4), 
						result1.getFloat(5), result1.getFloat(6), result1.getFloat(7)), result1.getFloat(8), 
						new Vector3f(result1.getFloat(9), result1.getFloat(10), result1.getFloat(11)));
			}
			
			while(result2.next())
			{
				for(SolarObject solarObj : Universe.getApplication().solarObjectArray)
				{
					// check if the object is equal to the destination object and if so, add the line
					if(solarObj.getId() == result2.getInt(3))
					{
						vizObj.addPointerArrow(solarObj, result2.getString(4), new ColorRGBA(result2.getFloat(5), 
								result2.getFloat(6), result2.getFloat(7), result2.getFloat(8)));
					}
				}
				
				for(SPCObject spcObj : Universe.getApplication().spcObjectArray)
				{
					
					if(spcObj.getId() == result2.getInt(3))
					{
						vizObj.addPointerArrow(spcObj, result2.getString(4), new ColorRGBA(result2.getFloat(5), 
								result2.getFloat(6), result2.getFloat(7), result2.getFloat(8)));
					}
				}
				
				for(SunObject sunObj : Universe.getApplication().sunObjectArray)
				{
					
					if(sunObj.getId() == result2.getInt(3))
					{
						vizObj.addPointerArrow(sunObj, result2.getString(4), new ColorRGBA(result2.getFloat(5), 
								result2.getFloat(6), result2.getFloat(7), result2.getFloat(8)));
					}
				}
				
				
				//vizObj.addPtrArrow();
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
