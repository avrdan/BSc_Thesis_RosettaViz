package esa.esac.Rosetta.Visualization.File;

import java.io.File;
import java.util.ArrayList;

/**
 * Assures the implementation of some necessary methods for creating shape and position data.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public interface ShapePosReader {
	
	/**
	 * Initialize the respective data structures by reading and parsing the file.
	 */
	public void init();
	
	/**
	 * Sets the input file from which to read shape or position data.
	 * 
	 * @param file 	the input file
	 */
	public void setFile(File file);
	
	/**
	 * Gets the input file.
	 * 
	 * @return 		the input file
	 */
	public File getFile();
	
	
}
