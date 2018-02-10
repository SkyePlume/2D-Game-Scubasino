package scene;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import constant.SceneType;

/**
 * Created by gerard on 04/05/2016.
 */
public class LevelScene extends AbstractScene implements IOnSceneTouchListener {
    private Color colour;
    private int level;

    public LevelScene() {
        super(SceneType.SCENE_LEVEL);
        this.colour = Color.CYAN;
        this.level = 2;
    }

    @Override
    public void onBackKeyPressed() {
        // TODO Auto-generated method stub
        return;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        // TODO Auto-generated method stub
        if(pSceneTouchEvent.isActionDown()){
            if(level < 3){
                SceneManager.getInstance().loadLevel(level);
                level++;
                return true;
            }
            else{System.exit(0);}
        }
        return false;
    }

    @Override
    public void populate() {
        setBackground(new Background(this.colour));
        setOnSceneTouchListener(this);
        attachChild(new Text(100, 340, res.font, "Level "+level, vbom));
        attachChild(new Text(50, 540, res.font, "Touch Level Screen to start", vbom));
    }

    @Override
    public void onPause() {
    }
    @Override
    public void onResume() {
    }
    @Override
    public SceneType getSceneType() {
        // TODO Auto-generated method stub
        return SceneType.SCENE_LEVEL;
    }
    @Override
    public void disposeScene() {
        // TODO Auto-generated method stub
    }
}