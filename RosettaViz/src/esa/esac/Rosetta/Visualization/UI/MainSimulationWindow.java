package esa.esac.Rosetta.Visualization.UI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import jme3test.light.TestPssmShadow;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import com.jme3.system.awt.AwtPanel;
import com.jme3.system.awt.AwtPanelsContext;
import com.jme3.system.awt.PaintMode;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.Universe;



public class MainSimulationWindow implements Runnable{
	private Universe simulationApp;
	public MainSimulationWindow() 
	{
		/*AppSettings settings = new AppSettings(true);
	    settings.setTitle("Rosetta Viz Simulation");
	    
	    settings.setSettingsDialogImage("Textures/Dialog/340px-Rosetta.jpg");
	    
		Universe simulationApp = Universe.getApplication();
		
		simulationApp.setSettings(settings);
		
		simulationApp.start();
		*/
//		GlobalTools.executor.submit(new Runnable() {
//			
//			@Override
//			public void run() {
//				if(Universe.getApplication().getContext() == null)
//				{
//					AppSettings settings = new AppSettings(true);
//				    settings.setTitle("Rosetta Viz Simulation");
//				    
//				    settings.setSettingsDialogImage("Textures/Dialog/340px-Rosetta.jpg");
//				    
//					Universe simulationApp = Universe.getApplication();
//					
//					settings.setCustomRenderer(AwtPanelsContext.class);
//					
//					simulationApp.setSettings(settings);
//					
//					
//					simulationApp.start();
//					
//					GlobalTools.ctx = (AwtPanelsContext) simulationApp.getContext();
//					
//					GlobalTools.mainPanel = GlobalTools.ctx.createPanel(PaintMode.Accelerated);
//					
//					GlobalTools.mainPanel.setPreferredSize(new Dimension(settings.getWidth(), settings.getHeight()));
//	                GlobalTools.ctx.setInputSource(GlobalTools.mainPanel);
//	                
//	                GlobalTools.createWindowForPanel(GlobalTools.mainPanel, 400, 400, settings.getTitle());
//				}
//				
//				
// 
//				
//			}
//			
//		});
		
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(Universe.getApplication().getContext() == null)
				{
					AppSettings settings = new AppSettings(true);
				    settings.setTitle("Rosetta Viz Simulation");
				    
				    settings.setSettingsDialogImage("Textures/Dialog/340px-Rosetta.jpg");
				    
					Universe simulationApp = Universe.getApplication();
					
					settings.setCustomRenderer(AwtPanelsContext.class);
					
					
					//settings.setFrameRate(NORM_PRIORITY);
					simulationApp.setSettings(settings);
					
					if(Universe.getApplication().appStarted)
						simulationApp.restart();
					else
						simulationApp.start();
					
					GlobalTools.ctx = (AwtPanelsContext) simulationApp.getContext();
					
					GlobalTools.mainPanel = GlobalTools.ctx.createPanel(PaintMode.Accelerated);
					
					GlobalTools.mainPanel.setPreferredSize(new Dimension(settings.getWidth(), settings.getHeight()));
	                GlobalTools.ctx.setInputSource(GlobalTools.mainPanel);
					
	                
					
					
					//GlobalTools.mainPanel.TOP_ALIGNMENT = Toolkit.getDefaultToolkit().getScreenSize().height - 200;
	                
	                GlobalTools.createWindowForPanel(GlobalTools.mainPanel, 0, settings.getTitle());

				}
				else
				{
					//Universe.getApplication().getContext().destroy(true);
					System.out.println("parampam");
					GlobalTools.initParams();
					Universe.getApplication().getContext().destroy(true);
					Universe.getApplication().stop();
					
					AppSettings settings = new AppSettings(true);
				    settings.setTitle("Rosetta Viz Simulation");
				    
				    settings.setSettingsDialogImage("Textures/Dialog/340px-Rosetta.jpg");
				    
					Universe simulationApp = Universe.getApplication();
					
					settings.setCustomRenderer(AwtPanelsContext.class);
					
					
					//settings.setFrameRate(NORM_PRIORITY);
					simulationApp.setSettings(settings);
					
					if(Universe.getApplication().appStarted)
						simulationApp.restart();
					else
						simulationApp.start();
					
					GlobalTools.ctx = (AwtPanelsContext) simulationApp.getContext();
					
					GlobalTools.mainPanel = GlobalTools.ctx.createPanel(PaintMode.Accelerated);
					
					GlobalTools.mainPanel.setPreferredSize(new Dimension(settings.getWidth(), settings.getHeight()));
	                GlobalTools.ctx.setInputSource(GlobalTools.mainPanel);
				}
               
				
			}
			
		}).start();
		*/
	}

	@Override
	public void run() {
		
			AppSettings settings = new AppSettings(true);
		    settings.setTitle("Rosetta Viz Simulation");
		    
		    settings.setSettingsDialogImage("Textures/Dialog/340px-Rosetta.jpg");
		    settings.setCustomRenderer(AwtPanelsContext.class);
		    ///settings.setStereo3D(true);
		    
		    if(simulationApp == null)
		    {
		    	simulationApp = Universe.getApplication();
		    	settings.setFrameRate(500);
		    	simulationApp.setSettings(settings);
		    	
		    	//settings.setFullscreen(true);
		    	simulationApp.start();
		    }
		    	else
		    {
		    	Universe.makeNull();
		    	
		    	simulationApp = Universe.getApplication();
		    	settings.setFrameRate(500);
		    	simulationApp.setSettings(settings);
		    	
		    	//settings.setFullscreen(true);
		    	simulationApp.start();
		    	
		    	//simulationApp.setSettings(settings);
		    	//simulationApp.restart();
		    }
		    //if(simulationApp != null)
		    	//Universe.makeNull();
			
			
			GlobalTools.ctx = (AwtPanelsContext) simulationApp.getContext();
			
			GlobalTools.mainPanel = GlobalTools.ctx.createPanel(PaintMode.Accelerated);
			
			GlobalTools.mainPanel.setPreferredSize(new Dimension(settings.getWidth(), settings.getHeight()));
            GlobalTools.ctx.setInputSource(GlobalTools.mainPanel);
            
            GlobalTools.createWindowForPanel(GlobalTools.mainPanel, 0, 100, settings.getTitle());
		
		
	}

	
	
}
