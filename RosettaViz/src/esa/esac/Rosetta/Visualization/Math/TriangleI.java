package esa.esac.Rosetta.Visualization.Math;

/**
 * Represents the triangle indices.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 */
public class TriangleI {
	private int P1_i, P2_i, P3_i;
	
	
	/**
	 * Creates a triangle index from 3 input integers, which represent the indices of the triangle's points.
	 * 
	 * @param P1_i 	the index of the first point
	 * @param P2_i  the index of the second point
	 * @param P3_i  the index of the third point
	 */
	public TriangleI(int P1_i, int P2_i, int P3_i)
	{
		this.P1_i = P1_i;
		this.P2_i = P2_i;
		this.P3_i = P3_i;
		
	}
	
	/**
	 * Gets the index of the first point.
	 * 
	 * @return the index of the first point
	 */
	public int getP1_i()
	{
		return this.P1_i;
	}
	
	/**
	 * Gets the index of the second point.
	 * 
	 * @return the index of the second point
	 */
	public int getP2_i()
	{
		return this.P2_i;
	}
	
	/**
	 * Gets the index of the third point.
	 * 
	 * @return the index of the third point
	 */
	public int getP3_i()
	{
		return this.P3_i;
	}

	
	@Override
	public String toString()
	{
			
		return "Triangle: " + "\n" + "Point 1 Index: " + this.P1_i + "\n"+ "Point 2 Index: "+ this.P2_i + "\n" + "Point 3 Index: "+ this.P3_i;
	}

}
