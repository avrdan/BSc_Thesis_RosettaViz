package esa.esac.Rosetta.Visualization.Geometry;

import java.util.ArrayList;

import com.jme3.scene.Geometry;

import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;



/**
 * Provides an interface to create the shape parameters and subsequently the geometry.
 * All future geometry creator classes must implement these methods in order to work correctly.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public interface GeometryCreator {
	/**
	 * Returns the final geometry array which can be used further to create the 3D object.
	 * 
	 * @return the array of geometries
	 */
	public ArrayList<Geometry> create();
	/**
	 * Sets the shape parameters which have been read from a source(for example DB)
	 * or supplied by a user.
	 * 
	 * @param sp the shape parameters
	 */
	public void setParameters(ObjectParams sp);

}
