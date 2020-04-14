package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jme3.math.ColorRGBA;


import esa.esac.Rosetta.Visualization.Universe;
import esa.esac.Rosetta.Visualization.Graphics.ObjectDistanceLine;
import esa.esac.Rosetta.Visualization.Graphics.SPCObject;
import esa.esac.Rosetta.Visualization.Graphics.SolarObject;
import esa.esac.Rosetta.Visualization.Graphics.SunObject;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;

public class ObjDistLineReader {
	private ArrayList<ObjectDistanceLine> odLineArray;
	public ObjDistLineReader(VizObject vizObj)
	{
		try {
			odLineArray = new ArrayList<ObjectDistanceLine>();
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_obj_dist_line WHERE vo_id = " + vizObj.getId());
			
			// Creating a variable to execute the query
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				for(SolarObject solarObj : Universe.getApplication().solarObjectArray)
				{
					// check if the object is equal to the destination object and if so, add the line
					if(solarObj.getId() == result.getInt(3))
					{
						//System.out.println("THIS MUST BE WORKING!");
						vizObj.addLine(solarObj, new ColorRGBA(result.getFloat(4), 
								result.getFloat(5), result.getFloat(6), result.getFloat(7)), result.getFloat(8));
					}
				}
				
				for(SPCObject spcObj : Universe.getApplication().spcObjectArray)
				{
					
					if(spcObj.getId() == result.getInt(3))
					{
						vizObj.addLine(spcObj, new ColorRGBA(result.getFloat(4), 
								result.getFloat(5), result.getFloat(6), result.getFloat(7)), result.getFloat(8));
					}
				}
				
				for(SunObject sunObj : Universe.getApplication().sunObjectArray)
				{
					
					if(sunObj.getId() == result.getInt(3))
					{
						vizObj.addLine(sunObj, new ColorRGBA(result.getFloat(4), 
								result.getFloat(5), result.getFloat(6), result.getFloat(7)), result.getFloat(8));
					}
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
	public ArrayList<ObjectDistanceLine> getObjDistLines() {
		return odLineArray;
	}
}
