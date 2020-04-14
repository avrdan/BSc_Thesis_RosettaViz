package esa.esac.Rosetta.Visualization.File;

import com.jme3.scene.Node;

import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;

/**
 * Provides the interface for reading and constructing objects from binary .j3o  files.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public interface J3oReader {
	/**
	 * Creates the .j3o data based on the path given by the shape parameters.
	 * 
	 * @param sp the shape parameters
	 * @return	the object node
	 */
	public Node createJ3oData(ObjectParams sp);
	
}
