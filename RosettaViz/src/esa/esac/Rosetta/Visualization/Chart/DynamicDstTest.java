package esa.esac.Rosetta.Visualization.Chart;
import java.util.Date;

import esa.esac.Rosetta.Visualization.Universe;


import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.ITracePoint2D;
import info.monitorenter.gui.chart.TracePoint2D;
import info.monitorenter.gui.chart.io.ADataCollector;


/**
 * A proof of concept dummy implementation for the supertype.
 * <p>
 * 
 * Only collects random values with timestamp on the x axis. The timestamp is
 * related to the time when this instance is instantiated to make it a lower
 * value (offset to start). implementation for exact timestamps that may be
 * formatted with java.text.DateFormat instances.
 * <p>
 * 
 * @author <a href="mailto:Achim.Westermann@gmx.de">Achim Westermann </a>
 * 
 * @version $Revision: 1.9 $
 */
public class DynamicDstTest extends ADataCollector {

  private float distance;
  private Date dateTime;
  int i;

  /**
   * Creates a collector that collectes every latency ms a point and adds it to
   * the trace.
   * <p>
   * 
   * @param trace
   *          the trace to add points to.
   * 
   * @param latency
   *          the interval for collection of points.
   */
  public DynamicDstTest(final ITrace2D trace, final int latency) {
    
	super(trace, latency);
    i = 0;
  }

  /**
   * @see ADataCollector#collectData()
   */
  @Override
  public ITracePoint2D collectData() {
	 // fullPosResultMap
	  
	//dateTime = Universe.getApplication().dateArr.get(i);
	//i++;
	//distance = Universe.getApplication().comet.getPosition().distance(Universe.getApplication().rosetta.getPosition());
	
    return new TracePoint2D(((double) dateTime.getTime()), this.distance);
  }
}


