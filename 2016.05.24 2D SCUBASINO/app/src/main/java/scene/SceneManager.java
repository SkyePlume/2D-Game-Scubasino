package scene;

import org.andengine.engine.Engine;
import org.andengine.util.color.Color;

import constant.SceneType;
import resourceManagement.ResourceManager;

public class SceneManager
{
    //---------------------------------------------
    // SCENES
    //---------------------------------------------
    
    private AbstractScene splashScene=null;
    private AbstractScene gameScene=null;
    private AbstractScene scoreScene=null;
    private AbstractScene levels;
   
    
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static SceneManager INSTANCE = new SceneManager();
    
    private SceneType currentSceneType;
    private AbstractScene currentScene;
    private Engine engine;
    
      
    //---------------------------------------------
    // Setup
    //---------------------------------------------
    private SceneManager(){
        levels = new LevelScene();
    		currentSceneType = SceneType.SCENE_SPLASH;
        engine = ResourceManager.getInstance().engine;
    }
    
    
    public void update(AbstractScene splash,AbstractScene game,AbstractScene score){
       	splashScene = splash;
        gameScene = game;
        scoreScene = score;
    }
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    public void setScene(AbstractScene scene)
    {
        //will need to add code to remove current scene and resources if loaded.
        if(currentScene != null){
                currentScene.disposeScene();
                currentScene.detachChildren();
                currentScene.clearEntityModifiers();
                currentScene.clearTouchAreas();
                currentScene.clearUpdateHandlers();
        }
        currentScene = scene;
        currentScene.populate();
        engine.setScene(currentScene);
        currentSceneType = scene.getSceneType();
    }
    
    public void setScene(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_SCORE:
                setScene(scoreScene);
                break;
            case SCENE_LEVEL:
                setScene(levels);
                break;
            case SCENE_GAME:
                setScene(gameScene);
                break;
            default:
                break;
        }
    }

    public void loadLevel(int levelNumber){
          switch(levelNumber){
            case 1 : update(new SplashScene("Enter the Casino", Color.BLUE),
                        new GameScene(),
                        new ScoreScene(Color.BLUE));
                    break;
            case 2 : update(new SplashScene("Find LeChifre", Color.PINK),
                        new GameScene(),
                        new ScoreScene(Color.PINK));
                    break;
        }

       setScene(SceneType.SCENE_SPLASH);
    }

    public synchronized static SceneManager getInstance(){
		if(INSTANCE == null){INSTANCE = new SceneManager();}
		return INSTANCE;
	}

    public SceneType getCurrentSceneType() {return currentSceneType;}

    public AbstractScene getCurrentScene() {return currentScene;}

}