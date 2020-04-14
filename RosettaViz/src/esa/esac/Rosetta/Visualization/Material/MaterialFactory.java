package esa.esac.Rosetta.Visualization.Material;

import java.util.Hashtable;

import esa.esac.Rosetta.Visualization.DataStructure.MaterialParams;


/**
 * Allows the dynamic creation of different types of materials for different kinds of objects.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 *
 */
public class MaterialFactory {
	
	private static MaterialFactory instance;
	private Hashtable<String, String> extReaderTable;
	private MaterialParams mp;
	
	/**
	 * Sets the hash table that contains the information about the type of material.
	 * 
	 * @param table the table representing <MatType, MatClass>
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
	 * Retrieves the instance of this material factory. (singleton)
	 * 
	 * @return the GeometryFactory instance
	 */
	public static MaterialFactory getInstance()
	{
		instance = new MaterialFactory();
		Hashtable<String, String> table = new Hashtable<String, String>();
		
		/*table.put("lighting", "esa.esac.Rosetta.Viz.Dan.Avram.LightingMaterial");
		table.put("unshaded", "esa.esac.Rosetta.Viz.Dan.Avram.UnshadedMaterial");
		table.put("particle", "esa.esac.Rosetta.Viz.Dan.Avram.ParticleMaterial");
		
		table.put("personalType", "esa.esac.Rosetta.Viz.Dan.Avram.personalTypeMaterial"); // you can write your own shaders and add the new type
		*/
		
		table.put("lighting", "esa.esac.Rosetta.Visualization.Material.LightingMaterial");
		table.put("unshaded", "esa.esac.Rosetta.Visualization.Material.UnshadedMaterial");
		table.put("particle", "esa.esac.Rosetta.Visualization.Material.ParticleMaterial");
		
		table.put("personalType", "esa.esac.Rosetta.Viz.Dan.Avram.personalTypeMaterial"); // you can write your own shaders and add the new type
		
		instance.setReaderTable(table);
	
		return instance;
	}
	
	/**
	 * Gets the material creator.
	 * 
	 * @return MaterialCreator
	 */
	public MaterialCreator getCreator() 
	{
		try {
			MaterialCreator creator;
			creator = (MaterialCreator)Class.forName(extReaderTable.get(mp.getType())).newInstance();
			creator.setParameters(mp);
			
			//System.out.println("using creator class :" + creator.getClass().getName());
			return creator;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("Error");
		}
	}

	/**
	 * Set the material parameters, in order to know what kind of material is needed for this object.
	 * 
	 * @param mp the material parameters
	 */
	public void setMaterialParameters(MaterialParams mp) {
		this.mp = mp;
		
	}
}
