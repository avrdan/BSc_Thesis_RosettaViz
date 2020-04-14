package esa.esac.Rosetta.Visualization.DataStructure;

import java.util.ArrayList;
import java.util.Date;

import com.jme3.math.Vector3f;

/**
 * Represents a data structure holding all of the position data (positions, distances, dates & times).
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 */
public class PositionData {
	// general
	private int objId;
	
	// pos
	private ArrayList<Vector3f> positions;
	//private ArrayList<Float> distances;
	private ArrayList<Date> dts;
		
	public PositionData(int objId)
	{
		this.objId = objId;
		
		this.positions = new ArrayList<Vector3f>();
		this.dts       = new ArrayList<Date>();
	}
	
	public PositionData(int objId, ArrayList<Vector3f> positions, ArrayList<Date> datesTime)
	{
		this.positions = new ArrayList<Vector3f>();
		this.dts       = new ArrayList<Date>();
		
		this.objId = objId;
		this.positions = positions;
		this.dts = datesTime;
	}
	
	/**
	 * Gets the positions of the 3D objects.
	 * 
	 * @return an array of position vectors
	 */
	public ArrayList<Vector3f> getPositions()
	{
		return positions;
	}
		
	/**
	 * Gets the distances of the 3D objects.
	 * 
	 * @return an array of distances
	 */
	/*public ArrayList<Float> getDistances()
	{
		return distances;
	}*/
		
	/**
	 * Gets the dates and times of the positions of the 3D objects.
	 * 
	 * @return an array of strings representing dates & times
	 */
	public ArrayList<Date> getDatesAndTime()
	{
		return dts;
	}
	
	/**
	 * Sets the positions to the array given as a parameter.
	 * 
	 * @param positions the array of positions
	 */
	public void setPositions(ArrayList<Vector3f> positions)
	{
		this.positions =  positions;
	}
	
	/**
	 * Sets the distances to the array given as a parameter.
	 * 
	 * @param distances the array of distances
	 */
	/*public void setDistances(ArrayList<Float> distances)
	{
		this.distances = distances;
	}
	*/
	/**
	 * Sets the dates & times to the array given as a parameter.
	 * 
	 * @param datesTime the array of dates & times
	 */
	public void setDatesAndTime(ArrayList<Date> datesTime)
	{
		this.dts = datesTime;
	}

	public int getObjectId() {
		return objId;
	}

	public void setObjectId(int objId) {
		this.objId = objId;
	}
	
	public void add(int objId, Vector3f position, Date dt)
	{
		this.objId = objId;
		this.positions.add(position);
		this.dts.add(dt);
	}
	
	public void add(Vector3f position, Date dt)
	{
		this.positions.add(position);
		this.dts.add(dt);
	}
	
	@Override
	public String toString()
	{
		if(positions != null && dts != null)
			return "Object ID: " + objId + ", Number of Positions: " + positions.size() + ", Number of DateTimes: " + dts.size();
		else if(positions != null)
			return "Object ID: " + objId + ", Number of Positions: " + positions.size();
		else if(dts != null)
			return "Object ID: " + objId + ", Number of DateTimes: " + dts.size();
		else
			return "Object ID: " + objId + ".";
	}
}
