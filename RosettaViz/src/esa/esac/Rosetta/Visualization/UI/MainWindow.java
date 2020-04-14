package esa.esac.Rosetta.Visualization.UI;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

import com.jme3.input.FlyByCamera;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.Universe;
import esa.esac.Rosetta.Visualization.Chart.DynamicDistanceChart;
import esa.esac.Rosetta.Visualization.DB.ConnectToDatabase;
import esa.esac.Rosetta.Visualization.DB.ViewTextReader;
import esa.esac.Rosetta.Visualization.Graphics.CoordinateArrow;
import esa.esac.Rosetta.Visualization.Graphics.MaskObject;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.jfree.ui.RefineryUtilities;
import javax.swing.JLabel;



public class MainWindow {

	private JFrame frmRosettaVisualizationApplication;
	private static JMenu mnNew3dWindow;
	private static JMenu mnShowHide;
	private static ArrayList<Boolean> vizObjBool;
	private static ArrayList<Node> cNodes;
	private static ArrayList<JMenu> cMenus;
	private static int newZoom = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			
			/*for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
			            break;

					}
			}*/
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmRosettaVisualizationApplication.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		GlobalTools.initParams();
		initialize();
		
		// connect to the database
		new ConnectToDatabase("RosettaViz", "root", "");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRosettaVisualizationApplication = new JFrame();
		frmRosettaVisualizationApplication.setTitle("Rosetta Visualization Application");
		frmRosettaVisualizationApplication.setBounds(0, 0, 699, 94);
		frmRosettaVisualizationApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmRosettaVisualizationApplication.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewSimulation = new JMenuItem("New Simulation");
		mntmNewSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new NewSimulationDialog().setVisible(true);
			}
		});
		mnFile.add(mntmNewSimulation);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as..");
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnSimulation = new JMenu("Simulation");
		menuBar.add(mnSimulation);
		
		JMenuItem mntmProperties = new JMenuItem("Properties");
		mnSimulation.add(mntmProperties);
		
		JMenuItem menuItem = new JMenuItem("");
		mnSimulation.add(menuItem);
		
		mnShowHide = new JMenu("Show/Hide");
		mnSimulation.add(mnShowHide);
		
		/*for(VizObject obj : GlobalTools.objectArray)
		{
			JMenuItem mntmObj = new JMenuItem(obj.getNode().getName());
			mnShowHide.add(mntmObj);
			
			for(Spatial child : obj.getNode().getChildren())
			{
				JMenuItem mntmChildObj = new JMenuItem(child.getName());
				mntmObj.add(mntmChildObj);
			}
		}*/
		
//		JMenuItem mntmTrajectory = new JMenuItem("Trajectory");
//		mnShowHide.add(mntmTrajectory);
//		
//		JMenuItem mntmBoresight = new JMenuItem("Boresight");
//		mnShowHide.add(mntmBoresight);
//		
//		JMenuItem mntmLandmark = new JMenuItem("Landmark");
//		mnShowHide.add(mntmLandmark);
//		
//		JMenuItem mntmRoi = new JMenuItem("ROI");
//		mnShowHide.add(mntmRoi);
//		
//		JMenuItem mntmMask = new JMenuItem("Mask");
//		mnShowHide.add(mntmMask);
//		
//		JMenuItem mntmCoordinateSystem = new JMenuItem("Coordinate System");
//		mnShowHide.add(mntmCoordinateSystem);
		
		JMenu mnUtilities = new JMenu("Utilities");
		menuBar.add(mnUtilities);
		
		JMenu mnNewdChart = new JMenu("New 2D Chart");
		mnUtilities.add(mnNewdChart);
		
		JMenuItem mntmDistanceChart = new JMenuItem("Distance Chart");
		mntmDistanceChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DynamicDistanceChart chart = 
					new DynamicDistanceChart("Distance Chart", 
						"Rosetta SPC", "The comet");
				
				chart.pack();
				RefineryUtilities.centerFrameOnScreen(chart);
				chart.setVisible(true);
				
				Thread updater;
				
				updater = chart.new UpdaterThread();
				updater.setDaemon(true);
				updater.start();
				/*DynamicDistanceChart demo = new DynamicDistanceChart("Rosetta-Comet distance chart");
			    demo.pack();
			    RefineryUtilities.centerFrameOnScreen(demo);
			    demo.setVisible(true);
			        
			    updater = demo.new UpdaterThread();
			    updater.setDaemon(true);
			    updater.start();*/
				
			}
		});
		mnNewdChart.add(mntmDistanceChart);
		
		mnNew3dWindow = new JMenu("New 3D Window");
		mnUtilities.add(mnNew3dWindow);
		
//		JMenuItem mntmRosettaToComet = new JMenuItem("Rosetta to Comet");
//		mnNew3dWindow.add(mntmRosettaToComet);
//		mntmRosettaToComet.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Future<Boolean> fut = (Universe.getApplication().enqueue(new Callable<Boolean>() {
//				    public Boolean call() throws Exception {
//				        //this is where you modify your object, you can return a result value
//				    	
//				    	Universe.getApplication().getEnvironment().addView(640, 480, "SpatialToCamera", 
//				    			Universe.getApplication().spcObjectArray.get(0), null, Universe.getApplication().solarObjectArray.get(0), GlobalTools.sunNode, 300);
//				        return true;
//				    }
//				}));
//				
//			}
//		});
//		
//		
//		JMenuItem mntmCometToZ = new JMenuItem("Comet view on Z Axis");
//		mnNew3dWindow.add(mntmCometToZ);
//		
//		mntmCometToZ.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Future<Boolean> fut = (Universe.getApplication().enqueue(new Callable<Boolean>() {
//				    public Boolean call() throws Exception {
//				        //this is where you modify your object, you can return a result value
//				    	
////				    	Universe.getApplication().getEnvironment().addView(800, 600, "SpatialToCamera", 
////				    			Universe.getApplication().solarObjectArray.get(0), Vector3f.UNIT_Z, Universe.getApplication().spcObjectArray.get(0), GlobalTools.sunNode, 300);
//				    	Universe.getApplication().getEnvironment().addView(640, 480, "SpatialToCamera", 
//				    			Universe.getApplication().sunObjectArray.get(0), Vector3f.UNIT_Z, Universe.getApplication().solarObjectArray.get(0), GlobalTools.sunNode, 300);
//				    	return true;
//				    }
//				}));
//				
//			}
//		});
//		
		final JLabel lblZoom = new JLabel("Zoom: 0");
		
		JMenuItem mntmLog = new JMenuItem("Log");
		mnUtilities.add(mntmLog);
		
		JMenu mnPlugins = new JMenu("Plugins");
		menuBar.add(mnPlugins);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About..");
		mnHelp.add(mntmAbout);
		
		JMenuItem mntmThemes = new JMenuItem("Themes");
		mnHelp.add(mntmThemes);
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, lblZoom, 8, SpringLayout.NORTH, frmRosettaVisualizationApplication.getContentPane());
		frmRosettaVisualizationApplication.getContentPane().setLayout(springLayout);
		
		JButton btnBackwardsButton = new JButton("");
		springLayout.putConstraint(SpringLayout.NORTH, btnBackwardsButton, 3, SpringLayout.NORTH, frmRosettaVisualizationApplication.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnBackwardsButton, -624, SpringLayout.EAST, frmRosettaVisualizationApplication.getContentPane());
		btnBackwardsButton.setIcon(new ImageIcon(MainWindow.class.getResource("/Icons/media-seek-backward.png")));
		frmRosettaVisualizationApplication.getContentPane().add(btnBackwardsButton);
		
		final JLabel lblSpeed = new JLabel("Speed: 1x");
		
		btnBackwardsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Universe.getApplication().appSpeed > 1)
				{
					Universe.getApplication().setAppSpeed(Universe.getApplication().getAppSpeed() / 2);
					lblSpeed.setText("Speed: " + ((float)Universe.getApplication().appSpeed) + "x");
				}
			}
		});
		
		JButton btnPlayStopButton = new JButton("");
		springLayout.putConstraint(SpringLayout.NORTH, btnPlayStopButton, 3, SpringLayout.NORTH, frmRosettaVisualizationApplication.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnPlayStopButton, 6, SpringLayout.EAST, btnBackwardsButton);
		btnPlayStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Universe.getApplication().isRunning())
				{
					Universe.getApplication().setRunning(false);
				}
				else
				{
					Universe.getApplication().setRunning(true);
				}
				
				//btnPlayStopButton.setIcon(defaultIcon);
			}
		});
		btnPlayStopButton.setIcon(new ImageIcon(MainWindow.class.getResource("/Icons/media-playback-start.png")));
		frmRosettaVisualizationApplication.getContentPane().add(btnPlayStopButton);
		
		JButton btnForwardsButton = new JButton("");
		springLayout.putConstraint(SpringLayout.NORTH, btnForwardsButton, 3, SpringLayout.NORTH, frmRosettaVisualizationApplication.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnForwardsButton, 7, SpringLayout.EAST, btnPlayStopButton);
		btnForwardsButton.setIcon(new ImageIcon(MainWindow.class.getResource("/Icons/media-seek-forwards.png")));
		frmRosettaVisualizationApplication.getContentPane().add(btnForwardsButton);
		
		final JLabel lblCamSpeed = new JLabel("Cam Speed: 25.0");
		springLayout.putConstraint(SpringLayout.NORTH, lblCamSpeed, 10, SpringLayout.NORTH, frmRosettaVisualizationApplication.getContentPane());;
		
		JButton btnCameraMovementIncrease = new JButton("");
		springLayout.putConstraint(SpringLayout.NORTH, btnCameraMovementIncrease, 3, SpringLayout.NORTH, frmRosettaVisualizationApplication.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnCameraMovementIncrease, 45, SpringLayout.EAST, lblSpeed);
		springLayout.putConstraint(SpringLayout.WEST, lblCamSpeed, 6, SpringLayout.EAST, btnCameraMovementIncrease);
		btnCameraMovementIncrease.setIcon(new ImageIcon(MainWindow.class.getResource("/Icons/view-sort-ascending.png")));
		btnCameraMovementIncrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				float currSpeed = Universe.getApplication().flyCamMoveSpeed;
				currSpeed += 5;
				Universe.getApplication().flyCamMoveSpeed = currSpeed;
				Universe.getApplication().getFlyByCamera().setMoveSpeed(currSpeed);
				lblCamSpeed.setText("Cam speed: " + currSpeed);
				
			}
		});
		frmRosettaVisualizationApplication.getContentPane().add(btnCameraMovementIncrease);
		
		JButton btnCameraMovementDecrease = new JButton("");
		springLayout.putConstraint(SpringLayout.NORTH, btnCameraMovementDecrease, 3, SpringLayout.NORTH, frmRosettaVisualizationApplication.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnCameraMovementDecrease, 6, SpringLayout.EAST, lblCamSpeed);
		btnCameraMovementDecrease.setIcon(new ImageIcon(MainWindow.class.getResource("/Icons/view-sort-descending.png")));
		btnCameraMovementDecrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				float currSpeed = Universe.getApplication().flyCamMoveSpeed;
				currSpeed -= 5;
				Universe.getApplication().flyCamMoveSpeed = currSpeed;
				Universe.getApplication().getFlyByCamera().setMoveSpeed(currSpeed);
				lblCamSpeed.setText("Cam speed: " + currSpeed);
			}
		});
		frmRosettaVisualizationApplication.getContentPane().add(btnCameraMovementDecrease);
		
		//final JLabel lblSpeed = new JLabel("Speed: 1x");
		springLayout.putConstraint(SpringLayout.NORTH, lblSpeed, 10, SpringLayout.NORTH, frmRosettaVisualizationApplication.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblSpeed, 6, SpringLayout.EAST, btnForwardsButton);
		frmRosettaVisualizationApplication.getContentPane().add(lblSpeed);
		frmRosettaVisualizationApplication.getContentPane().add(lblCamSpeed);
		
		JButton btnZoomIn = new JButton("");
		springLayout.putConstraint(SpringLayout.SOUTH, btnZoomIn, 0, SpringLayout.SOUTH, btnBackwardsButton);
		springLayout.putConstraint(SpringLayout.EAST, btnZoomIn, -6, SpringLayout.WEST, lblZoom);
		btnZoomIn.setIcon(new ImageIcon(MainWindow.class.getResource("/Icons/zoom-in.png")));
		btnZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(newZoom > 0)
					newZoom = 0;
				newZoom -= 5;
				if(newZoom < -100)
					newZoom = 0;
				// derive fovY value
				Camera cam = Universe.getApplication().getCamera();
		        float h = cam.getFrustumTop();
		        float w = cam.getFrustumRight();
		        float aspect = w / h;

		        float near = cam.getFrustumNear();

		        float fovY = FastMath.atan(h / near)
		                  / (FastMath.DEG_TO_RAD * .5f);
		        fovY += newZoom * 0.1f;

		        h = FastMath.tan( fovY * FastMath.DEG_TO_RAD * .5f) * near;
		        w = h * aspect;

		        cam.setFrustumTop(h);
		        cam.setFrustumBottom(-h);
		        cam.setFrustumLeft(-w);
		        cam.setFrustumRight(w);
		        
		        lblZoom.setText("Zoom: " + newZoom);
		    }
		});
		frmRosettaVisualizationApplication.getContentPane().add(btnZoomIn);
		frmRosettaVisualizationApplication.getContentPane().add(lblZoom);
		
		JButton btnZoomOut = new JButton("");
		springLayout.putConstraint(SpringLayout.EAST, lblZoom, -7, SpringLayout.WEST, btnZoomOut);
		springLayout.putConstraint(SpringLayout.NORTH, btnZoomOut, 0, SpringLayout.NORTH, btnBackwardsButton);
		springLayout.putConstraint(SpringLayout.EAST, btnZoomOut, -10, SpringLayout.EAST, frmRosettaVisualizationApplication.getContentPane());
		btnZoomOut.setIcon(new ImageIcon(MainWindow.class.getResource("/Icons/zoom-out.png")));
		btnZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(newZoom < 0)
					newZoom = 0;
				if(newZoom > 100)
					newZoom = 0;
				newZoom += 5;
				// derive fovY value
				Camera cam = Universe.getApplication().getCamera();
		        float h = cam.getFrustumTop();
		        float w = cam.getFrustumRight();
		        float aspect = w / h;

		        float near = cam.getFrustumNear();

		        float fovY = FastMath.atan(h / near)
		                  / (FastMath.DEG_TO_RAD * .5f);
		        fovY += newZoom * 0.1f;

		        h = FastMath.tan( fovY * FastMath.DEG_TO_RAD * .5f) * near;
		        w = h * aspect;

		        cam.setFrustumTop(h);
		        cam.setFrustumBottom(-h);
		        cam.setFrustumLeft(-w);
		        cam.setFrustumRight(w);
		        
		        lblZoom.setText("Zoom: " + newZoom);
			}
		});
		frmRosettaVisualizationApplication.getContentPane().add(btnZoomOut);
		
		btnForwardsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Universe.getApplication().setAppSpeed(Universe.getApplication().getAppSpeed() * 2);
				lblSpeed.setText("Speed: " + Universe.getApplication().appSpeed + "x");
			}
		});
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public static JMenu getMnNew3dWindow() {
		return mnNew3dWindow;
	}
	
	public static void addOffViewMenus(String name, final OffView offView)
	{
		JMenuItem mntmOffViewScreen = new JMenuItem(name);
		mnNew3dWindow.add(mntmOffViewScreen);
		
		mntmOffViewScreen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Future<Boolean> fut = (Universe.getApplication().enqueue(new Callable<Boolean>() {
				    public Boolean call() throws Exception {
				    	Universe.getApplication().getEnvironment().addOffView(offView);
				    	return true;
				    }
				}));
				
			}
		});
	}
	public static void addOffViewMenus(final int id, final int width,final  int height,final  String controlDirection, 
			final VizObject viewedObject,final  Vector3f viewedAxis,final  VizObject viewerObject,final  Node scene, 
			final int location, final MaskObject mask, String name)
	{
		
		
		JMenuItem mntmOffViewScreen = new JMenuItem(name);
		mnNew3dWindow.add(mntmOffViewScreen);
		
		mntmOffViewScreen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Future<Boolean> fut = (Universe.getApplication().enqueue(new Callable<Boolean>() {
				    public Boolean call() throws Exception {
				    	OffView offView = new OffView(id, width, height, controlDirection, 
								viewedObject, viewedAxis, viewerObject, scene, 
								location, mask);
				    	new ViewTextReader(offView.getId());
						GlobalTools.offViewArray.add(offView);
				    	return true;
				    }
				}));
				
			}
		});
		

	}
	
	public static void addShowHideMenus()
	{
		cNodes = new ArrayList<Node>();
		cMenus = new ArrayList<JMenu>();
		vizObjBool = new ArrayList<Boolean>();
		for(final VizObject obj : GlobalTools.objectArray)
		{
			JMenu mntmObj = new JMenu(obj.getNode().getName());
			final JMenuItem mntmObjItem = new JMenuItem("Remove: " + obj.getNode().getName());
			
			//mntmObjItem.setFont(new FontFont.BOLD);
			mnShowHide.add(mntmObj);
			mnShowHide.add(mntmObjItem);
			
			mntmObjItem.setFont(mntmObjItem.getFont().deriveFont(Font.BOLD));
			//Font.createFont(Font.BOLD, (InputStream)Font.SANS_SERIF);
			
			for(final Spatial child : obj.getTranslationNode().getChildren())
			{
				
					
					JMenu mntmChildObj = new JMenu("Add/Remove: " + child.getName());
					mntmObj.add(mntmChildObj);
					
					mntmChildObj.setFont(mntmObjItem.getFont().deriveFont(Font.BOLD));
					mntmChildObj.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							Future<Boolean> fut = (Universe.getApplication().enqueue(new Callable<Boolean>() {
							    public Boolean call() throws Exception {
							    	//child.getParent().detachChild(child);
							    	if(child.getCullHint() == CullHint.Always)
							    		child.setCullHint(CullHint.Dynamic);
							    	else
							    		child.setCullHint(CullHint.Always);
							    	//child.setCullHint(CullHint.Dynamic);
							    	return true;
							    }
							}));
							
						}
					});
					
					cMenus.add(mntmChildObj);
					Node t = (Node)child;
					cNodes.add(t);
					
					for(final Spatial grandChild : t.getChildren())
					{

							JMenuItem mntmGrandChildObj = 
								new JMenuItem("Add/Remove: " + grandChild.getName());
							mntmChildObj.add(mntmGrandChildObj);
						
						
						mntmGrandChildObj.setFont(mntmObjItem.getFont().deriveFont(Font.BOLD));
						mntmGrandChildObj.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								Future<Boolean> fut = (Universe.getApplication().enqueue(new Callable<Boolean>() {
								    public Boolean call() throws Exception {
								    	//child.getParent().detachChild(child);
								    	if(grandChild.getCullHint() == CullHint.Always)
								    		grandChild.setCullHint(CullHint.Dynamic);
								    	else
								    		grandChild.setCullHint(CullHint.Always);
								    	//child.setCullHint(CullHint.Dynamic);
								    	return true;
								    }
								}));
								
							}
						});
						
						
					
					}
					
			}
			
			
			//String addRemoveText = "Remove";
			mntmObjItem.addActionListener(new ActionListener() {
				
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					Future<Boolean> fut = (Universe.getApplication().enqueue(new Callable<Boolean>() {
					    public Boolean call() throws Exception {
					    	obj.loadOrRemove();
					    	if(obj.isConnected())
					    		mntmObjItem.setText("Remove: " + obj.getNode().getName());
					    	else
					    		mntmObjItem.setText("Add: " + obj.getNode().getName());
					    	return true;
					    }
					}));
					
				}
			});	
			
			
		}
				
			
				
	}
}
