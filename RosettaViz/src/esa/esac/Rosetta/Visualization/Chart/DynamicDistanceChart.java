package esa.esac.Rosetta.Visualization.Chart;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;


import javax.swing.JButton;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.jme3.math.Vector3f;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.Universe;
import esa.esac.Rosetta.Visualization.DB.TimeReader;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;

/**
 *
 * @author davram
*/ 


public class DynamicDistanceChart extends ApplicationFrame{
    
    private TimeSeries series;
    private DynamicTimeSeriesCollection dset;
    public double lastValue = 100.0;
    private float distance = 0;
    private int secondCheck = 0;
    private Date dateTime;
    private int i = Universe.getApplication().posIndex;
    
    private VizObject startObj;
    private VizObject endObj;
    
    private ArrayList<Date> timeArray;
    
    
    
    public class UpdaterThread extends Thread {
        
       
        @Override
       public void run()
       {
           setPriority(MIN_PRIORITY);
           
           while(true)
           {
        	   if(!Universe.getApplication().isRunning())
        	   {
        		   continue;
        	   }
        	   distance = startObj.getPosition().distance(endObj.getPosition());
               System.out.println("THE DISTANCE: " + distance);
        	   //distance = Universe.getApplication().comet.getPosition().distance(Universe.getApplication().rosetta.getPosition());
               //System.out.println("comet pos: " + Universe.getApplication().comet.getPosition() + " rosetta pos: " + Universe.getApplication().rosetta.getPosition());
               //float tpf = Universe.getApplication().getContext().getTimer().getTimePerFrame();
               //final double factor = 0.90 + 0.2 * Math.random();
               //lastValue = lastValue * factor;
               //final Millisecond now = new Millisecond();
               //secondCheck += now.getMillisecond();
               //dateTime = Universe.getApplication().dateArr.get(i);
               dateTime = timeArray.get(i);
              /* if(secondCheck >= 999)
               {
            	//   series.update(Universe.dateArr.get(0).getTime() + secondCheck , distance);
            	   dateTime.setTime(dateTime.getTime() + secondCheck);
            	   series.add(new Millisecond(dateTime)  , distance);
            	   secondCheck = 0;
            	   i++;
               }
               else
               {//   series.update((Millisecond) Universe.dateArr.get(0).getTime(), distance);
            	//dateTime.setTime(dateTime.getTime() + secondCheck);
            	   Millisecond bla = new Millisecond(dateTime);
            	   
            	   System.out.println("date and time stuff" + bla.toString() + " the distance: " + distance);
               	//series.update(new Millisecond(dateTime), distance);
            	   series.add(new Millisecond(dateTime), distance);
            	   
            	   
               }
               */

               
              series.add(new Millisecond(dateTime), distance);
              i++;
     
               try
               {
            	   //sleep((long) Universe.getApplication().getCon Carretera de Colmenar del Arroyo a Robledo de Chavela text().getTimer().getTimePerFrame());

            	   //sleep(1000);
            	   //sleep((long)(1000/(Universe.getApplication().appSpeed*Universe.getApplication().timeStep)));
            	   sleep((long)(1000/(Universe.getApplication().appSpeed)));

               }
               catch (InterruptedException e)
               {
                   // suppress
               }
           }
       }
    }
    
    @Override
    public void windowClosing(final WindowEvent evt){
    	if(evt.getWindow() == this){
    	dispose();

    	}
    }
    
    public DynamicDistanceChart(String title, String startObjName, String endObjName)
    {
        super(title);
        
        //timeArray = new TimeReader().getTimeArray();
        timeArray = new TimeReader(Universe.getApplication().getStartDate(),
        		Universe.getApplication().getEndDate()).getTimeArray();
        
        this.series = new TimeSeries("Rosetta Data", Millisecond.class);
        
        for (VizObject vo : GlobalTools.objectArray)
        {
        	if(vo.getName().equals("Rosetta SPC"))
        		startObj = vo;
        	else
        		if(vo.getName().equals("The comet"))
            		endObj = vo;
        }
        
        this.dset = new DynamicTimeSeriesCollection(1, 1000, new Millisecond());
       // this.dset.a
        //this.series = new TimeSeries("data", domain, range);
        TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
        JFreeChart chart = createChart(dataset);
        
        ChartPanel chartPanel = new ChartPanel(chart);

        
        JPanel content = new JPanel(new BorderLayout());
        content.add(chartPanel);
 
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(content);
        
        //UpdaterThread.currentThread().start();
    }
    


    private JFreeChart createChart(TimeSeriesCollection dataset) {
        JFreeChart result = ChartFactory.createTimeSeriesChart("Dynamic Distance", "Time", "Distance", dataset, true, true, false);
        
        XYPlot plot = result.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);
        axis = plot.getRangeAxis();
        axis.setRange(19.75, 20.50);
       
        return result;
    }
}
