package esa.esac.Rosetta.Visualization;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.post.SceneProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderContext;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.JmeSystem;
import com.jme3.system.awt.AwtPanel;
import com.jme3.system.awt.AwtPanelsContext;

import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;
import esa.esac.Rosetta.Visualization.DataStructure.PositionData;
import esa.esac.Rosetta.Visualization.Graphics.MaskObject;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;
import esa.esac.Rosetta.Visualization.UI.OffView;






/**
 * These tools are useful for several different purposes. 
 * As such, they must be global so other classes can use them.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 */
public class GlobalTools {
	// the application
	public static SimpleApplication simpleApp;
	
	// the asset manager
	public static AssetManager assetManager; 
	
	// viewports
	//public static ViewPort viewPort;
	public static ArrayList<Boolean> viewActive;

	// cameras
	public static Camera cam;
	
	// global nodes
	public static Node rootNode;
	public static Node sunNode;
	public static Node solarObjectNode;
	public static Node spcObjectNode;
	public static Node sunLightNode;
	public static Node viewNode;
	public static Node mainViewNode;
	public static Node allViewNode;
	
	// Render manager
	public static RenderManager renderManager;
	
	// Renderer
	public static Renderer renderer;
	
	// params
	public static ArrayList<ObjectParams> shapeParamsArray;
	public static ArrayList<MaterialParams> materialParamsArray;
	
	// vars
	public static boolean isActive = false;
	
	/* This constructor creates a new executor with a core pool size of 4. */
	public static ScheduledThreadPoolExecutor executor; 
	
	public static AwtPanelsContext ctx;
	
	public static ViewPort viewPort;
	
	public static AwtPanel mainPanel;
	
	public static int panelsOpened, panelsClosed;

	public static ArrayList<JFrame> frameArray;
	
	public static ArrayList<PositionData> posPlotArr;
	
	public static List<VizObject> objectArray;
	public static ArrayList<MaskObject> maskArray;
	public static List<OffView> offViewArray;
	
	public static ArrayList<PositionData> dbBuffer;
	private static ArrayList<PositionData> plotBuffer;
	
	public static PositionData dbPosBuffer;
	public static PositionData plotPosBuffer;
	
	// should be final or synchronized
	public static int readLimit;
	
	public static void createWindowForPanel(AwtPanel panel, int locationX, int locationY, String name){
        final JFrame frame = new JFrame(name);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        

        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
               
                
                frame.removeAll();
                
                for(JFrame aFrame : frameArray)
            	{
                	aFrame.dispatchEvent(new WindowEvent( aFrame, WindowEvent.WINDOW_CLOSING));
            	}
              
                //Universe.getApplication().stop(true);
                Future<Boolean> fut = (Universe.getApplication().enqueue(new Callable<Boolean>() {
        		    public Boolean call() throws Exception {
        		    	Universe.getApplication().stop(true);
        		    	GlobalTools.ctx = null;
        		    	GlobalTools.mainPanel = null;
        		        return false;
        		    }
        		}));
              
            }
         
            
        });
        frame.setLocation(locationX, locationY);
        //fullScreenFrame(frame);
     
        frame.setVisible(true);
        frame.pack();
    
    }
	
	public static void createWindowForPanel(final AwtPanel panel, int location, String name, final ViewPort vp){
        final JFrame frame = new JFrame(name);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        panelsOpened++;
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
               
                
                //cleanup
                frame.removeAll();
                
               
		    	//offView.getOffBuffer().deleteObject(GlobalTools.renderer);
		    	
		    	/*// remove scene processor
		    	for(SceneProcessor sp : vp.getProcessors())
		    	{
		    		vp.removeProcessor(sp);
		    	}*/
                
                
                
                
                Future<Boolean> fut = (Universe.getApplication().enqueue(new Callable<Boolean>() {
        		    public Boolean call() throws Exception {
        		    	
        		    	while (vp.getProcessors().size() > 0) {
			    	      final SceneProcessor proc = vp.getProcessors().get(0);
			    	      vp.removeProcessor(proc);
			    	    }
			   
			    	if(vp.getOutputFrameBuffer() != null)
			    		vp.getOutputFrameBuffer().clearColorTargets();
	                vp.clearScenes();
	            	vp.setOutputFrameBuffer(null);
	            	GlobalTools.renderManager.clearQueue(vp);
	            	GlobalTools.renderManager.removePreView(vp);
        		        return false;
        		    }
        		}));
                
               
				    	
                
            }
            
        
            
        });
        frame.setLocation(location, Toolkit.getDefaultToolkit().getScreenSize().height - 400);
        frameArray.add(frame);
   
        frame.pack();
       
        frame.setVisible(true);
        
    }
	
	private static void fullScreenFrame(JFrame frame)
	{
		GraphicsDevice device = frame.getGraphicsConfiguration().getDevice();
		device.setFullScreenWindow(frame);
		device.setDisplayMode(new DisplayMode(640, 480, 24, 60));
		
        frame.setVisible(true);
        frame.pack();
//		DisplayMode oldDm;
//		if(gd.isFullScreenSupported())
//		{
//			// take away the borders and make it non-resizable
//			frame.setUndecorated(true);
//			frame.setResizable(false);
//			
//			oldDm = gd.getDisplayMode();
//			
//			DisplayMode dm = new DisplayMode(frame.getWidth(), frame.getHeight(), oldDm.getBitDepth(), oldDm.getRefreshRate());
//			
//			// set this window to fullscreen
//			gd.setFullScreenWindow(frame);
//			
//			gd.setDisplayMode(dm);
//		}
//		else
//		{
//			JOptionPane.showMessageDialog(null,
//				    "Cannot go fullscreen!",
//				    "Fullscreen error",
//				    JOptionPane.ERROR_MESSAGE);
//		}
	}
	
	/**
	 * Initializes the most important parameters of the Application
	 * 
	 * @param renderManager the current render manager
	 * @param renderer		the renderer
	 * @param rootNode		the scenegraph root node
	 */
	public static void init3D(RenderManager renderManager, Renderer renderer, Node rootNode, AssetManager assetManager, ViewPort viewPort)
	{
		//assetManager = JmeSystem.newAssetManager(Thread.currentThread().getContextClassLoader().getResource("com/jme3/asset/Desktop.cfg"));
		
		assetManager.registerLocator("assets/Textures/Comet/", FileLocator.class.getName());
		assetManager.registerLocator("assets/Textures/Sun/", FileLocator.class.getName());
		assetManager.registerLocator("assets/Textures/Mars/", FileLocator.class.getName());
		assetManager.registerLocator("assets/Models/RosettaMesh/", FileLocator.class.getName());
		assetManager.registerLocator("assets/Models/Arrow3DMesh/", FileLocator.class.getName());
		assetManager.registerLocator("assets/Textures/Space/", FileLocator.class.getName());
		assetManager.registerLocator("assets/Textures/Dialog/", FileLocator.class.getName());
		
		
//		assetManager.registerLocator("Textures/Comet/", FileLocator.class.getName());
//		assetManager.registerLocator("Textures/Sun/", FileLocator.class.getName());
//		assetManager.registerLocator("Models/RosettaMesh/", FileLocator.class.getName());
//		assetManager.registerLocator("Models/Arrow3DMesh/", FileLocator.class.getName());
//		assetManager.registerLocator("Textures/Space/", FileLocator.class.getName());
//		assetManager.registerLocator("Textures/Dialog/", FileLocator.class.getName());
		
		
		GlobalTools.assetManager = assetManager;
		
		sunLightNode = new Node("Sunlight Node");
		sunNode  = new Node("Sun Node");
		solarObjectNode = new Node("Solar Object Node");
		spcObjectNode = new Node("SpaceCraft Object Node");
		viewNode = new Node("View node");
		mainViewNode = new Node("Main View Node");
		allViewNode = new Node("All Views' Node");
		
		GlobalTools.renderManager = renderManager;
		GlobalTools.renderer = renderer;
		GlobalTools.rootNode = rootNode;
		
		viewPort.clearScenes();
		viewPort.attachScene(sunNode);
		GlobalTools.viewPort = viewPort;
	}
	
	public static void initParams()
	{
		// create param arrays
		shapeParamsArray = new ArrayList<ObjectParams>();
		materialParamsArray = new ArrayList<MaterialParams>();
		executor = new ScheduledThreadPoolExecutor(4);
		
		panelsOpened = panelsClosed = 0;
		
		frameArray = new ArrayList<JFrame>();
		
		posPlotArr = new ArrayList<PositionData>();
		
		readLimit = 2;
		//mainPanel = null;
		
		objectArray = Collections.synchronizedList(new ArrayList<VizObject>());
		maskArray = new ArrayList<MaskObject>();
		offViewArray = Collections.synchronizedList(new ArrayList<OffView>());
		
		dbBuffer = new ArrayList<PositionData>();
		plotBuffer = new ArrayList<PositionData>();

	}
}
