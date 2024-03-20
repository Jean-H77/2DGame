package com.csun.game.player;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.csun.game.ashley.components.CameraComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.csun.game.attributes.Attributes;
import com.csun.game.attributes.AttributesMap;
import com.csun.game.map.GameMap;
import com.csun.game.models.LightType;

import java.util.concurrent.CompletableFuture;

import static com.csun.game.GameConstants.*;

public final class Player {

    private final AttributesMap attributes = new AttributesMap();
    private final MovementComponent movementComponent;
    private final TextureComponent textureComponent;
    private final CameraComponent cameraComponent;

    public Player(Entity entity) {
        this.movementComponent = entity.getComponent(MovementComponent.class);
        this.textureComponent = entity.getComponent(TextureComponent.class);
        this.cameraComponent = entity.getComponent(CameraComponent.class);
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

    public String getName() {
        return attributes.get(Attributes.PLAYER_NAME);
    }

    public AttributesMap getAttributes() {
        return attributes;
    }

    public MovementComponent getMovementComponent() {
        return movementComponent;
    }

    private void defaultAttributes() {
        attributes.set(Attributes.PLAYER_NAME, "un_named");
        attributes.set(Attributes.PLAYER_POSITION, movementComponent.pos);
        attributes.set(Attributes.PLAYER_CURRENT_MAP, "dadroom.tmx");
        attributes.set(Attributes.PLAYER_IS_LOGGED_IN, false);
    }

    public void moveToMap(String map) {
        // here we can do something like
        // if player has this map unlocked then continue else return
        // if map get ambient light or darkness level < 1.0f
        // then we can check if player has lantern
        // if player has lantern then we can toggle the light
        // then we'll call gameMap.enterMap(map, maps darkness level)
        attributes.set(Attributes.PLAYER_CURRENT_MAP, map);
        GameMap.INSTANCE.enterMap(map, 1f);
        movementComponent.disabled = false;
    }

    public void login() {
        defaultAttributes();
        ((OrthographicCamera) getCamera()).setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        load().join();
        postLoad();
    }

    public void postLoad() {
        attributes.set(Attributes.PLAYER_IS_LOGGED_IN, true);
        moveToMap(attributes.get(Attributes.PLAYER_CURRENT_MAP));
    }

    public void toggleLight(LightType lightType, int radius) {
        if(!attributes.hasKey(Attributes.PLAYER_LIGHT)) {
            if(lightType == LightType.POINT_LIGHT) {
                attributes.set(Attributes.PLAYER_LIGHT, new PointLight(
                    GameMap.INSTANCE.getRayHandler(),
                    1000,
                    Color.WHITE,
                    radius,
                    movementComponent.pos.x,
                    movementComponent.pos.y
                ));
            } else if(lightType == LightType.CONE_LIGHT) {
                attributes.set(Attributes.PLAYER_LIGHT, new ConeLight(
                    GameMap.INSTANCE.getRayHandler(),
                    1000,
                    Color.WHITE,
                    radius,
                    movementComponent.pos.x,
                    movementComponent.pos.y,
                    movementComponent.dir.getDegree(),
                    60.0f
                ));
            }
            return;
        }
        attributes.get(Attributes.PLAYER_LIGHT).remove();
        attributes.remove(Attributes.PLAYER_LIGHT);
    }

    public CompletableFuture<Void> save() {
        return new PlayerSave(this).save();
    }

    public CompletableFuture<Void> load() {
        return new PlayerLoad(this).loadPlayerAttributes();
    }

    public CameraComponent getCameraComponent() {
        return cameraComponent;
    }
}
