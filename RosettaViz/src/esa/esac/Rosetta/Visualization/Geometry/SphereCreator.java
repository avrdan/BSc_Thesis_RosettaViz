package esa.esac.Rosetta.Visualization.Geometry;


import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;


/**
 * Used to create 3D spheres.
 * Can be easily used for creating planets or similar objects.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 */
public class SphereCreator extends AbstractGeometry {
	private Sphere sphere;
	
	/**
	 * Initializes itself.
	 * Used by the geometry factory to create the sphere geometry.
	 */
	public SphereCreator()
	{
		super();
	}
	
	@Override
	public void setParameters(ObjectParams sp) {
		setShapeParams(sp);
		
		//System.out.println(super.getShapeParams().toString());
		sphere = new Sphere(128, 128, getShapeParams().getRadius(), true, false);
	
		setGeom(new Geometry(getShapeParams().getName(), sphere));
	}
	


}
