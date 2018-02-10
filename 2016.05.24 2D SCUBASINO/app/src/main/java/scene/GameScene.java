package scene;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;

import constant.FORCE;
import constant.GameLength;
import constant.SceneType;
import display.GameHUD;
import entity.Bonus;
import entity.Mine;
import entity.Player;
import factory.BonusFactory;
import factory.MineFactory;
import factory.PlayerFactory;
import resourceManagement.ResourceManager;
import resourceManagement.RewardManager;

/**
 * Created by gerard on 19/08/2015.
 */
public class GameScene extends AbstractScene implements IAccelerationListener{
    ResourceManager rMan;
    Player player;
    private GameHUD hud;
    private GameLength duration = GameLength.LONG;
    public Mine mine;
    public Bonus bonus;

    float lastX = 0;

    public Body bottomWallBody;



    public GameScene(){
        super(SceneType.SCENE_GAME);
    }

    @Override
    public void populate() {

        sceneType = SceneType.SCENE_GAME;
        rMan = ResourceManager.getInstance();
        createBackground();
        createPlayer();
        createHUD();
        createMine(250, 150, FORCE.PUSH);
        rMan.camera.setChaseEntity(player);
       setOnSceneTouchListener(new IOnSceneTouchListener() {
           @Override
           public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
               if (pSceneTouchEvent.isActionDown() && player.getY() > 50) {
                   player.swim();
                   player.applyForce(0, -10);
                   return false;
               }
               return false;
           }
       });

        engine.enableAccelerationSensor(rMan.context, this);
        registerUpdateHandler(rMan.physicsWorld);
        registerUpdateHandler(new RewardManager(this));
    }

    private void createPlayer() {
        player = PlayerFactory.getInstance().createPlayer(240, -200);
        player.setScale(1f);
        attachChild(player);
    }

    private void createMine(int x, int y, FORCE f){
        mine = MineFactory.getInstance().createMine(x, y, f);
        attachChild(mine);
    }

    @Override
    public void onPause() {

    }
    @Override
    public void onResume() {
    }

    @Override
    public void disposeScene() {
        rMan.camera.reset();
        hud.removeHud();
        rMan.camera.setCenter(0f,0f);
    }

    private void createHUD() { hud = new GameHUD((float)duration.getVal());}

    private void createBackground() {
        Entity background = new Entity();
        Sprite backgroundSprite = new Sprite(120, 0, rMan.backGroundTexture.getWidth(),rMan.backGroundTexture.getHeight(),
                rMan.backGroundTexture,vbom);
        backgroundSprite.setScale(2f);
        attachChild(backgroundSprite);
        setBackground(new EntityBackground(0.92f, 0.92f, 0.92f,
                background));;
    }

    @Override
    public void onAccelerationAccuracyChanged(
            AccelerationData pAccelerationData) {}

    @Override
    public void onAccelerationChanged( final AccelerationData pAccelerationData) {
        if (Math.abs(pAccelerationData.getX() - lastX) > 0.75f){
            lastX = pAccelerationData.getX();
        }
        final Vector2 force =
                Vector2Pool.obtain(pAccelerationData.getX()*0.25f, 0);
        final Vector2 point = player.getBody().getWorldCenter();
        player.getBody().applyForce(force, point);
        player.getBody().applyLinearImpulse(force, point);
        Vector2Pool.recycle(force);
    }
}