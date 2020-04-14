package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;


import esa.esac.Rosetta.Visualization.Graphics.Landmark;
import esa.esac.Rosetta.Visualization.Graphics.SPCObject;
import esa.esac.Rosetta.Visualization.Graphics.TextCreator;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;

public class ViewTextReader {
	
	public ViewTextReader(int offViewId)
	{
		try {
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_view_text WHERE offview_id = " + offViewId);
			
			// Creating a variable to execute the query
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				new TextCreator(result.getString(3), new Vector3f(result.getInt(4), result.getInt(5), result.getInt(6)), 
						new ColorRGBA(result.getFloat(7), result.getFloat(8), result.getFloat(9), result.getFloat(10)));
				//offView.addText
				//offView.addText();
				//vizObj.addLandmark(result.getFloat(3), result.getFloat(4), result.getFloat(5), new ColorRGBA(result.getFloat(6), result.getFloat(7), result.getFloat(8), result.getFloat(9)), result.getFloat(10));
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