package esa.esac.Rosetta.Visualization;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.jme3.math.Vector3f;

import esa.esac.Rosetta.Visualization.DB.ConnectToDatabase;
import esa.esac.Rosetta.Visualization.DB.PositionReader;
import esa.esac.Rosetta.Visualization.DataStructure.PositionData;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;



public class DBPlotToggle {
	Timer timer;
	private int start = 0, step = 100;
	// realstart = 3*start
	// realend   = 3*start+step
	VizObject obj;
	int jump = 0;
	ArrayList<Vector3f> tmpArray;
	int dbTaskIndex = 0, plotTaskIndex = 0;
	PositionReader pr;
	public DBPlotToggle(VizObject vizObj)
	{
		obj = vizObj;
		pr = new PositionReader(obj.getId());
		timer = new Timer();
		tmpArray = new ArrayList<Vector3f>();
		timer.schedule(new ReadFromDB(), 50);
		
	}
	
	
	 class ReadFromDB extends TimerTask  {
		    public void run (  )   {
		    	/*
		    	jump = 0;
				
			    while(jump < Universe.getApplication().appSpeed
						  *Universe.getApplication().timeStep)
				{
					//fullPosResultMap.get(obj.getId()).next();
					jump++;
				}*/
		    	//GlobalTools.dbPosBuffer.getPositions().clear();
		    	//GlobalTools.dbPosBuffer.getDatesAndTime().clear();
		    	//GlobalTools.dbPosBuffer = null;
		      System.out.println("!DB TASK! " + dbTaskIndex);
			  System.out.println("Begin start: " + start + ", Begin step: " + step);  

		      try {
		    	pr.readDBPositionData(start, step);
				GlobalTools.dbPosBuffer = pr.getDBPositionData();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("db buffer size: " + 
					GlobalTools.dbPosBuffer.getPositions().size());
			/*if(GlobalTools.dbPosBuffer.getPositions().size() == 0)
			{
				timer.cancel();
			}*/
			/*while(obj.getControl().isEnabled() == true)
	    	{
	    		
	    	}*/
	    	System.out.println("MEEEEEEEEEEEEEEEERGE!!");
	    	tmpArray.clear();
	    	for(int i = 0; i < GlobalTools.dbPosBuffer.getPositions().size(); i++)
	    	{
	    		tmpArray.add(GlobalTools.dbPosBuffer.getPositions().get(i));
	    		//System.out.println("A Vector: " + GlobalTools.dbPosBuffer.getPositions().get(i));
	    	}
	    	
			GlobalTools.plotPosBuffer = GlobalTools.dbPosBuffer;
			GlobalTools.plotPosBuffer.setPositions(tmpArray);
			timer.schedule(new PlotData(), 50);
		      //System.out.println ( "This is task1!" ) ;
		      //timer.schedule(new ToDoTask2 (  ), 10*1000);
			/*PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT pos_x, pos_y, pos_z, pos_time " +
						"FROM rosetta_pos WHERE vo_id = " + id +" AND (pos_time BETWEEN '" + start + "' AND '" + end + "')");
			result = statement.executeQuery();*/
			start += 1000;
			//step  += 1000;
			System.out.println("NExt Start: " + start + ", Next Step: " + step);
			System.out.println("!END DB TASK! " + dbTaskIndex);
			dbTaskIndex++;
		    }
		  }
		  
		  class PlotData extends TimerTask  {
			    public void run (  )   {
			    	System.out.println("!PLOT TASK! " + plotTaskIndex);
			    	System.out.println("plot pos buffer size: " +
			    			GlobalTools.plotPosBuffer.getPositions().size());
			    	obj.getControl().initParams(obj, GlobalTools.plotPosBuffer);
			    	while(obj.getControl().isEnabled() == true)
			    	{
			    		
			    	}
			    	timer.schedule(new ReadFromDB(), 50);
			    	/*Universe.getApplication().getStateManager().getState(PlotToggleState.class).initParams(obj, GlobalTools.plotPosBuffer);
			    	if(Universe.getApplication().getStateManager().getState(PlotToggleState.class).isEnabled() == false)
			    	{
			    		timer.schedule(new ReadFromDB(), 10);
			    	}*/
			    	//timer.schedule ( new ToDoTask (  ) , 5*1000 ) ;
			      //System.out.println ( "This is task2!\n" ) ;
			    	System.out.println("!END PLOT TASK! " + plotTaskIndex);
			    	plotTaskIndex++;
			    }
			  }
}
