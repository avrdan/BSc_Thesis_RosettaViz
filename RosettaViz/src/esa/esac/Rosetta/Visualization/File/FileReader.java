package esa.esac.Rosetta.Visualization.File;

import java.io.File;
import java.util.ArrayList;

import com.jme3.scene.Node;

import esa.esac.Rosetta.Visualization.DataStructure.ShapeData;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;

/**
 * Represents the file reader. 
 * Reads shape, position and j30 file types, based on file extension.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 *
 */
public abstract class FileReader {
	private File file;
	
	/**
	 * Sets an input file.
	 * 
	 * @param file the file
	 */
	public void setFile(File file)
	{
		this.file = file;
	}
	
	/**
	 * Gets the input file.
	 * 
	 * @return the file
	 */
	public File getFile()
	{
		return file;
	}
		
	/**
	 * Creates the node which contains a complete external model 
	 * imported from a modelling tool (for example: Blender).
	 * 
	 * @param file 	the input file
	 * @param sp 	the shape parameters - the name must be known and it is enclosed in these parameters
	 * @return		the node which contains the 3d object
	 */
	public static Node readJ3oFile(File file, ObjectParams sp)
	{
		String fileExtensionKey = getFileExtension(file);
		ShapePosReader reader = ReaderFactory.getInstance().getReader("j3o");
		reader.setFile(file);
		reader.init();
		System.out.println("Shape name in j30 reader" + sp.getName());
  		return ((J3oReader) reader).createJ3oData(sp);
	}
	
	/**
	 * Returns this file's extension.
	 * 
	 * @param afile the file to get the extension from
	 * @return a string representing the file extension
	 */
	public static String getFileExtension(File afile) 
	  {
		  String extension = afile.getName();
		  
		  System.out.println(extension);
		  
		  
		  int dotPos = extension.lastIndexOf(".");
		  extension = extension.substring(dotPos+1);
		  
		  System.out.println(extension);
		  
		  return extension;
	  }
	
	
	
}
