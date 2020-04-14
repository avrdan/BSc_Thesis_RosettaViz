package esa.esac.Rosetta.Visualization.Graphics;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 * Represents the angle direction arrows. For example: the X,Y,Z arrows.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 *
 */
public class CoordinateArrow extends Arrow3D {
		
		
		/** Do not use this constructor. For internal use only.
		 * 
		 */
		public CoordinateArrow()
		{
			
		}
		
		/**
		 * Represents an angle direction arrow which is created using the following:
		 * 
		 * @param model the path to the physical model created with a modelling tool (for example: Blender)
		 * @param color the color of the arrow
		 * @param angle the angle of the arrow with respect to
		 * @param axis  the axis 
		 */
		public CoordinateArrow(String model, ColorRGBA color, float angle, Vector3f axis)
		{
			// calls the superclass to set some common parameters
			super(model, color);
			
			// sets initial direction
			super.getArrowDirection().fromAngleAxis(angle, axis);
			super.getArrow().setLocalRotation(super.getArrowDirection());
		}
		
	
		
		/**
		 * Sets the direction of the arrow to the one specified.
		 * Useful for updating the arrow direction while the application is running.
		 * 
		 * @param angle the angle of the arrow with respect to
		 * @param axis  the axis
		 */
		public void setArrowDirection(float angle, Vector3f axis)
		{
			super.getArrowDirection().fromAngleAxis(angle, axis);
			super.getArrow().setLocalRotation(super.getArrowDirection());
		}
		
		
}
