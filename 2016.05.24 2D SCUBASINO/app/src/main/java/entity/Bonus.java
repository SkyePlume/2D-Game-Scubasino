package entity;

import com.badlogic.gdx.physics.box2d.Body;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;

import java.util.Random;

import constant.FORCE;
import constant.Gift;
import constant.SPEED;
import resourceManagement.ResourceManager;
import resourceManagement.RewardManager;

/**
 * Created by gja10 on 25/04/2016.
 */
public class Bonus extends AnimatedSprite implements CollidableEntity {
    private boolean isAnimated=false;
    private Body body;
    public static final String TYPE = "Reward";
    private Gift bonusType;
    private float WIDTH;
    private final int OFFSET = 20;
    private SPEED speed;
    private FORCE force;
    private Text bonusText;
    private Random random;

    public Bonus(float pX, float pY, Gift gift,
                ITiledTextureRegion pTiledTextureRegion,
                VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
        random = new Random();
        WIDTH  = ResourceManager.getInstance().camera.getWidth()-60;
        speed = SPEED.MEDIUM;
        bonusType = gift;
        force = FORCE.PUSH;
        registerEntityModifier(startSequenceModifier);
    }
    public void swimLeft(){
        applyForce(force.getVal(),0);
        startAnim(speed);
    }

    public void swimRight(){
        applyForce(-force.getVal(),0);
        startAnim(speed);
    }

   /* protected void onManagedUpdate(float pSecondsElapsed){
        super.onManagedUpdate(pSecondsElapsed);
        if(this.getX() > WIDTH){swimRight();}
        if(this.getX() < 0){swimLeft();}
    }*/

    public void initGraphic(){
        startSequenceModifier.reset();
    }

    public void setSpeed(SPEED s) {
        speed = s;
    }

    public void setForce(FORCE f) {
        force = f;
    }

    @Override
    public void runCollisionRoutine(Object o) {
        ResourceManager.getInstance().gHud.adjustTimer(bonusType.getVal());
        this.setAlpha(0.2f);
        registerEntityModifier(finalSequenceModifier);
        RewardManager.adjustRewardCount();

    }

    public void rise() {
        if (this.getX() < WIDTH / 2) {
            setForce(FORCE.PULL);
            applyForce(force.getVal(), force.getVal());
            startAnim(speed);
        }
        else{
            setForce(FORCE.PUSH);
            applyForce(force.getVal(),-force.getVal());
            startAnim(speed);
        }
    }

    public void startAnim(SPEED speed){
        this.animate(speed.getVal());
        isAnimated = true;
    }

    public void stopAnim(){
        this.stopAnimation();
        isAnimated = false;
    }

    public void toggleAnim(SPEED speed){
        if(isAnimated){
            stopAnim();
        }
        else{
            startAnim(speed);
        }
    }

    public boolean isAnimated(){
        return isAnimated;
    }

    public void applyForce(float x, float y){body.setLinearVelocity( x, y);}


    @Override
    public void setBody(Body body) {this.body = body;}

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public void rewardToScreen(){
        final TextOptions textOptions = new TextOptions();
        textOptions.setHorizontalAlign(HorizontalAlign.RIGHT);
        bonusText = new Text(this.getX()+OFFSET,this.getY()+OFFSET,
                ResourceManager.getInstance().rewardFont,
                bonusType.toString(),
                bonusType.toString().length(),
                textOptions,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        bonusText.setColor(0f, 1f, .075f, 1f);
        this.getParent().attachChild(bonusText);

    }

    public Gift getRewardType(){return bonusType;}

    ScaleModifier introScale = new ScaleModifier(3,0.1f,1.1f);

    AlphaModifier fadeOut = new AlphaModifier(1,1,0);

    RotationModifier initialiseModifier = new RotationModifier(1, 0, 360)
    {
        @Override
        protected void onModifierStarted(IEntity pItem) {
            super.onModifierStarted(pItem);
            startAnim(SPEED.MEDIUM);
        }

        @Override
        protected void onModifierFinished(IEntity pItem)
        {
            super.onModifierFinished(pItem);
            stopAnim();
        }
    };

    DelayModifier endOnDelayModifier = new DelayModifier(1){
        @Override
        protected void onModifierStarted(IEntity pItem)
        {
            super.onModifierStarted(pItem);
            rewardToScreen();
            rise();
        }

        @Override
        protected void onModifierFinished(IEntity pItem) {
            super.onModifierFinished(pItem);
            bonusText.registerEntityModifier(fadeOut);
        }
    };

    SequenceEntityModifier startSequenceModifier = new SequenceEntityModifier(
            initialiseModifier);
    SequenceEntityModifier finalSequenceModifier = new SequenceEntityModifier(
            endOnDelayModifier,fadeOut);

   }