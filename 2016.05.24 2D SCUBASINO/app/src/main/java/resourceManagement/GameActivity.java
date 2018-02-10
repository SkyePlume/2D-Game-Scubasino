package resourceManagement;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

import constant.CameraDimension;
import display.FollowCamera;
import scene.GameScene;
import scene.SceneManager;

/**
 * Created by gerard on 19/08/2015.
 */
public class GameActivity extends BaseGameActivity {
    public static final int CAMERA_WIDTH = 480;
    public static final int CAMERA_HEIGHT = 800;
    public static final String TEST_STRING = "0123456789";
    private GameScene gScene;
    private FollowCamera camera;
    private Text mText;
    ResourceManager rMan;


    @Override
    public Engine onCreateEngine(final EngineOptions pEngineOptions){
        return new FixedStepEngine(pEngineOptions, 60);
    }

    /**
     * This is where the music is run inside of the app, it starts upon the game resuming
     */
    public synchronized void onResumeGame() {
        if(ResourceManager.getInstance().mTrack != null && !ResourceManager.getInstance().mTrack.isPlaying()){
            ResourceManager.getInstance().mTrack.play();
        }
        super.onResumeGame();
    }

    /**
     * This is where the music is paused inside of the app, it stops upon the game pausing
     */
    public synchronized void onPauseGame() {
        if(ResourceManager.getInstance().mTrack != null && ResourceManager.getInstance().mTrack.isPlaying()){
            ResourceManager.getInstance().mTrack.pause();
        }
        super.onPauseGame();
    }

    @Override
    public EngineOptions onCreateEngineOptions(){
        camera = new FollowCamera(0,0, CameraDimension.WIDTH.getVal(), CameraDimension.HEIGHT.getVal());
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new FillResolutionPolicy(),
                camera);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        engineOptions.getAudioOptions().setNeedsMusic(true);
        engineOptions.getAudioOptions().setNeedsSound(true);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        ResourceManager.getInstance().create(this, getEngine(), getEngine().getCamera(), getVertexBufferObjectManager());
        ResourceManager.getInstance().loadGameGraphics();
        ResourceManager.getInstance().loadFonts();
        ResourceManager.getInstance().loadSounds();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        SceneManager.getInstance().loadLevel(1);
        //gScene = new GameScene();
        pOnCreateSceneCallback.onCreateSceneFinished(SceneManager.getInstance().getCurrentScene());
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

}
