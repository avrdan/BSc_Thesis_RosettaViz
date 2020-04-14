package esa.esac.Rosetta.Visualization;

import com.jme3.app.state.AbstractAppState;

import esa.esac.Rosetta.Visualization.DataStructure.PositionData;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;

public class PlotToggleState extends AbstractAppState{
	private VizObject vizObj;
	private PositionData pd;
	
	/*public PlotToggleState(VizObject vizObj, PositionData pd)
	{
		this.vizObj = vizObj;
		this.pd = pd;
	}*/
	public void initParams(VizObject vizObj, PositionData pd)
	{
		this.vizObj = vizObj;
		this.pd = pd;
		setEnabled(true);
	}
	
	 @Override
	    public void setEnabled(boolean enabled) {
	      // Pause and unpause
	      super.setEnabled(enabled);
	      
	    }

	
	@Override
    public void update(float tpf) {
      if(isEnabled() && vizObj != null){
        // do the following while game is RUNNING
//        this.app.getRootNode().getChild("blah").scale(tpf); // modify scene graph...
//        x.setUserData(...);                                 // call some methods...
//      } else {
        // do the following while game is PAUSED, e.g. play an idle animation.
//        ...        
    	  for(VizObject vo: GlobalTools.objectArray)
    	  {
    		  
    		  if(vo.getId() == vizObj.getId())
    		  {
    			  vizObj.setPtrArrowDirection();
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


}
