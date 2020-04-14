package esa.esac.Rosetta.Visualization.Geometry;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

import esa.esac.Rosetta.Visualization.DataStructure.ObjectParams;

/**
 * Represents a particle effect (atmosphere, dust, explosion etc...).
 * 
 * @author Dan Adrian Avram
 * 
 * @version PreAlpha v0.21
 */
public class ParticleEmitterCreator extends AbstractGeometry{
	ParticleEmitter emit;
	
	/**
	 * Used by the geometry factory to create a particle effect(emitter).
	 */
	public ParticleEmitterCreator()
	{
		super();
	}
	
	@Override
	public void setParameters(ObjectParams sp) {
		setShapeParams(sp);
		
		
		// PARAMETERS HERE ARE HARD CODED!!! TO BE CHANGED IN THE FUTURE.
		
		
		emit = new ParticleEmitter(getShapeParams().getName(), Type.Triangle, 5000);
        emit.setGravity(0, 0, 0);
        emit.getParticleInfluencer().setVelocityVariation(2);
        emit.setLowLife(1);
        emit.setHighLife(10);
        emit.getParticleInfluencer().setInitialVelocity(new Vector3f(0, .5f, 0));
        emit.setImagesX(15);
        //emit.setImagesY(15);
        emit.setStartSize(5);
        emit.setEndSize(5);
        
		//emit.setImagesY(150);
        
        // END <PARAMETERS HERE ARE HARD CODED!!! TO BE CHANGED IN THE FUTURE.>
		
        setGeom(emit);
        
	}

}
