package scene;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import constant.SceneType;
import resourceManagement.ResourceManager;

/**
 * Created by gerard on 19/08/2015.
 */
public abstract class AbstractScene extends Scene {
    protected ResourceManager res = ResourceManager.getInstance();
    protected Engine engine = res.engine;
    protected VertexBufferObjectManager vbom = res.vbom;
    protected Camera camera = res.camera;
    protected SceneType sceneType;

    public AbstractScene(SceneType st){sceneType=st;}

    public SceneType getSceneType(){return sceneType;}

    public abstract void populate();
    public void destroy() {}
    public void onBackKeyPressed() {}
    public abstract void onPause();
    public abstract void onResume();
    public abstract void disposeScene();


}