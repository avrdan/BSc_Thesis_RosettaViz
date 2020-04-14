package esa.esac.Rosetta.Visualization;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

import esa.esac.Rosetta.Visualization.DataStructure.PositionData;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;

public class PlotToggleControl extends AbstractControl {
	private VizObject vizObj;
	private PositionData pd;
	int jump = 0;
	/*public PlotToggleControl(VizObject vizObj, PositionData pd)
	{
		this.vizObj = vizObj;
		this.pd = pd;
		setEnabled(true);
	}*/
	
	public void initParams(VizObject vizObj, PositionData pd)
	{
		this.vizObj = vizObj;
		this.pd = pd;
		setEnabled(true);
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
		/* Optional: rendering manipulation (for advanced users) */


	}
	
	@Override
    public void setEnabled(boolean enabled) {
      // Pause and unpause
      super.setEnabled(enabled);
      
    }

	@Override
	protected void controlUpdate(float tpf) {
		if(isEnabled()){
	    	  for(VizObject vo: GlobalTools.objectArray)
	    	  {
	    		  
	    		  if(vo.getId() == vizObj.getId())
	    		  {
	    			  System.out.println("plot buffer size: " + pd.getPositions().size());
	    			  /*jump = 0;
						
					  while(jump < Universe.getApplication().appSpeed
								  *Universe.getApplication().timeStep)
					  {
						 //fullPosResultMap.get(obj.getId()).next();
					     //skip a value
						 jump++;
					  }*/
	    			  for(int i = 0; i < pd.getPositions().size(); i++)
	    			  {
	    				  vizObj.setPosition(pd.getPositions().get(i));
	    			  }
	    			  setEnabled(false);
	    			  break;
	    		  }
	    	  }
	      }

	}

	@Override
	public Control cloneForSpatial(Spatial spatial) {
		// TODO Auto-generated method stub
		return null;
	}

}
