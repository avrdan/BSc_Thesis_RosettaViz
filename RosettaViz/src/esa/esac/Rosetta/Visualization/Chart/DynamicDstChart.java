package esa.esac.Rosetta.Visualization.Chart;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.ZoomableChart;
import info.monitorenter.gui.chart.io.ADataCollector;
import info.monitorenter.gui.chart.io.RandomDataCollectorOffset;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.views.ChartPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class DynamicDstChart {
	 private ADataCollector collector;
	 /**
	   * Action adapter for zoomAllButton.
	   * <p>
	   */
	  class ZoomAllAdapter implements ActionListener {
	    /** The zoomable chart to act upon. */
	    private ZoomableChart m_zoomableChart;

	    /**
	     * Creates an instance that will reset zooming on the given zoomable chart upon the triggered
	     * action.
	     * <p>
	     * 
	     * @param chart
	     *            the target to reset zooming on.
	     */
	    public ZoomAllAdapter(final ZoomableChart chart) {
	      this.m_zoomableChart = chart;
	    }

	    /**
	     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	     */
	    public void actionPerformed(final ActionEvent event) {
	      this.m_zoomableChart.zoomAll();
	    }
	  }
	  
	  public void createChart() {
		//Container c = this.getContentPane();
		//c.setLayout(new BorderLayout());
	    
		// Create a chart:
		  
	    //Chart2D chart = new Chart2D();
	    
	    // zoomable
		ZoomableChart chart = new ZoomableChart();
	    // Create an ITrace:
	    // Note that dynamic charts need limited amount of values!!!
	    ITrace2D trace = new Trace2DLtd(1000);
	    trace.setColor(Color.RED);

	    // Add the trace to the chart:
	    chart.addTrace(trace);
	    IAxis axisX = chart.getAxisX();
	    axisX.setStartMajorTick(false);
	    axisX.setMajorTickSpacing(10);
	    
	    
	    // Make it visible:
	    // Create a frame.
	    JFrame frame = new JFrame("Dynamic Distance Chart");
	    // add the chart to the frame:
	    frame.getContentPane().add(new ChartPanel(chart));
	    frame.setSize(400, 300);
	    // Enable the termination button [cross on the upper right edge]:
	    frame.addWindowListener(new WindowAdapter() {
	      /**
	       * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
	       */
	      @Override
	      public void windowClosing(final WindowEvent e) {
	        System.exit(0);
	      }
	    });
	    frame.setVisible(true);
	    // Every 1000 milliseconds a new value is collected.
	    collector = new DynamicDstTest(trace, 1000); // need to change this accordingly
	    collector.start();
	  }
	  
	  public void pause()
	  {
		  collector.stop();
	  }

	  /** Defcon. */
	  public DynamicDstChart() {
	    // nop
	  }

	public void start() {
		collector.start();
		
	}

}
