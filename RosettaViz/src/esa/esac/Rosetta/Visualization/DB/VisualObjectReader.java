package esa.esac.Rosetta.Visualization.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;
import esa.esac.Rosetta.Visualization.DataStructure.PositionData;
import esa.esac.Rosetta.Visualization.DataStructure.ShapeData;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;

public class VisualObjectReader {
	private ObjectParams sp;
	private ShapeData sd;
	private PositionData pd;
	private MaterialParams mp;
		public VisualObjectReader()
		{
			
			try {
				PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_vo");
				
				// Creating a variable to execute the query
				ResultSet result = statement.executeQuery();
				
				while(result.next())
				{
					/*System.out.println(result.getString(1) + " " + result.getString(2)+ " " + result.getString(3) + " " 
							+ result.getString(4) + " " + result.getString(5) + " " + result.getString(6) + " " +
							result.getString(7) + " " + result.getString(8));
					*/
					//if(sp.getType())
					//System.out.println("OBJECT TYPE: " + result.getString(3));
					if(result.getString(3).equals("ROI"))
					{
						//System.out.println("so it knows that this is an ROI");
						PreparedStatement getDestObjIdStatement = ConnectToDatabase.con.prepareStatement("SELECT vo_id FROM rosetta_roi WHERE vo_roi_id = " + result.getInt(1));
						
						ResultSet resVoId = getDestObjIdStatement.executeQuery();
						
						while(resVoId.next())
						{
							System.out.println("ROID ID: " + result.getInt(1) + ", DEST OBJ ID: " + resVoId.getInt(1));
							sd = new ROIObjectReader(result.getInt(1), resVoId.getInt(1)).getShapeData();
						}
					}
						
					else
					{	
						sd = new VisualDataReader(result.getInt(1)).getShapeData();	
					}
					//pd = new PositionReader(result.getInt(1)).getPositionData();
					int bla;
					if(result.getInt(1) == 2)
					{	bla = sd.getTotalPoints();
					
					System.out.println(bla);}
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
		
		public ObjectParams getShapeParameters() {
			return sp;
		}
	
		
		
}
