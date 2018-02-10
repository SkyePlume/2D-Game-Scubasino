package entity;

import com.badlogic.gdx.physics.box2d.Body;

import org.andengine.entity.IEntity;

/**
 * Created by gerard on 21/09/2015.
 */
public interface CollidableEntity extends IEntity {
    public void setBody(Body body);
    public Body getBody();
    public String getType();
    public void runCollisionRoutine(Object o);
}
