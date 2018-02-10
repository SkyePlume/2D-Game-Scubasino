package display;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.HorizontalAlign;

import java.io.IOException;

import constant.SceneType;
import resourceManagement.ResourceManager;
import scene.SceneManager;

public class GameHUD {
	private HUD mHUD;
	private Sprite clockFace;
	private Text countingText;
	public static float EndingTimer;


	public GameHUD(float d) {
		EndingTimer = d;
		mHUD = new HUD();
		ResourceManager.getInstance().loadFonts();
		loadHUD();
	}

	public void loadHUD(){
		clockFace = new Sprite(0,0,
				ResourceManager.getInstance().clockFace,
				ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		setupHudTxt(43f,43f,"0123456789");
		setupEvent();
		mHUD.attachChild(clockFace);
		mHUD.attachChild(countingText);
		ResourceManager.getInstance().camera.setHUD(mHUD);
	}

	public void setupHudTxt(float x, float y, String textString){
		final TextOptions textOptions = new TextOptions();
		textOptions.setHorizontalAlign(HorizontalAlign.RIGHT);
		countingText = new Text(x,y,
				ResourceManager.getInstance().font,
				textString,
				textString.length(),
				textOptions,
				ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		countingText.setColor(0f, 0f, 0f);

	}

	public void setupEvent(){
		mHUD.setX(10);
		mHUD.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
			}

			@Override
			public void onUpdate(float pSecondsElapsed) {
				EndingTimer -= pSecondsElapsed;
				if (EndingTimer <= 0) {
					countingText.setText("00");
					mHUD.unregisterUpdateHandler(this);
					SceneManager.getInstance().setScene(SceneType.SCENE_SCORE);
				} else if (EndingTimer <= 9) {
					countingText.setText("0" + String.valueOf(Math.round(EndingTimer)));
				} else {
					countingText.setText(String.valueOf(Math.round(EndingTimer)));
				}
			}
		});
	}

	public HUD getHUD(){
		return mHUD;
	}


	public void removeHud(){
		mHUD.detachChild(clockFace);
		mHUD.detachChild(countingText);
	}

	public static void adjustTimer(int val) {
		EndingTimer += val;
	}
	public static float getTimeLeft(){
		return EndingTimer;
	}

}
