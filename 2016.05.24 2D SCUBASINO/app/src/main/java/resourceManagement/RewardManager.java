package resourceManagement;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;

import entity.Bonus;
import factory.BonusFactory;

/**
 * Created by gerard on 27/04/2016.
 */
public class RewardManager implements IUpdateHandler {
    private float endTimer;
    private final float REFRESHTIME=7f;
    final int MAXREWARDS = 15;
    private Scene currentScene;
    private static int rewardCount;
    private int count =0;
    private ResourceManager rMan;

    public RewardManager(Scene scene) {
        currentScene = scene;
        endTimer = REFRESHTIME;
        rMan = ResourceManager.getInstance();
        rewardCount=0;
    }
        @Override
        public void onUpdate(float pSecondsElapsed) {
            endTimer -= pSecondsElapsed;
            if (endTimer <= 0 && rewardCount<MAXREWARDS) {
                currentScene.attachChild(BonusFactory.getInstance().createBonus());
                endTimer = REFRESHTIME;
                rewardCount++;
            }
        }

        @Override
        public void reset() {

        }

    public static void adjustRewardCount(){
        rewardCount--;
    }
}