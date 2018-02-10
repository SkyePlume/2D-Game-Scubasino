package scene;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import constant.CameraDimension;
import constant.SceneType;
import resourceManagement.ResourceManager;

/**
 * Created by gerard on 03/05/2016.
 */
public class ScoreScene extends AbstractScene implements IOnSceneTouchListener {
    private Color colour;
    private ResourceManager rMan;
    private static String scoreTxt = "Empty";

    public ScoreScene(Color colour) {
        super(SceneType.SCENE_SCORE);
        this.colour = colour;
        this.rMan = ResourceManager.getInstance();
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
            SceneManager.getInstance().setScene(SceneType.SCENE_LEVEL);
            return true;
        }
        return false;
    }

    @Override
    public void populate() {
        setBackground(new Background(colour));
        setOnSceneTouchListener(this);
        attachChild(
                setupTxt(
                        CameraDimension.WIDTH.getVal()/6,
                        CameraDimension.HEIGHT.getVal()/2));
    }

    public static void setScore(String score){
        scoreTxt = score;
    }

    public Text setupTxt(float x, float y){
        String msg = "Goodies in the goody bag\n"+scoreTxt+"\n\nTouch screen to End";
        final TextOptions textOptions = new TextOptions();
        textOptions.setHorizontalAlign(HorizontalAlign.RIGHT);
        Text text = new Text(x,y,
                rMan.font,
                msg,
                msg.length(),
                textOptions,
                rMan.engine.getVertexBufferObjectManager());
        text.setColor(0.5f, 0.5f, 1f, 1f);
        return text;
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
        return SceneType.SCENE_SCORE;
    }
    @Override
    public void disposeScene() {
        // TODO Auto-generated method stub
    }
}