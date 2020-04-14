package esa.esac.Rosetta.Visualization.File;

import com.jme3.scene.Node;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;

/**
 * Reads the binary JME file - j30.
 * Also creates the object node.
 * 
 * @author Dan Adrian Avram
 *
 * @version PreAlpha v0.21
 */
public class JMEFileReader extends FileReader implements ShapePosReader, J3oReader {
	private Node objectNode;
	
	
	/**
	 * Do not use.
	 */
	public JMEFileReader()
	{
		
	}
	

	@Override
	public void init() {
		setObjectNode(new Node());
		
	}
	
	@Override
	public Node createJ3oData(ObjectParams sp)
	{
		System.out.println(sp.getName());
		objectNode.setName(sp.getName());
		objectNode =(Node) GlobalTools.assetManager.loadModel(sp.getShapeData().getModelFile().getName());
		return objectNode;
	}
	
	/**
	 * Gets the object node of this object(model read from .j3o file).
	 * @return the object Node
	 */
	public Node getObjectNode() {
		return objectNode;
	}
	
	/**
	 * Sets the object node.
	 * 
	 * @param objectNode the object Node
	 */
	public void setObjectNode(Node objectNode) {
		this.objectNode = objectNode;
	}

	
	
}
