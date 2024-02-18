package com.csun.game.player;

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

import java.util.concurrent.CompletableFuture;

import static com.csun.game.GameConstants.*;

public final class Player {

    private final AttributesMap attributes = new AttributesMap();
    private final MovementComponent movementComponent;
    private final TextureComponent textureComponent;
    private final CameraComponent cameraComponent;
    private final GameMap gameMap;

    public Player(Entity entity, GameMap gameMap) {
        this.movementComponent = entity.getComponent(MovementComponent.class);
        this.textureComponent = entity.getComponent(TextureComponent.class);
        this.cameraComponent = entity.getComponent(CameraComponent.class);
        this.gameMap = gameMap;

        login();
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
        attributes.set(Attributes.PLAYER_CURRENT_MAP, "tempmap.tmx");
        attributes.set(Attributes.PLAYER_IS_LOGGED_IN, false);
        moveToMap(attributes.get(Attributes.PLAYER_CURRENT_MAP));
    }

    public void moveToMap(String map) {
        attributes.set(Attributes.PLAYER_CURRENT_MAP, map);
        gameMap.enterMap(map, 1f);
    }

    public void login() {
        defaultAttributes();
        ((OrthographicCamera) getCamera()).setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        load().thenRun(this::postLoad);
    }

    public CompletableFuture<Void> postLoad() {
        return CompletableFuture.runAsync(() -> {
            attributes.set(Attributes.PLAYER_IS_LOGGED_IN, true);
            moveToMap(attributes.get(Attributes.PLAYER_CURRENT_MAP));
        });
    }

    public void toggleLight(int radius, float darkness) {
        if(!attributes.hasKey(Attributes.PLAYER_POINT_LIGHT)) {
            attributes.set(Attributes.PLAYER_POINT_LIGHT, new PointLight(gameMap.getRayHandler(),
                1000,
                Color.WHITE,
                radius,
                movementComponent.pos.x,
                movementComponent.pos.y
            ));
            gameMap.getRayHandler().setAmbientLight(darkness);
            return;
        }
        gameMap.getRayHandler().setAmbientLight(1f);
        attributes.get(Attributes.PLAYER_POINT_LIGHT).remove();
        attributes.remove(Attributes.PLAYER_POINT_LIGHT);
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

    public GameMap getGameMap() {
        return gameMap;
    }
}
