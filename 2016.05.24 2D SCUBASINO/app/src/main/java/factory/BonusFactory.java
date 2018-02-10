package factory;

/**
 * Created by gerard on 19/02/2016.
 */

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import java.util.Random;

import constant.Gift;
import entity.Bonus;
import resourceManagement.ResourceManager;

public class BonusFactory {
    private static BonusFactory INSTANCE = new BonusFactory();
    private ResourceManager rMan = ResourceManager.getInstance();
    public static final FixtureDef BONUS_FIXTURE = PhysicsFactory.createFixtureDef(0f, 0f, 0f, false);
    private Gift[] gifts = {Gift.BOMBDEFUSAL, Gift.BUBBLE, Gift.MACHETE, Gift.OXYGEN, Gift.WATERJET};
    private int WIDTH = (int)ResourceManager.getInstance().giftTextureRegion.getWidth();
    private int offset = WIDTH*3;
    private Random random;
    private static final int MAXDEPTH = 600;
    private static final int MINDEPTH = 60;


    private BonusFactory() {}

    public static BonusFactory getInstance() {
        return INSTANCE;
    }

    public Bonus createBonus() {
        random = new Random();
        offset *= -1f;
        float x = (float)random.nextInt((int)WIDTH)+offset;
        float y = (float)random.nextInt(MAXDEPTH)+MINDEPTH;


        Bonus bonus = new Bonus(x, y, gifts[random.nextInt(gifts.length)], rMan.giftTextureRegion, rMan.vbom);

        Body bonusBody = PhysicsFactory.createBoxBody(rMan.physicsWorld, bonus,
                BodyType.KinematicBody, BONUS_FIXTURE);
        bonusBody.setLinearDamping(1f);
        bonusBody.setFixedRotation(true);
        bonusBody.setUserData(bonus);
        rMan.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bonus,bonusBody));
        bonus.setBody(bonusBody);
        if(x>rMan.camera.getWidth()/2){
            bonus.swimLeft();
        }else{
            bonus.swimRight();
        }
        return bonus;
    }
}