package esa.esac.Rosetta.Visualization.Geometry;

import java.util.Hashtable;

import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;

/**
 * Allows the dynamic creation of different types of geometries for different kinds of objects.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 *
 */
public class GeometryFactory {
	
	private static GeometryFactory instance;
	private Hashtable<String, String> extReaderTable;
	private ObjectParams sp;
	
	/**
	 * Sets the hash table that contains the information about the type of geometry.
	 * 
	 * @param table the table representing <GeomType, GeomClass>
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
	 * Retrieves the instance of this geometry factory. (singleton)
	 * 
	 * @return the GeometryFactory instance
	 */
	public static GeometryFactory getInstance()
	{
		instance = new GeometryFactory();
		Hashtable<String, String> table = new Hashtable<String, String>();
		
		/*table.put("custom", "esa.esac.Rosetta.Viz.Dan.Avram.CustomGeometryCreator"); 
		table.put("sphere", "esa.esac.Rosetta.Viz.Dan.Avram.SphereCreator");
		table.put("ellipsoid", "esa.esac.Rosetta.Viz.Dan.Avram.EllipsoidCreator"); 
		table.put("line", "esa.esac.Rosetta.Viz.Dan.Avram.LineObject");
		table.put("jmeText", "esa.esac.Rosetta.Viz.Dan.Avram.TextCreator");
		table.put("pEmitter", "esa.esac.Rosetta.Viz.Dan.Avram.ParticleEmitterCreator");
		table.put("mask", "esa.esac.Rosetta.Viz.Dan.Avram.MaskCreator");
		*/
		table.put("custom", "esa.esac.Rosetta.Visualization.Geometry.CustomGeometryCreator"); 
		table.put("sphere", "esa.esac.Rosetta.Visualization.Geometry.SphereCreator");
		table.put("ellipsoid", "esa.esac.Rosetta.Visualization.Geometry.EllipsoidCreator"); 
		table.put("line", "esa.esac.Rosetta.Visualization.Geometry.LineObject");
		table.put("jmeText", "esa.esac.Rosetta.Visualization.GeometryTextCreator");
		table.put("pEmitter", "esa.esac.Rosetta.Visualization.Geometry.ParticleEmitterCreator");
		table.put("mask", "esa.esac.Rosetta.Visualization.Geometry.MaskCreator");
		instance.setReaderTable(table);
	
		return instance;
	}
	
	/**
	 * Gets the geometry creator.
	 * 
	 * @return GeometryCreator
	 */
	public GeometryCreator getCreator() 
	{
		try {
			GeometryCreator creator;
			creator = (GeometryCreator)Class.forName(extReaderTable.get(sp.getGeometryType())).newInstance();			
			creator.setParameters(sp);
			//System.out.println("using reader class :" + creator.getClass().getName());
			return creator;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("Error");
		}
	}

	/**
	 * Set the shape parameters, in order to know what geometry is needed for this object.
	 * 
	 * @param shape the shape parameters
	 */
	public void setShapeParameters(ObjectParams shape) {
		this.sp = shape;
		
	}
}
