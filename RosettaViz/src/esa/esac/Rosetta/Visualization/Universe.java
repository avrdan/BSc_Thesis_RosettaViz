package esa.esac.Rosetta.Visualization;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.swing.JOptionPane;
import javax.swing.text.DateFormatter;







import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.MatParam;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Texture;
import com.jme3.texture.Image.Format;
import com.jme3.util.BufferUtils;
import com.jme3.util.SkyFactory;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;

import esa.esac.Rosetta.Visualization.DB.ArrowReader;
import esa.esac.Rosetta.Visualization.DB.BoresightReader;
import esa.esac.Rosetta.Visualization.DB.CaptionReader;
import esa.esac.Rosetta.Visualization.DB.ConnectToDatabase;
import esa.esac.Rosetta.Visualization.DB.FullPositionReader;
import esa.esac.Rosetta.Visualization.DB.FullTrajectoryLineReader;
import esa.esac.Rosetta.Visualization.DB.OneRotationReader;
import esa.esac.Rosetta.Visualization.DB.LandmarkReader;
import esa.esac.Rosetta.Visualization.DB.ObjDistLineReader;
import esa.esac.Rosetta.Visualization.DB.OffViewReader;
import esa.esac.Rosetta.Visualization.DB.PositionReader;
import esa.esac.Rosetta.Visualization.DB.TrajectoryLineReader;
import esa.esac.Rosetta.Visualization.DB.ViewTextReader;
import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;
import esa.esac.Rosetta.Visualization.DataStructure.PositionData;
import esa.esac.Rosetta.Visualization.DataStructure.PositionSelector;
import esa.esac.Rosetta.Visualization.DataStructure.TempPosData;
import esa.esac.Rosetta.Visualization.Graphics.HUDText;
import esa.esac.Rosetta.Visualization.Graphics.MaskObject;
import esa.esac.Rosetta.Visualization.Graphics.SPCObject;
import esa.esac.Rosetta.Visualization.Graphics.SolarObject;
import esa.esac.Rosetta.Visualization.Graphics.SunObject;
import esa.esac.Rosetta.Visualization.Graphics.VizObject;
import esa.esac.Rosetta.Visualization.Material.TextureCreator;
import esa.esac.Rosetta.Visualization.UI.MainWindow;
import esa.esac.Rosetta.Visualization.UI.OffView;







/**
 * Creates the 3D World that the user can interact with.
 * Creates all objects and their attributes.
 * Extends the JMonkey SimpleApplication for initializing the 3D world.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21

 */
public class Universe extends SimpleApplication {

	
	private SolarObject atmosphere;
	//public Thread updater;
	public float totalTime, timeStep, timeCounter;
	public int appSpeed;
	//public Vector3f delta1, delta2;
	private Boolean isRunning = false;
	private Date startDate, endDate;
	private boolean simStart = false;
	
	public float flyCamMoveSpeed = 25f;
	
	public ArrayList<SolarObject> solarObjectArray;
	public ArrayList<SPCObject> spcObjectArray;
	public ArrayList<SunObject> sunObjectArray;

	
	private static volatile Universe staticUniverse;
	
	
	private Environment env;
	
	public boolean appStarted = false;
		
	private Map<Integer, FullPositionReader> fullPosReaderMap;
	
	private Map<Integer, ResultSet> fullPosResultMap;
	private Map<Integer, ResultSet> fullRotResultMap;
	private Map<Integer, ResultSet> oneRotResultMap;
	
	//private ArrayList<ResultSet> fullDateResultMap;

	public int posIndex = 0;
	
	private DirectionalLight sunLight;
	
	private int jump;
	
	public ResultSet solarRotation;
	private ResultSet rosettaRotation;
	
	private int scaleDistanceFactor = 1000000;
	
	private PositionData posData;
	private Future<PositionData> future;
	private ArrayList<PositionSelector> posSelArr;
	private ArrayList<PositionData>     posDataArr;
	//private ScheduledThreadPoolExecutor executor; 
	private ArrayList<TempPosData>		tempPosDataArr;
	
	long startIndex = 0;
	long endIndex = 1000;
	
	int objIndex = 0;
	int genIndex = 0;
	
	private ArrayList<Future<PositionData>> futureArray;
	private int objCounter = 0;
	private HUDText hudText;
	
	public int simSpeed = 1;
    /**
     * Returns a static accessor for the Universe application, so it can be used globally.
     * Useful for accessing JME/SimpleApplication specific attributes.
     * It is thread safe.
     * 
     * @return staticUniverse
     */
    private Universe(){
    
    }
    
  
    public synchronized static Universe getApplication(){
        if(staticUniverse == null){
            synchronized(Universe.class){
              if(staticUniverse == null){
            	  staticUniverse = new Universe();
              }
              else
              {
            	  if(!staticUniverse.getContext().isCreated())
            		  staticUniverse = null;
              }
            }
        }
        return staticUniverse;
    }
    
    public synchronized static Universe makeNull(){
        if(staticUniverse != null){
            synchronized(Universe.class){
              if(staticUniverse != null){
            	  staticUniverse = null;
            	  System.gc();
              }
            }
        }
        return staticUniverse;
    }
    
   /* private Callable <PositionData> updatePosition = new Callable<PositionData>()
    {

		@Override
		public PositionData call() throws Exception {
			int objId = 0;
			long sI = 0, eI = 0;
			
			
			
			TempPosData tpd = tempPosDataArr.get(genIndex);
			objId = tpd.getObjId();
			sI    = tpd.getStartIndex();
			eI    = tpd.getEndIndex();
			// Data data = myWorld.getData();
			// PositionData pd = myWorld.getData();
			// myWorld?
			genIndex++;
			
			posData = new PositionData(objId);
			try {
				PreparedStatement statement = ConnectToDatabase.con.prepareStatement("SELECT " +
						"pos_x, pos_y, pos_z, pos_time FROM rosetta_pos " +
						"WHERE vo_id = "+ objId +" LIMIT " + sI + ", " + eI);
				
				ResultSet result = statement.executeQuery();

				
				while(result.next())
				{
					posData.add(new Vector3f(Float.parseFloat(result.getString(1)), 
							Float.parseFloat(result.getString(2)), Float.parseFloat(result.getString(3))), result.getTimestamp(4));
					
					
				
				}
								
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
					    "Could not retrieve desired data.",
					    "Database query error",
					    JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			
			return posData;
		}
    	
    };
*/    

   public AppStateManager getStateManger()
   {
	   return stateManager;
   }
	
	@Override
	public void simpleInitApp() {
		PlotToggleState plotToggleState = new PlotToggleState();
		stateManager.attach(plotToggleState);
		

		//GlobalTools.simpleApp = this.getApplication().;
		GlobalTools.executor = new ScheduledThreadPoolExecutor(4);
		posSelArr = new ArrayList<PositionSelector>();
		tempPosDataArr = new ArrayList<TempPosData>();
		
		GlobalTools.init3D(renderManager, renderer, rootNode, assetManager, viewPort);
		GlobalTools.mainPanel.attachTo(GlobalTools.viewPort);
		
		
		guiViewPort.setEnabled(true);
		Geometry g;
		Node n;
		
		// hud text
		hudText = new HUDText(guiNode, guiViewPort);
		
		// initialize object arrays
		sunObjectArray = new ArrayList<SunObject>();
		solarObjectArray = new ArrayList<SolarObject>();
		spcObjectArray = new ArrayList<SPCObject>();
		
		GlobalTools.objectArray = new ArrayList<VizObject>();

		
	
		
		
		fullPosReaderMap = Collections.synchronizedMap(new HashMap<Integer, FullPositionReader>());
		
		//fullPosReaderMap = new HashMap<Integer, FullPositionReader>();
		//fullPosResultMap = new HashMap<Integer, ResultSet>();
		//fullRotResultMap = new HashMap<Integer, ResultSet>();
		//oneRotResultMap  = new HashMap<Integer, ResultSet>();
		fullPosResultMap = Collections.synchronizedMap(new HashMap<Integer, ResultSet>());
		fullRotResultMap = Collections.synchronizedMap(new HashMap<Integer, ResultSet>());
		oneRotResultMap  = Collections.synchronizedMap(new HashMap<Integer, ResultSet>());
		
		
		
		for (ObjectParams op : GlobalTools.shapeParamsArray) {
			if (op.getType().equals("sun"))
			{
				SunObject sunObj = new SunObject(op);
				sunObjectArray.add(sunObj);
				
				// add to obj array
				GlobalTools.objectArray.add(sunObj);
				
				//fullPosReaderMap.put(sunObj.getId(), new FullPositionReader(sunObj.getId(), startDate, endDate));
				
				FullPositionReader fpr = new FullPositionReader(sunObj.getId(), startDate, endDate);
				fullPosResultMap.put(sunObj.getId(), fpr.getResultSet());
				//FullRotationReader frr = new FullRotationReader(sunObj.getId());
				//fullRotResultMap.put(sunObj.getId(), frr.getResultSet());
				
				new ArrowReader(sunObj);
				
				new CaptionReader(sunObj);
				
				sunObjectArray.get(0).setPosition(5, 30, 5);
			
				new ObjDistLineReader(sunObj);
				
				GlobalTools.posPlotArr.add(new PositionData(sunObj.getId()));
				
				
				PositionSelector ps = new PositionSelector(sunObj.getId(), 0, 1000);
				posSelArr.add(ps);
				
				objCounter++;
				
				
				//new DBPlotToggle(sunObj);
				//PlotToggleControl objControl = new PlotToggleControl();
				//objControl.setEnabled(false);
				//sunObj.addControl(objControl);
				
			}
			else if(op.getType().equals("solar"))
					{
						SolarObject solarObj = new SolarObject(op);
						solarObjectArray.add(solarObj);
						
						// add to obj array
						GlobalTools.objectArray.add(solarObj);
						
						//fullPosReaderMap.put(solarObj.getId(), new FullPositionReader(solarObj.getId(), startDate, endDate));
						
						FullPositionReader fpr = new FullPositionReader(solarObj.getId(), startDate, endDate);
						fullPosResultMap.put(solarObj.getId(), fpr.getResultSet());
						
						OneRotationReader frr = new OneRotationReader(solarObj.getId());
						oneRotResultMap.put(solarObj.getId(), frr.getResultSet());
						
						try {
							if(oneRotResultMap.get(solarObj.getId()).first())
								solarRotation = oneRotResultMap.get(solarObj.getId());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						new ArrowReader(solarObj);
						
						new LandmarkReader(solarObj);
		
						
						
						new CaptionReader(solarObj);
					
						GlobalTools.posPlotArr.add(new PositionData(solarObj.getId()));
						
						PositionSelector ps = new PositionSelector(solarObj.getId(), 0, 1000);
						posSelArr.add(ps);
						new ObjDistLineReader(solarObj);
						objCounter++;
						
						//new DBPlotToggle(solarObj);
						//PlotToggleControl objControl = new PlotToggleControl();
						//objControl.setEnabled(false);
						//solarObj.addControl(objControl);
					}
			else if (op.getType().equals("spc")) {
				SPCObject spcObj = new SPCObject(op);
				
				
				
				//fullPosReaderMap.put(spcObj.getId(), new FullPositionReader(spcObj.getId(), startDate, endDate));
				
				FullPositionReader fpr = new FullPositionReader(spcObj.getId(),startDate, endDate);
				fullPosResultMap.put(spcObj.getId(), fpr.getResultSet());
			
//				FullRotationReader frr = new FullRotationReader(spcObj.getId());
//				fullRotResultMap.put(spcObj.getId(), frr.getResultSet());
				
				OneRotationReader frr = new OneRotationReader(spcObj.getId());
				oneRotResultMap.put(spcObj.getId(), frr.getResultSet());
				
				try {
					if(oneRotResultMap.get(spcObj.getId()).first())
						rosettaRotation = oneRotResultMap.get(spcObj.getId());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				new ArrowReader(spcObj);
				
				new BoresightReader(spcObj);
				new LandmarkReader(spcObj);
				
				new CaptionReader(spcObj);
				
				spcObj.setPosition(0, -30, 0);
				spcObjectArray.add(spcObj);
				
				// add to obj array
				GlobalTools.objectArray.add(spcObj);
				new ObjDistLineReader(spcObj);

				new FullTrajectoryLineReader(spcObj);
				GlobalTools.posPlotArr.add(new PositionData(spcObj.getId()));
				
				PositionSelector ps = new PositionSelector(spcObj.getId(), 0, 1000);
				posSelArr.add(ps);
				
				objCounter++;
				
				//new DBPlotToggle(spcObj);
				//PlotToggleControl objControl = new PlotToggleControl();
				//objControl.setEnabled(false);
				//spcObj.addControl(objControl);
			}
			else if (op.getType().equals("fovMask")) {

				MaskObject mask = new MaskObject(op);
				GlobalTools.maskArray.add(mask);
			}
			else if (op.getType().equals("atm")) {
			
				SolarObject atmosphere = new SolarObject(op);
				GlobalTools.objectArray.add(atmosphere);

			}
			else if (op.getType().equals("ROI")) {
				SolarObject roi = new SolarObject(op);
				roi.getNode().setQueueBucket(Bucket.Opaque);
				
				GlobalTools.objectArray.add(roi);
			}
			
		}

		flyCam.setMoveSpeed(flyCamMoveSpeed);
		flyCam.setEnabled(true);
		
		cam.setLocation(new Vector3f(0, 0, 30));
			
		
	
		// construct node hierarchy
		
		rootNode.attachChild(GlobalTools.sunLightNode);
		GlobalTools.sunLightNode.attachChild(GlobalTools.viewNode);
	
		GlobalTools.viewNode.attachChild(GlobalTools.sunNode);

		
		
		
		GlobalTools.sunNode.attachChild(GlobalTools.solarObjectNode);
		GlobalTools.sunNode.attachChild(GlobalTools.spcObjectNode);
		
		// set drag to rotate
		flyCam.setDragToRotate(true);	
		
		// set sky map
		//Texture tex_space = assetManager.loadTexture("nebulacube.jpg");
		GlobalTools.sunNode.attachChild(SkyFactory.createSky(GlobalTools.assetManager, "CustomStarField.dds", false));
		totalTime = 0;

		timeStep = 1;
		
		
		
		appSpeed = 1;
		
		timeCounter = 0;
		
		env = new Environment();

		if(appStarted == false)
			appStarted = true;
	
		//startDate = new Date(0);
		//endDate = new Date(0);
		System.out.println(startDate);
		System.out.println(endDate);
		
		posIndex = 1;
		jump = 0;
		sunLight = new DirectionalLight();
		
		// set the lights		
		//sunLight.setDirectionalSunLight(ColorRGBA.White, new Vector3f(0, 1, 1));
		sunLight.setDirection(new Vector3f(0, 1, 1));
		//GlobalTools.sunLightNode.addLight(sunLight);
		
		//GlobalTools.rootNode.detachAllChildren();
		new OffViewReader();
		GlobalTools.mainPanel.attachTo(guiViewPort);
		MainWindow.addShowHideMenus();
		
		
//		for(OffView ov: GlobalTools.offViewArray)
//		{
//			new ViewTextReader(ov.getId());
//		}
		
		// initialize future array
		futureArray = new ArrayList<Future<PositionData>>();
	}
	


	
	@Override
    public void simpleUpdate(float tpf) {
		rootNode.updateGeometricState();
		rootNode.updateLogicalState(tpf);
		
		

		if(isRunning)
		{
				totalTime += tpf;
				timeCounter += tpf;
				
//				try {
//					if(fullPosResultMap.get(1).next())
//						hudText.updateText(fullPosResultMap.get(1).getTimestamp("pos_time").toString());
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
						
					if(timeCounter > timeStep)
						{
							try {
								if(fullPosResultMap.get(1) != null)
								{
									hudText.updateText(fullPosResultMap.get(1).getTimestamp("pos_time").toString());
								}
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
								//System.out.println("APP SPEED: " + appSpeed);
								//System.out.println("TIMESTEP: " + timeStep);
							
								
							
							/*for(VizObject obj : GlobalTools.objectArray)
							{
								try {
									if(fullRotResultMap.get(obj.getId()) != null)
									{
										jump = 0;
										
										
									     
										
										while(jump < appSpeed*timeStep)
											{
											
											jump++;
											}
											
												if(fullRotResultMap.get(obj.getId()).next())
												{
										
													obj.setRotation(new Matrix3f(fullRotResultMap.get(obj.getId()).getFloat(2), 
															fullRotResultMap.get(obj.getId()).getFloat(3), fullRotResultMap.get(obj.getId()).getFloat(4),
															fullRotResultMap.get(obj.getId()).getFloat(5), fullRotResultMap.get(obj.getId()).getFloat(6),
															fullRotResultMap.get(obj.getId()).getFloat(7), 
															fullRotResultMap.get(obj.getId()).getFloat(8), 											
															fullRotResultMap.get(obj.getId()).getFloat(9), 
															fullRotResultMap.get(obj.getId()).getFloat(10)));
										
												}
											
									
											}
									
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}*/
							
							
							// multithreading retry
							/*objIndex = 0;
							for(VizObject obj : GlobalTools.objectArray)
							{
								try
								{
									if(posData == null && futureArray.get(objIndex) == null)
									{
										// calculate future
									}
									else if (futureArray.get(objIndex) != null)
									{
										if(futureArray.get(objIndex).isDone())
										{
											
											posData = futureArray.get(objIndex).get();
											
											futureArray.set(objIndex, null);
										}
									}
									else if(futureArray.get(objIndex).isCancelled())
									{
										futureArray.set(objIndex, null);
									}
									
									objIndex++;
								}
								catch(Exception e){ 
								      e.printStackTrace();
								    }
							}*/
							
							// END multithreading retry
							
							
							// multithreading
							/*objIndex = 0;
							genIndex = 0;
							for(VizObject obj : GlobalTools.objectArray)
							{
							
								try{
									if(posData == null && future == null)
									{
										
										TempPosData tpd = new TempPosData(obj.getId(), startIndex, endIndex);
										tempPosDataArr.add(objIndex, tpd);
										
										future = GlobalTools.executor.submit(updatePosition);
										
										
										
										
										startIndex += 1000;
										endIndex   += 1000;
									}
									else if (future != null)
									{
										if(future.isDone())
										{
											
											posData = future.get();
											
											future  = null;
										}
									}
									else if(future.isCancelled())
									{
										future = null;
									}
									
									objIndex++;
								}
								catch(Exception e){ 
								      e.printStackTrace();
								    }
								
							}*/
							// end multithreading
							
							/*for(VizObject obj : GlobalTools.objectArray)
							{
									obj.setPtrArrowDirection();
							}*/
							// single-thread position implementation - works
						/*try {
							if(fullPosResultMap.get(1).next())
								hudText.updateText(fullPosResultMap.get(1).getTimestamp("pos_time").toString());
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
							for(VizObject obj : GlobalTools.objectArray)
								{
								//System.out.println("DOES IT WORK?");
								obj.setPtrArrowDirection();
								
								try {
										System.out.println("Index: " + posIndex);
										
										if(fullPosResultMap.get(obj.getId()) != null)
										{
											
											//hudText.updateText(fullPosResultMap.get(obj.getId()).getTimestamp("pos_time").toString());
									
											jump = 0;
										
											while(jump < appSpeed*timeStep)
												{
												 fullPosResultMap.get(obj.getId()).next();
											     jump++;
												}
											
												if(fullPosResultMap.get(obj.getId()).next())
												{
													
													if(Math.abs(Float.parseFloat(fullPosResultMap.get(obj.getId()).getString(1))) > 100000)
															{
																obj.setPosition(new Vector3f(Float.parseFloat(fullPosResultMap.get(obj.getId()).getString(1)), 
																Float.parseFloat(fullPosResultMap.get(obj.getId()).getString(2)), 
																Float.parseFloat(fullPosResultMap.get(obj.getId()).getString(3))).divide(scaleDistanceFactor));
															}
													else
														obj.setPosition(new Vector3f(Float.parseFloat(fullPosResultMap.get(obj.getId()).getString(1)), 
																Float.parseFloat(fullPosResultMap.get(obj.getId()).getString(2)), 
																Float.parseFloat(fullPosResultMap.get(obj.getId()).getString(3))));
													
													
													
											
//													System.out.println("Pos: " + new Vector3f(Float.parseFloat(fullPosResultMap.get(obj.getId()).getString(1)), 
//															Float.parseFloat(fullPosResultMap.get(obj.getId()).getString(2)), 
//															Float.parseFloat(fullPosResultMap.get(obj.getId()).getString(3))));
													
												
												}
										}
//										
										
									
										} catch (SQLException e) {
											JOptionPane.showMessageDialog(null,
												    "Could not retrieve desired data.",
												    "Database query error",
												    JOptionPane.ERROR_MESSAGE);
											e.printStackTrace();
										}
									}
							       // end single-thread position reading
								
									// rotation
									for(SolarObject obj : solarObjectArray)
									{
										try {
											obj.setRotation((solarRotation.getFloat(4)/(3600*24))*timeCounter*timeStep*appSpeed, 
													new Vector3f(solarRotation.getFloat(1), solarRotation.getFloat(2), solarRotation.getFloat(3)));
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								
									for(SPCObject obj : spcObjectArray)
									{
										try {
											obj.setRotation((rosettaRotation.getFloat(4)/(360*24))*timeCounter*timeStep*appSpeed, 
													new Vector3f(rosettaRotation.getFloat(1), rosettaRotation.getFloat(2), rosettaRotation.getFloat(3)));
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									// end rotation
																			
								for(OffView offView : GlobalTools.offViewArray)
								{
									offView.update();
								}
								
								timeCounter = 0;
								posIndex += appSpeed*timeStep; 
						
						
						
								if(simStart)
								{
									simStart = false;
								}

							}	
			
			
	}
}
	
	/**
	 * Gets the end date & time of the simulation.
	 * 
	 * @return the end date and time
	 */
	public synchronized Date getEndDate() {
		return endDate;
	}
	
	/**
	 * Sets the end date & time of the simulation.
	 * 
	 * @param endDate	the new end date and time
	 */
	public synchronized void setEndDate(Date endDate) {
		this.endDate = endDate;
	
	}
	
	/**
	 * Gets the start date & time of the simulation.
	 * 
	 * @return	the start date and time
	 */
	public synchronized Date getStartDate() {
		return startDate;
	}
	
	/**
	 * Sets the start date & time of the simulation.
	 * 
	 * @param startDate	the new start date and time
	 */
	public synchronized void setStartDate(Date startDate) {
		
		this.startDate = startDate;

	}
	
	/**
	 * Gets the speed of the simulation.
	 * @return	gets the speed of the simulation.
	 */
	public int getAppSpeed() {
		return appSpeed;
	}
	
	/**
	 * Sets the speed of the simulation.
	 * The default speed is 1 (1x).
	 * 
	 * @param appSpeed	the speed of the simulation
	 */
	public void setAppSpeed(int appSpeed) {
		this.appSpeed = appSpeed;
		if(appSpeed <= 32)
			timeStep =  1.0f/(float)appSpeed;
	}
	
	/**
	 * Checks if the simulation has been started or not.
	 * 
	 * @return the simulation start flag
	 */
	public boolean isSimStarted() {
		return simStart;
	}
	
	/**
	 * Sets the simulation start flag.
	 * 
	 * @param simStart	the simulation start flag
	 */
	public void setSimStart(boolean simStart) {
		this.simStart = simStart;
	}
	
	@Override
	public void destroy(){
	    super.destroy();
	    //executor.shutdown(); // shutdown() I think it is...
	}
	
	/** Gets the environment. 
	 * 
	 * @return the environment which contains the views and windows
	 */
	public Environment getEnvironment()
	{
		return env;
	}
	
	/**
	 * Checks if the simulation is paused.
	 * 
	 * @return the status of the simulation (paused/unpaused)
	 */
	public Boolean isRunning()
	{
		return isRunning;
	}
	
	/**
	 * Sets the state of the simulation to paused or unpaused.
	 * @param pause	true to pause, false to resume
	 */
	public void setRunning(Boolean isRunning)
	{
		this.isRunning = isRunning;
	}
	
	
}
