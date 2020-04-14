package esa.esac.Rosetta.Visualization.DataStructure;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class AdditionalParams {

	
	
	
	
	
	// BORESIGHT!
	
	private Vector3f startPoint, dirVect;
	private float length;
	private ColorRGBA color;
	private float lineWidth;
	
	public AdditionalParams(Vector3f startPoint, float length, ColorRGBA color, float lineWidth)
	{
		this.startPoint = startPoint;
		this.length = length;
		this.color = color;
		this.lineWidth = lineWidth;
	}
	
	public Vector3f getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(float x, float y, float z) {
		this.startPoint = new Vector3f(x, y, z);
	}
	
	public Vector3f getDirVect() {
		return dirVect;
	}
	
	public void setDirVect(float x, float y, float z) {
		this.dirVect = new Vector3f(x, y, z);
	}
	
	public float getLength() {
		return length;
	}
	
	public void setLength(float length) {
		this.length = length;
	}
	public ColorRGBA getColor() {
		return color;
	}
	public void setColor(float r, float g, float b, float a) {
		this.color = new ColorRGBA(r, g, b, a);
	}
	public float getLineWidth() {
		return lineWidth;
	}
	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}
	// END BORESIGHT!
}
