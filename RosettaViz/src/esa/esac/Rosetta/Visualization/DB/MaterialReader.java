package esa.esac.Rosetta.Visualization.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;

public class MaterialReader {
	private MaterialParams mp;
	
	public MaterialReader(int id)
	{
		try {
			//PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_mat");
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_mat " +
					"WHERE rosetta_mat.vo_id = " + id);
			
			
			// Creating a variable to execute the query
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				/*System.out.println(result.getString(1) + " " + result.getString(2)+ " " + result.getString(3) + " " 
						+ result.getString(4) + " " 
						+ result.getString(5));
				*/
				mp = new MaterialParams(result.getString(3),result.getString(4), result.getString(5));
				
				PreparedStatement statement1 = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_mat_params " +
						"WHERE rosetta_mat_params.mat_id = " + result.getInt(1));
				ResultSet result1 = statement1.executeQuery();
				
				while(result1.next())
				{
					if(result1.getString(2) != null && result1.getString(3) != null && 
							result1.getString(4) != null && result1.getString(5) != null)
					mp.setAmbColor(result1.getFloat(2), result1.getFloat(3), result1.getFloat(4), result1.getFloat(5));
					
					if(result1.getString(6) != null && result1.getString(7) != null && 
							result1.getString(8) != null && result1.getString(9) != null)
					mp.setDiffuseColor(result1.getFloat(6), result1.getFloat(7), result1.getFloat(8), result1.getFloat(9));
					
					if(result1.getString(10) != null && result1.getString(11) != null && 
							result1.getString(12) != null && result1.getString(13) != null)
					mp.setSpecColor(result1.getFloat(10), result1.getFloat(11), result1.getFloat(12), result1.getFloat(13));
					
					if(result1.getString(14) != null)
						mp.setShininess(result1.getFloat(14));
				}
				
				//GlobalTools.materialParamsArray.add(mp);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				    "Could not retrieve desired data.",
				    "Database query error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
				
	}

	public MaterialParams getMaterialParams() {
		return mp;
	}
}
