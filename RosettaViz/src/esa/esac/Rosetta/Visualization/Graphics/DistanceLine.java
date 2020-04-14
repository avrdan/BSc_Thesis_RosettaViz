package esa.esac.Rosetta.Visualization.Graphics;

import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;

import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;

public class DistanceLine extends AbstractObject {

	public DistanceLine(ObjectParams shape)
	{
		super(shape);	
		
		
		// init GUI type node
		//orthoNode = new Node(super.getShapeParams().getName() + " ortho node");
		//orthoNode.setQueueBucket(Bucket.Gui);
		//orthoNode.setCullHint(CullHint.Never);
		
		connectNodes();
		
		load();	
	}
	
	@Override
	public void connectNodes() {
		// TODO Auto-generated method stub

	}

	@Override
	public void load() {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
