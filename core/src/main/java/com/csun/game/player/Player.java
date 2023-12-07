package com.csun.game.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.csun.game.ashley.components.CameraComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.csun.game.attributes.Attributes;
import com.csun.game.attributes.AttributesMap;
import com.csun.game.models.Direction;
import com.csun.game.models.MovementState;

import static com.csun.game.GameConstants.TILE_SIZE;

public final class Player {

    private final AttributesMap attributes = new AttributesMap();

    private final MovementComponent movementComponent;
    private final TextureComponent textureComponent;
    private final CameraComponent cameraComponent;

    public Player(Entity entity) {
        this.movementComponent = entity.getComponent(MovementComponent.class);
        this.textureComponent = entity.getComponent(TextureComponent.class);
        this.cameraComponent = entity.getComponent(CameraComponent.class);

        defaultAttributes();

        load(); // for testing
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

    public TextureComponent getTextureComponent() {
        return textureComponent;
    }

    public CameraComponent getCameraComponent() {
        return cameraComponent;
    }

    public String getName() {
        return attributes.get(Attributes.PLAYER_NAME);
    }

    public int getTotalKills() {
        return attributes.get(Attributes.PLAYER_KILLS);
    }

    public AttributesMap getAttributes() {
        return attributes;
    }

    public MovementComponent getMovementComponent() {
        return movementComponent;
    }

    private void defaultAttributes() {
        attributes.set(Attributes.PLAYER_NAME, "un_named");
        attributes.set(Attributes.PLAYER_KILLS, 0);
        attributes.set(Attributes.PLAYER_POSITION, movementComponent.pos);
    }

    public void save() {
        new PlayerSave(this).save();
    }

    public void load() {
        new PlayerLoad(this).loadPlayerAttributes().join();
    }
}
