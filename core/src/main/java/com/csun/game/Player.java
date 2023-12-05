package com.csun.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.csun.game.ashley.components.CameraComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.csun.game.attributes.AttributeKey;
import com.csun.game.attributes.Attributes;
import com.csun.game.attributes.AttributesMap;
import com.csun.game.models.Direction;
import com.csun.game.models.MovementState;

import static com.csun.game.GameConstants.TILE_SIZE;

public class Player {

    private final AttributesMap attributes = new AttributesMap();

    private final MovementComponent movementComponent;
    private final TextureComponent textureComponent;
    private final CameraComponent cameraComponent;

    public Player(Entity entity) {
        this.movementComponent = entity.getComponent(MovementComponent.class);
        this.textureComponent = entity.getComponent(TextureComponent.class);
        this.cameraComponent = entity.getComponent(CameraComponent.class);
    }

    public float getTruePosX() {
        return movementComponent.pos.x;
    }

    public float getTruePosY() {
        return movementComponent.pos.y;
    }

    public float getTilePosX() {
        return movementComponent.pos.x / TILE_SIZE;
    }

    public float getTilePosY() {
        return movementComponent.pos.y / TILE_SIZE;
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

    public AttributesMap getAttributes() {
        return attributes;
    }

    public void move(Direction dir) {
        movementComponent.velocity = 1;
        movementComponent.state = MovementState.MOVING;
        movementComponent.dir = dir;
    }
}
