package esa.esac.Rosetta.Visualization.DB;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;


import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.Universe;
import esa.esac.Rosetta.Visualization.Graphics.Landmark;
import esa.esac.Rosetta.Visualization.Graphics.MaskObject;
import esa.esac.Rosetta.Visualization.Graphics.SPCObject;
import esa.esac.Rosetta.Visualization.Graphics.SolarObject;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;
import esa.esac.Rosetta.Visualization.UI.MainWindow;
import esa.esac.Rosetta.Visualization.UI.OffView;

public class OffViewReader {
	//private Landmark landMark;
	
	public OffViewReader()
	{
		try {
			PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT * FROM rosetta_offview");
			
			// Creating a variable to execute the query
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{		

				VizObject viewedObject = null, viewerObject = null;
				Vector3f viewedAxis = null;
				MaskObject mask = null;
				if(result.getString(12) != null)
				{
					for(MaskObject msk : GlobalTools.maskArray)
				
					{
						
						if(msk.getId() == result.getInt(12))
							mask = msk;
					}
				}
				if((result.getString(6) != null && result.getString(7) != null && result.getString(8) != null))
				{
					// viewed axis
					viewedAxis = new Vector3f(result.getFloat(6), result.getFloat(7), result.getFloat(8));
					
					/*Universe.getApplication().getEnvironment().addView(result.getInt(2), result.getInt(3), result.getString(4), 		
								viewedObject, new Vector3f((result.getFloat(6), result.getFloat(7), result.getFloat(8)), viewerObject, 
										GlobalTools.sunNode, result.getInt(10));
					*/
					for(VizObject vizObj : GlobalTools.objectArray)
					{
						if (vizObj.getId() == result.getInt(9))
						{
							viewerObject = vizObj;
						}
					}
					
//					
				}
				else
				{
					// viewed object
					for(VizObject vizObj : GlobalTools.objectArray)
					{
						if(vizObj.getId() == result.getInt(5))
							viewedObject = vizObj;
						else if (vizObj.getId() == result.getInt(9))
						{
							viewerObject = vizObj;
						}
					}	
					
//					offView = new OffView(result.getInt(2), result.getInt(3), result.getString(4), 		
//							viewedObject, null, viewerObject, GlobalTools.sunNode, result.getInt(10), mask);
//					GlobalTools.offViewArray.add(offView);
				}
				
				//MainWindow.addOffViewMenus(result.getString(11), offView);
				if(mask != null)
					MainWindow.addOffViewMenus(result.getInt(1),result.getInt(2), result.getInt(3), result.getString(4), 		
								viewedObject, viewedAxis, viewerObject, GlobalTools.viewNode, result.getInt(10), mask, result.getString(11));
				else
					MainWindow.addOffViewMenus(result.getInt(1), result.getInt(2), result.getInt(3), result.getString(4), 		
							viewedObject, viewedAxis, viewerObject, GlobalTools.sunNode, result.getInt(10), mask, result.getString(11));

				//new ViewTextReader(offView.getId());
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
