package resourceManagement;

import android.content.Context;
import android.graphics.Typeface;
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import java.io.IOException;

import display.GameHUD;
import entity.CollidableEntity;

/**
 * Created by gerard on 18/08/2015.
 */
public class ResourceManager {

    //singleton stuff
    private static final ResourceManager INSTANCE = new ResourceManager();
    public Sound rustleSound;
    public Sound thudSound;
    public Music mTrack;


    public static ResourceManager getInstance() {
        return INSTANCE;
    }

    private ResourceManager() {
    }
    //end singleton stuff

    //common objects
    public Engine engine;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    public Context context;
    //physics
    private Vector2 gravity;
    public PhysicsWorld physicsWorld;
    public GameHUD gHud;

    //font
    public Font font;
    public Font rewardFont;

    //game textures
    public ITiledTextureRegion playerTextureRegion;
    public ITiledTextureRegion mineTextureRegion;
    public ITiledTextureRegion giftTextureRegion;
    public TextureRegion backGroundTexture;
    public TextureRegion clockFace;
    public TextureRegion leftScape;
    public TextureRegion rightScape;



    private BuildableBitmapTextureAtlas gameTextureAtlas;


    public void create(Context context, Engine engine, Camera camera, VertexBufferObjectManager vbom) {
        this.engine = engine;
        this.camera = camera;
        this.vbom = vbom;
        this.context = context;
        //configure physics
        gravity = new Vector2(0,SensorManager.STANDARD_GRAVITY*0.5f);
        physicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.STANDARD_GRAVITY/2),true);
        physicsWorld.setContactListener(new ContactListener(){
            @Override
            public void beginContact(Contact contact) {
                final Fixture x1 = contact.getFixtureA();
                final Fixture x2 = contact.getFixtureB();
                ((CollidableEntity) x1.getBody().getUserData()).runCollisionRoutine(x2.getBody().getUserData());
                ((CollidableEntity) x2.getBody().getUserData()).runCollisionRoutine(x1.getBody().getUserData());
            }
            @Override
            public void endContact(Contact contact) {}
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
        FixtureDef PlayerFixtureDef = PhysicsFactory.createFixtureDef(20f, 0f, 0.5f);
        FixtureDef BonusFixtureDef = PhysicsFactory.createFixtureDef(0f, 0f, 0.5f);
    }


    public void loadGameGraphics() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        gameTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),2048, 2048, TextureOptions.BILINEAR);

        playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                gameTextureAtlas, context, "diver.png", 11, 2);

        mineTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                gameTextureAtlas, context, "MineSprite.png", 10, 1);

        giftTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                gameTextureAtlas, context, "plus.png", 6, 4);

        clockFace = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, context, "RouletteWheelSprite.png");

        leftScape = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, context, "left.png");

        rightScape = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, context, "right.png");

        backGroundTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, context, "background.png");

        /*sandScape = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, context, "sand.png");*/
        try {
            gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            gameTextureAtlas.load();

        } catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            throw new RuntimeException("Error while loading game textures", e);
        }
    }

    public synchronized void loadFonts(){
        FontFactory.setAssetBasePath("font/");
        // Create mFont object via FontFactory class
        font = FontFactory.create(engine.getFontManager(),
                engine.getTextureManager(), 256, 256,
                Typeface.create(Typeface.DEFAULT, Typeface.NORMAL),
                32f, true,
                Color.ABGR_PACKED_RED_CLEAR);
        font.load();

        rewardFont = FontFactory.create(engine.getFontManager(),
                engine.getTextureManager(), 256, 256,
                Typeface.create(Typeface.DEFAULT, Typeface.NORMAL),
                21f, true,
                Color.ABGR_PACKED_RED_CLEAR);
        rewardFont.load();
    }

    public void loadSounds() throws IOException {
        SoundFactory.setAssetBasePath("sfx/");
        MusicFactory.setAssetBasePath("mfx/");
        try {
            rustleSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(),
                    context, "Rustle.ogg");
            thudSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(),
                    context, "Thud.ogg");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mTrack = MusicFactory.createMusicFromAsset(engine.getMusicManager(),
                    context, "russia.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mTrack.setLooping(true);
        mTrack.setVolume(.25f, .25f);
    }
}
