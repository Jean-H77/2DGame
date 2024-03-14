package com.csun.game.player;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.UI.impl.settings.Settings;
import com.csun.game.ashley.components.*;
import com.csun.game.attributes.Attributes;
import com.csun.game.attributes.AttributesMap;
import com.csun.game.dialogue.Dialogue;
import com.csun.game.map.GameMap;
import com.csun.game.misc.MutableInt;
import com.csun.game.models.Animations;
import com.csun.game.models.Direction;
import com.csun.game.models.LightType;
import com.csun.game.quest.Quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.csun.game.GameConstants.*;

public final class Player {

    private final AttributesMap attributes = new AttributesMap();
    private final TransformComponent transformComponent;
    private final TextureComponent textureComponent;
    private final CameraComponent cameraComponent;
    private final AnimationComponent animationComponent;
    private final CollisionComponent collisionComponent;
    private final GameMap gameMap;
    private final Map<String, Quest> quests;
    private final Entity entity;

    public Player(Entity entity, GameMap gameMap, Map<String, Quest> quests) {
        this.transformComponent = entity.getComponent(TransformComponent.class);
        this.textureComponent = entity.getComponent(TextureComponent.class);
        this.cameraComponent = entity.getComponent(CameraComponent.class);
        this.animationComponent = entity.getComponent(AnimationComponent.class);
        this.collisionComponent = entity.getComponent(CollisionComponent.class);
        this.gameMap = gameMap;
        this.quests = quests;
        this.entity = entity;
    }

    public float getTilePosX() {
        return transformComponent.pos.x / TILE_SIZE;
    }

    public float getTilePosY() {
        return transformComponent.pos.y / TILE_SIZE;
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

    public TransformComponent getMovementComponent() {
        return transformComponent;
    }

    public TransformComponent getTransformComponent() {
        return transformComponent;
    }

    public CollisionComponent getCollisionComponent() {
        return collisionComponent;
    }

    private void defaultAttributes() {
        attributes.set(Attributes.PLAYER_NAME, "un_named");
        attributes.set(Attributes.PLAYER_POSITION, transformComponent.pos);
        attributes.set(Attributes.PLAYER_CURRENT_MAP, "act1.tmx");
        attributes.set(Attributes.PLAYER_IS_LOGGED_IN, false);
        attributes.set(Attributes.PLAYER_SETTINGS, new Settings());
        attributes.set(Attributes.ALL_OPENED_UI, new ArrayList<>());
        attributes.set(Attributes.QUEST_STAGES, new HashMap<>());
        attributes.set(Attributes.ACTION_LISTENERS, List.copyOf(quests.values()));
    }

    public void moveToMap(String map) {
        attributes.set(Attributes.PLAYER_CURRENT_MAP, map);
        gameMap.enterMap(map, 1f);
        transformComponent.disabled = false;

        attributes.get(Attributes.ACTION_LISTENERS)
            .forEach(actionListener -> actionListener
                .enterMap(this, map));
    }

    public void moveTo(Vector2 pos, String map) {
        moveToMap(map);
        transformComponent.pos = pos;
    }

    public void moveTo(Vector2 pos) {
        transformComponent.pos = pos;
    }

    public void postLoad() {
        defaultAttributes();
        ((OrthographicCamera) getCamera()).setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        attributes.set(Attributes.PLAYER_IS_LOGGED_IN, true);
        animationComponent.set(Animations.SKELETON_IDLE_NORTH, 48, 48);
        moveToMap(attributes.get(Attributes.PLAYER_CURRENT_MAP));
    }

    public void enableLight(LightType lightType, int radius) {
        disableLight();
        Vector2 offset = calculatePlayerLightOffset();
        if(lightType == LightType.POINT_LIGHT) {
            attributes.set(Attributes.PLAYER_LIGHT, new PointLight(
                gameMap.getRayHandler(),
                1000,
                Color.WHITE,
                radius,
                transformComponent.pos.x,
                transformComponent.pos.y
            ));
        } else if(lightType == LightType.CONE_LIGHT) {
                attributes.set(Attributes.PLAYER_LIGHT, new ConeLight(
                    gameMap.getRayHandler(),
                    1000,
                    Color.WHITE,
                    radius,
                    transformComponent.pos.x,
                    transformComponent.pos.y,
                    transformComponent.dir.getDegree(),
                    60.0f
                ));
            }
    }

    public Vector2 calculatePlayerLightOffset() {
        Direction dir = transformComponent.dir;
        Vector2 vector2 = new Vector2();
        if(dir == Direction.E) {
            vector2.x = 48;
        } else if(dir == Direction.W) {
            vector2.x = -48;
        } else if(dir == Direction.N) {
            vector2.y = 48;
        } else if(dir == Direction.S){
             vector2.y = -48;
        } else if(dir == Direction.NE) {
            vector2.y = 48;
            vector2.x = 48;
        } else if(dir == Direction.SE) {
            vector2.y = -48;
            vector2.x = 48;
        } else if(dir == Direction.NW) {
            vector2.y = 48;
            vector2.x = -48;
        } else if(dir == Direction.SW) {
            vector2.y = -48;
            vector2.x = -48;
        }
        return vector2;
    }
    public MutableInt getQuestStage(String quest) {
        return attributes.get(Attributes.QUEST_STAGES).computeIfAbsent(quest, x -> new MutableInt(1));
    }

    public void disableLight() {
        if(!attributes.hasKey(Attributes.PLAYER_LIGHT)) {
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

    public void startDialogue(Dialogue dialogue) {
        if(attributes.hasKey(Attributes.CURRENT_DIALOGUE)) {
            attributes.get(Attributes.CURRENT_DIALOGUE).removeDialogue(this);
        }
        attributes.set(Attributes.CURRENT_DIALOGUE, dialogue);
        dialogue.setup(this);
    }

    public GameMap getGameMap() {
        return gameMap;
    }



    public AnimationComponent getAnimationComponent() {
        return animationComponent;
    }

    public Entity getEntity() {
        return entity;
    }
}
