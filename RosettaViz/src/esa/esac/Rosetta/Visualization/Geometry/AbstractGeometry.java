package esa.esac.Rosetta.Visualization.Geometry;

import java.util.ArrayList;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.texture.Texture;

import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;



/**
 *  Represents an abstract type of geometry which can later be subclassed and instantiated into some concrete geometry type.
 * 
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 */
public abstract class AbstractGeometry implements GeometryCreator {
	private String name;
	
	private ObjectParams sp;
	//private Geometry geom;
	
	private ArrayList<Geometry> geomArray;
	
	/** Used by the geometry factory to create a new type of geometry.
	 * 
	 */
	public AbstractGeometry()
	{
		
	}
	
	
	/** Sets the shape parameters and stores them so that a type of geometry can be created according to the available data.
	 * 
	 * @param sp the shape parameters that specify the type of geometry to be created
	 */
	public void setShapeParams(ObjectParams sp)
	{
		this.sp = sp;
		setName(sp.getName());
		this.geomArray = new ArrayList<Geometry>();
	}
	
	/**
	 * @return Returns the shape parameters, which specify this geometry
	 */
	public ObjectParams getShapeParams()
	{
		return sp;
	}
	
/*	public Geometry getGeom() {
		return geom;
	}

	public void setGeom(Geometry geom) {
		this.geom = geom;
	}
*/
	/** Gets this geometry array which contains all of the geometries of this 3d object that needs to be created.
	 * 
	 * @return Returns this geometry array.
	 */
	public ArrayList<Geometry> getGeom() {
		return geomArray;
	}

	/** Sets the array of geometries.
	 * @param geomArray the array of geometries which specify the physical structure of this 3d object
	 */
	public void setGeom(ArrayList<Geometry> geomArray) {
		this.geomArray = geomArray;
	}
	
	/**
	 * @param geom the geometry to be added to this geometry array
	 */
	public void setGeom(Geometry geom) {
		this.geomArray.add(geom);
	}
	
	
	/** Gets the name of this geometry
	 * @return the name of this geometry
	 */
	public String getName() {
		return name;
	}

	/** Sets the name of this geometry
	 * @param name the name of this geometry
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/*@Override
	public Geometry create()
	{
		return geom;
	}*/
	
	
	@Override
	public ArrayList<Geometry> create()
	{
		return geomArray;
	}
}
