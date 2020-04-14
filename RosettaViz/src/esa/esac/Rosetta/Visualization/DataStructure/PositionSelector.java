package esa.esac.Rosetta.Visualization.DataStructure;

public class PositionSelector {
	private int objId, startIndex, endIndex;
	
	public PositionSelector(int objId, int startIndex, int endIndex)
	{
		this.objId = objId;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	
	public synchronized void setInterval(int startIndex, int endIndex)
	{
		
	}
	
	public synchronized int getObjectId()
	{
		return objId;
	}
	
	public synchronized int getStartIndex()
	{
		return objId;
	}
	
	public synchronized int getEndIndex()
	{
		return objId;
	}
}
