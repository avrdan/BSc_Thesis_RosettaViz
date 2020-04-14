package esa.esac.Rosetta.Visualization.File;

import java.util.Hashtable;


/**
 * Allows the dynamic reading of different types of files.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class ReaderFactory {
	
	private static ReaderFactory instance;
	private Hashtable<String, String> extReaderTable;
	
	/**
	 * Sets the hash table that contains the information about the type of file.
	 * 
	 * @param table the table representing <FileType, FileClass>
	 */
	public void setReaderTable(Hashtable<String, String> table)
	{
		this.extReaderTable = table;
	}
	
	/**
	 * Gets the hash table.
	 * 
	 * @return the table
	 */
	public Hashtable<String, String> getReaderTable()
	{
		return this.extReaderTable;
	}
	
	/**
	 * Retrieves the instance of this reader factory. (singleton)
	 * 
	 * @return the ReaderFactory instance
	 */
	public static ReaderFactory getInstance()
	{
		instance = new ReaderFactory();
		Hashtable<String, String> table = new Hashtable<String, String>();
		
		table.put("mod", "esa.esac.Rosetta.Visualization.File.ModFileReader"); // ModFileReader.class
		table.put("pos", "esa.esac.Rosetta.Visualization.File.PosFileReader");
		table.put("j3o", "esa.esac.Rosetta.Visualization.File.JMEFileReader"); // ModFileReader.class
		
		instance.setReaderTable(table);
	
		return instance;
	}
	
	/**
	 * Gets the file reader.
	 * 
	 * @param extension the file extension
	 * @return the file reader(ShapePosReader - shape and position reader)
	 */
	public ShapePosReader getReader(String extension) 
	{
		try {
			ShapePosReader reader;
			reader = (ShapePosReader)Class.forName(extReaderTable.get(extension)).newInstance();			
			System.out.println("using reader class :" + reader.getClass().getName());
			return reader;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("Error");
		}
	}
}
