package esa.esac.Rosetta.Visualization.DataStructure;

public class TempPosData {
	int objId = 0;
	long startIndex = 0, endIndex = 0;
	
	public TempPosData(int objId, long startIndex, long endIndex)
	{
		this.objId      = objId;
		this.startIndex = startIndex;
		this.endIndex   = endIndex;
	}
	
	
	public int getObjId() {
		return objId;
	}
	public void setObjId(int objId) {
		this.objId = objId;
	}
	public long getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(long startIndex) {
		this.startIndex = startIndex;
	}
	public long getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(long endIndex) {
		this.endIndex = endIndex;
	}
	
	
}
