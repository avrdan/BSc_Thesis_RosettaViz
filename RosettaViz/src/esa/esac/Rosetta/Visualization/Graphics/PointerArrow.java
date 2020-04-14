package esa.esac.Rosetta.Visualization.Graphics;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;


/**
 * Represents a pointer arrow. 
 * It points towards a specified object from the 3D world.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 *
 */
public class PointerArrow extends Arrow3D {
		private VizObject destObj;
		private VizObject startObj;

		Node arrowNode;

		
		/**
		 * Do not use.
		 */
		public PointerArrow()
		{
			
		}
		
		/**
		 * Constructs the arrow and initializes it.
		 * 
		 * @param model						the 3D model of this arrow
		 * @param color						the color of this arrow
		 * @param destinationObject			the destination object that this arrow points to
		 * @param startObject				the object where the arrow is created
		 */
		public PointerArrow(String model, ColorRGBA color, VizObject destinationObject, VizObject startObject)
		{
			super(model, color);
			this.setDestObj(destinationObject);
			this.setStartObj(startObject);
			super.getArrowDirection().fromStartEndVectors((new Vector3f(startObject.getNode().getLocalTranslation().getX(), 
					startObject.getNode().getLocalTranslation().getY(), startObject.getNode().getLocalTranslation().getZ()).normalize()), 
					(new Vector3f(destinationObject.getNode().getLocalTranslation().getX(), destinationObject.getNode().getLocalTranslation().getY(), 
							destinationObject.getNode().getLocalTranslation().getZ() ).normalize()));
		}
			
		/**
		 * Sets the direction the arrow points towards.
		 * Used for updating the rotation every frame.
		 * 
		 * @param start				the start point
		 * @param end				the end point
		 * @param arrowNode			this arrow's node
		 * @param index				the index of this arrow in the arrow array
		 */
		public void setArrowDirection(Vector3f start, Vector3f end, Spatial arrowNode, int index)
		{	
			getArrowDirection().fromStartEndVectors(start, end);

			((Node) arrowNode).getChild(index).setLocalRotation(super.getArrowDirection());
		}
		
		/**
		 * Sets the direction the arrow points towards.
		 * Used for updating the rotation every frame.
		 * 
		 * @param start				the start point
		 * @param end				the end point
		 * @param arrowNode			this arrow's node
		 * @param index				the index of this arrow in the arrow array
		 */
		public void setArrowDirection(Vector3f start, Vector3f end, Spatial arrowNode)
		{	
			getArrowDirection().fromStartEndVectors(start, end);

			this.getArrow().setLocalRotation(super.getArrowDirection());
		}
		
		/**
		 * Gets the node of the arrow.
		 * 
		 * @return the arrow node
		 */
		public Node getPtrArrowNode()
		{
			return this.arrowNode;
		}

		public VizObject getDestObj() {
			return destObj;
		}

		public void setDestObj(VizObject destObj) {
			this.destObj = destObj;
		}

		public VizObject getStartObj() {
			return startObj;
		}

		public void setStartObj(VizObject startObj) {
			this.startObj = startObj;
		}
		
}
