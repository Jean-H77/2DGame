package com.csun.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.csun.game.ashley.components.CameraComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.TextureComponent;

public class Player {

    private final MovementComponent movementComponent;
    private final TextureComponent textureComponent;
    private final CameraComponent cameraComponent;

    public Player(Entity entity) {
        this.movementComponent = entity.getComponent(MovementComponent.class);
        this.textureComponent = entity.getComponent(TextureComponent.class);
        this.cameraComponent = entity.getComponent(CameraComponent.class);
    }

    public float getX() {
        return movementComponent.pos.x;
    }

    public float getY() {
        return movementComponent.pos.y;
    }

    public Camera getCamera() {
        return cameraComponent.camera();
    }


    public MovementComponent getMovementComponent() {
        return movementComponent;
    }

    public TextureComponent getTextureComponent() {
        return textureComponent;
    }

    public CameraComponent getCameraComponent() {
        return cameraComponent;
    }
}
