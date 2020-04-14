package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;


import esa.esac.Rosetta.Visualization.Graphics.Landmark;
import esa.esac.Rosetta.Visualization.Graphics.SPCObject;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;

public class ROIReader {
	int roi_id;
	public ROIReader(VizObject vizObj)
	{
		try {
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_roi WHERE vo_id = " + vizObj.getId());
			
			// Creating a variable to execute the query
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				roi_id = result.getInt(1);
				PreparedStatement getPrimitivesStatement = ConnectToDatabase.con.prepareStatement
				("SELECT primitive_id FROM rosetta_roi_prtv WHERE roi_id = " + roi_id);
				
				ResultSet primitiveResult = getPrimitivesStatement.executeQuery();
				
				while(primitiveResult.next())
				{
					primitiveResult.getArray(2);
					vizObj.addROI(primitiveResult.getArray(2), new ColorRGBA(result.getFloat(3), result.getFloat(4), result.getFloat(5), result.getFloat(6)));
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
