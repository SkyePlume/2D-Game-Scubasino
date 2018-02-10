package factory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import constant.FORCE;
import entity.Bonus;
import entity.Mine;
import resourceManagement.ResourceManager;

/**
 * Created by js884 on 13/05/2016.
 */
public class MineFactory {
    private static MineFactory INSTANCE = new MineFactory();
    private ResourceManager rMan = ResourceManager.getInstance();
    public static final FixtureDef BONUS_FIXTURE = PhysicsFactory.createFixtureDef(0f,0f,0f,false);

    private MineFactory() {}

    public static MineFactory getInstance() {return INSTANCE;}

    public Mine createMine(float x, float y){
        Mine mine = new Mine(x, y, rMan.mineTextureRegion, rMan.vbom);

        Body mineBody = PhysicsFactory.createBoxBody(rMan.physicsWorld, mine, BodyDef.BodyType.KinematicBody, BONUS_FIXTURE);
        mineBody.setUserData(mine);
        mineBody.setLinearDamping(1f);
        mineBody.setFixedRotation(true);
        rMan.physicsWorld.registerPhysicsConnector(new PhysicsConnector(mine,mineBody));
        mine.setBody(mineBody);
        return mine;
    }

    public Mine createMine (float x, float y, FORCE f){
        Mine mine = createMine(x,y);
        mine.setForce(f);
        if(x>rMan.camera.getWidth()/2){
            mine.swimLeft();
        }else{
            mine.swimRight();
        }
        return mine;
    }
}
