package entity;


import com.badlogic.gdx.physics.box2d.Body;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import constant.FORCE;
import constant.SPEED;
import resourceManagement.ResourceManager;

/**
 * Created by gerard on 19/08/2015.
 * v1.1 5thMay 2016
 */
public class Mine extends AnimatedSprite implements CollidableEntity, ITouchArea {
    private boolean isAnimated = false;
    boolean spent = false;
    private Body body;
    private static final String TYPE = "Mine";
    private float WIDTH;
    private SPEED speed;
    private FORCE force;
    private ResourceManager rMan;

    public Mine(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
        rMan = ResourceManager.getInstance();
        WIDTH = rMan.camera.getWidth()-this.getWidth();
        speed = SPEED.MEDIUM;
        this.stopAnimation(0);
    }


    public void setSpeed(SPEED s){speed = s;}

    public void setForce(FORCE f){force = f;}

    public void swimLeft(){
        applyForce(force.getVal(),0);
    }

    public void swimRight(){
        applyForce(-force.getVal(),0);
    }

    public void startAnim(SPEED speed){
        this.animate(speed.getVal());
        isAnimated = true;
    }

    public void stopAnim(){
        this.stopAnimation();
        isAnimated = false;
    }
    public boolean isAnimated(){return isAnimated;}

    public boolean isSpent(){return spent;}

    public void setSpent(boolean spent){this.spent = spent;}

    public void applyForce(float x, float y){body.setLinearVelocity( x, y);}

    protected void onManagedUpdate(float pSecondsElapsed){
        super.onManagedUpdate(pSecondsElapsed);
        if(this.getX() > WIDTH){swimRight();}
        if(this.getX() < 0){swimLeft();}
    }

    @Override
    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public Body getBody() {
        return null;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void runCollisionRoutine(Object o) {
        this.registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(0){
                    @Override
                    protected void onModifierFinished(IEntity pItem){
                        super.onModifierFinished(pItem);
                        startAnimateMine();
                    }
                },
                new DelayModifier(1.1f),
                new DelayModifier(0){
                    @Override
                    protected void onModifierFinished(IEntity pItem){
                        super.onModifierFinished(pItem);
                        stopAnimateMine();
                    }
                }
        ));

    }
    public void startAnimateMine(){this.animate(new long[]{100,100,100,100,100,100,100,100,100,500},0,9,false);}
    public void stopAnimateMine(){this.stopAnimation(0);}

}
