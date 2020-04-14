package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;


import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;
import esa.esac.Rosetta.Visualization.DataStructure.PositionData;
import esa.esac.Rosetta.Visualization.DataStructure.ShapeData;
import esa.esac.Rosetta.Visualization.Graphics.Landmark;
import esa.esac.Rosetta.Visualization.Graphics.SPCObject;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;

public class MaskReader {
	private ObjectParams sp;
	private ShapeData sd;
	private PositionData pd;
	private MaterialParams mp;
	
	public MaskReader(VizObject vizObj)
	{
		try {
PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_landmark WHERE vo_id = " + vizObj.getId());
			
			// Creating a variable to execute the query
			ResultSet result = statement.executeQuery();
			
			
			
			while(result.next())
			{
				/*System.out.println(result.getString(1) + " " + result.getString(2)+ " " + result.getString(3) + " " 
						+ result.getString(4) + " " + result.getString(5) + " " + result.getString(6) + " " +
						result.getString(7) + " " + result.getString(8));
				*/
				sd = new VisualDataReader(result.getInt(1)).getShapeData();
				
				mp = new MaterialReader(result.getInt(1)).getMaterialParams();
				
				if(sd != null)
				{
					//System.out.println(sd.toString());
					sp = new ObjectParams(result.getInt(1), result.getString(2), 
							result.getString(3), result.getString(4), result.getFloat(7), "", sd, pd, mp, result.getFloat(10));
					
				}
				else
				{
					sp = new ObjectParams(result.getInt(1), result.getString(2), 
							result.getString(3), result.getString(4), result.getFloat(7), "", pd, mp, result.getFloat(10));
				}
				
				//System.out.println(sp.toString());
				//System.out.println("LOL");
				
				GlobalTools.shapeParamsArray.add(sp);
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
