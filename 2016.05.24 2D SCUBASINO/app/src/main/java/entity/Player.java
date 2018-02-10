package entity;

import com.badlogic.gdx.physics.box2d.Body;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import constant.Gift;
import constant.SPEED;
import constant.SceneType;
import display.GameHUD;
import resourceManagement.PlayerGameData;
import resourceManagement.ResourceManager;
import scene.SceneManager;

/**
 * Created by gerard on 19/08/2015.
 */
public class Player extends AnimatedSprite implements CollidableEntity {
    boolean dead = false;
    private boolean isAnimated=false;
    private Body body;
    public PlayerGameData gameData;
    public static final String TYPE = "Player";
    private SPEED speed;
    private ResourceManager rMan;

    public Player(float pX, float pY,
                  ITiledTextureRegion pTiledTextureRegion,
                  VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
        speed = SPEED.SLOW;
        gameData = new PlayerGameData();
        rMan = ResourceManager.getInstance();
    }


    public void startAnim(SPEED speed){
        this.animate(75);
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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

  /*  public void turnLeft() {

    }

    public void turnRight() {

    }*/

    public void swim() { startAnim(SPEED.FAST); }



    public void die() {
        setDead(true);
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

            if (body.getLinearVelocity().y > 0) {
                swim();
            }
        if (body.getPosition().y > 23) {
            this.applyForce(0, -10);
        }
        if (body.getPosition().x > 15){
            this.applyForce(-10, 0);
        }
        if (body.getPosition().x < 0){
            this.applyForce(10, 0);
        }
        }

    public void applyForce(float x,float y){
        body.setLinearVelocity(x, y);
    }

    @Override
    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void runCollisionRoutine(Object o) {
        CollidableEntity ce = (CollidableEntity)o;

        if(ce.getType().equals("Mine")){
            GameHUD.adjustTimer(-10);
            if(GameHUD.getTimeLeft()<0){
                die();
            }
        }
        else if(ce.getType().equals("Reward")){
            entity.Bonus reward = (entity.Bonus)ce;
            switch(reward.getRewardType()){
                case MACHETE: gameData.addReward(Gift.MACHETE);
                    break;
                case BOMBDEFUSAL: gameData.addReward(Gift.BOMBDEFUSAL);
                    break;
                case WATERJET: gameData.addReward(Gift.WATERJET);
                    break;
                default : break;
            }
        }
        else if(ce.getType().equals("Platform") && dead){
            dead=false;
            SceneManager.getInstance().setScene(SceneType.SCENE_SCORE);
        }
    }

}