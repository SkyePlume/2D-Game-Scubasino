package scene;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import constant.SceneType;


public class SplashScene extends AbstractScene implements IOnSceneTouchListener{
    private String splashTxt;
    private Color colour;

	public SplashScene(String txt,Color colour) {
		super(SceneType.SCENE_SPLASH);
		this.splashTxt = txt;
		this.colour = colour;
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
			SceneManager.getInstance().setScene(SceneType.SCENE_GAME);
			return true;
		}
		return false;
	}

	@Override
	public void populate() {
		setBackground(new Background(this.colour));
		setOnSceneTouchListener(this);
		attachChild(new Text(100, 340, res.font, splashTxt, vbom));
		attachChild(new Text(75, 540, res.font, "Touch screen to start", vbom));
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
		return SceneType.SCENE_SPLASH;
	}
	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
	}
}
