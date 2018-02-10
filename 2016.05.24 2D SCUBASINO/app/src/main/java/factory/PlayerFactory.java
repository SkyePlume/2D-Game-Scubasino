package factory;

/**
 * Created by gerard on 19/02/2016.
 */

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import entity.Player;
import resourceManagement.ResourceManager;

public class PlayerFactory {
    private static PlayerFactory INSTANCE = new PlayerFactory();
    private ResourceManager rMan = ResourceManager.getInstance();
    private PhysicsWorld physicsWorld;
    public static final FixtureDef PLAYER_FIXTURE = PhysicsFactory.createFixtureDef(1f, 0f, 1f, false);

    private PlayerFactory() {}

    public static PlayerFactory getInstance() {
        return INSTANCE;
    }


    public Player createPlayer(float x, float y) {
        Player player = new Player(x, y, rMan.playerTextureRegion, rMan.vbom);
        player.setZIndex(2);

        Body playerBody = PhysicsFactory.createBoxBody(rMan.physicsWorld, player,
                BodyType.DynamicBody, PLAYER_FIXTURE);
        playerBody.setLinearDamping(1f);
        playerBody.setFixedRotation(true);
        playerBody.setUserData(player);
        rMan.physicsWorld.registerPhysicsConnector(new PhysicsConnector(player, playerBody));
        player.setBody(playerBody);
        return player;
    }
}