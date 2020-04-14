package esa.esac.Rosetta.Visualization.Math;
import com.jme3.math.Vector3f;



/**
 * A math library which provides some useful vector functions.
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 */
public class MathVect {

/**
 * Creates a normalized vector.
 * 
 * @param p_start 	the start point vector
 * @param p_end		the end point vector
 * @param p_vector	the vector
 */
public static void VectCreate (Vector3f p_start, Vector3f p_end, Vector3f p_vector)
{
	p_vector.setX(p_end.getX() - p_start.getX());
	p_vector.setY(p_end.getY() - p_start.getY());
	p_vector.setZ(p_end.getZ() - p_start.getZ());
	
	VectNormalize(p_vector);
	
}

/**
 * Normalizes the specified vector.
 * 
 * @param p_vector the vector that will be normalized
 */
public static void VectNormalize(Vector3f p_vector) {
	float l_length;
	
	l_length = VectLength(p_vector);
	
	if (l_length == 0)
		l_length = 1;
	
	p_vector.setX(p_vector.getX() / l_length);
	p_vector.setY(p_vector.getY() / l_length);
	p_vector.setZ(p_vector.getZ() / l_length);
	
}

/**
 * Computes the length of the specified vector.
 * 
 * @param p_vector the vector for which the length will be computed
 * 
 * @return the length of the vector
 */
private static float VectLength(Vector3f p_vector) {
	return (float) Math.sqrt((p_vector.getX() * p_vector.getX() + p_vector.getY() * p_vector.getY() + p_vector.getZ() * p_vector.getZ()));
}

/**
 * Computes the scalar product of two vectors and returns it.
 * 
 * @param p_vector1 the first vector
 * @param p_vector2 the second vector
 *
 * @return the scalar product
 */
public static float VectScalarProduct(Vector3f p_vector1, Vector3f p_vector2)
{
	return (p_vector1.getX() * p_vector2.getX() + p_vector1.getY() * p_vector2.getY() + p_vector1.getZ() * p_vector2.getZ());
}

/**
 * Computes the vector product of two vectors and returns it.
 * 
 * @param p_vector1 the first vector
 * @param p_vector2 the second vector
 * @param p_normal  the normal
 * 
 * @return the vector product stored in the normal vector
 */
public static Vector3f VectCrossProduct(Vector3f p_vector1, Vector3f p_vector2, Vector3f p_normal)
{
	p_normal.setX((p_vector1.getY() * p_vector2.getZ()) - (p_vector1.getZ() * p_vector2.getY()));
	p_normal.setY((p_vector1.getZ() * p_vector2.getX()) - (p_vector1.getX() * p_vector2.getZ()));
	p_normal.setZ((p_vector1.getX() * p_vector2.getY()) - (p_vector1.getY() * p_vector2.getX()));
	
	return p_normal;
}

	
}
