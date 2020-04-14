package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.jme3.math.ColorRGBA;

import esa.esac.Rosetta.Visualization.Graphics.CaptionTextCreator;
import esa.esac.Rosetta.Visualization.Graphics.Landmark;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;

public class CaptionReader {
	public CaptionReader(VizObject vizObj)
	{
		try {
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_caption WHERE vo_id = " + vizObj.getId());
			
			// Creating a variable to execute the query
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				
				
				vizObj.addCaption(result.getString(3), new ColorRGBA(result.getFloat(4), result.getFloat(5), 
						result.getFloat(6), result.getFloat(7)));
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
