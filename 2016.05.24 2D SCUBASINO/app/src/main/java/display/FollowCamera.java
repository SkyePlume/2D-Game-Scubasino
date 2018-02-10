package display;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.IEntity;

import resourceManagement.GameActivity;

/**
 * Created by gerard on 20/04/2016.
 */
public class FollowCamera extends SmoothCamera {

    private IEntity followEntity;
    private int offset = 150;

    public FollowCamera(float pX, float pY, float pWidth, float pHeight) {
        super(pX, pY, pWidth, pHeight, 3000f, 750f, 1f);
    }

    @Override
    public void setChaseEntity(IEntity pChaseEntity) {
        super.setChaseEntity(pChaseEntity);
        this.followEntity = pChaseEntity;
    }

    @Override
    public void updateChaseEntity() {
        if (followEntity != null) {
            if (followEntity.getY() > getCenterY()) {
                setCenter(getCenterX(), followEntity.getY()-offset);
            } else if (followEntity.getY() < getYMin() ) {
                setCenter(getCenterX(), followEntity.getY()- getHeight());
            }
        }
    }
}
