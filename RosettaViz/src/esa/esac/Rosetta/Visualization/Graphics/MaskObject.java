package esa.esac.Rosetta.Visualization.Graphics;

import java.util.ArrayList;

import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;

public class MaskObject extends AbstractObject {
	private Node orthoNode;
	
	public MaskObject(ObjectParams shape)
	{
		super(shape);	
		
		
		// init GUI type node
		orthoNode = new Node(super.getShapeParams().getName() + " ortho node");
		orthoNode.setQueueBucket(Bucket.Gui);
		orthoNode.setCullHint(CullHint.Never);
		
		connectNodes();
		
		load();	
	}
	
	public Node getOrthoNode()
	{
		return orthoNode;
	}
	
	@Override
	public void connectNodes() {
		orthoNode.attachChild(getNode());

		
	}
	
	@Override
	public void load() {

		if(!GlobalTools.viewNode.hasChild(getOrthoNode()))
			GlobalTools.viewNode.attachChild(getOrthoNode());
		
	}

	@Override
	public void remove() {

		if(GlobalTools.viewNode.hasChild(getNode()))
			GlobalTools.viewNode.detachChild(getNode());
		
	}

}
