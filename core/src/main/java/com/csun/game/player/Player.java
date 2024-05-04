package com.csun.game.player;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
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
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.csun.game.GameConstants.*;

@Getter
@Setter
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
    private String previousMap;

    public Player(Entity entity, GameMap gameMap, Map<String, Quest> quests) {
        this.transformComponent = entity.getComponent(TransformComponent.class);
        this.textureComponent = entity.getComponent(TextureComponent.class);
        this.cameraComponent = entity.getComponent(CameraComponent.class);
        this.animationComponent = entity.getComponent(AnimationComponent.class);
        this.collisionComponent = entity.getComponent(CollisionComponent.class);
        this.gameMap = gameMap;
        this.quests = quests;
        this.entity = entity;

        defaultAttributes();
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

    public String getName() {
        return attributes.get(Attributes.PLAYER_NAME);
    }

    private void defaultAttributes() {
        attributes.set(Attributes.PLAYER_NAME, "un_named");
        attributes.set(Attributes.PLAYER_POSITION, transformComponent.pos);
        attributes.set(Attributes.PLAYER_CURRENT_MAP, "toddroom.tmx");
        attributes.set(Attributes.PLAYER_IS_LOGGED_IN, false);
        attributes.set(Attributes.PLAYER_IS_PAUSED, false);
        attributes.set(Attributes.PLAYER_SETTINGS, new Settings());
        attributes.set(Attributes.ALL_OPENED_UI, new ArrayList<>());
        attributes.set(Attributes.QUEST_STAGES, new HashMap<>());
        attributes.set(Attributes.ACTION_LISTENERS, List.copyOf(quests.values()));
    }
    public void updatePreviousMap(String currentMap) {
        this.previousMap = currentMap;
    }
    public void moveToMap(String map) {
        attributes.set(Attributes.PLAYER_CURRENT_MAP, map);
        gameMap.enterMap(map, 1f);
        transformComponent.disabled = false;

        // Switch statement to handle different maps
        switch (map) {
            case "circus.tmx":
                transformComponent.pos.set(68,231);
                break;
            case "bonedoor.tmx":
                transformComponent.pos.set(506,130);
                break;
            case "wellstart.tmx":
                if ("act1.tmx".equals(previousMap)) {
                    transformComponent.pos.set(442,740);
                } if ("wellpuzzle.tmx".equals(previousMap)) {
                transformComponent.pos.set(869, 514);
            } break;
            case "wellpuzzle.tmx":
                transformComponent.pos.set(474,969);
                break;
            case "doctoroffice.tmx":
                transformComponent.pos.set(504,36);
                break;
            case "basement.tmx":
                transformComponent.pos.set(146, 448);
                break;
                case "toddroom.tmx":
                transformComponent.pos.set(391, 105);
                break;
            case "dadroom.tmx":
                transformComponent.pos.set(478, 200);
                break;
                case "mainhouse.tmx":
                if ("act1.tmx".equals(previousMap)) {
                    transformComponent.pos.set(473, 60);
                } if ("dadroom.tmx".equals(previousMap)) {
                transformComponent.pos.set(600, 340);
            }if ("toddroom.tmx".equals(previousMap)) {
                transformComponent.pos.set(360, 311);
            }

                break;
            case "witchhouse.tmx":
                if ("act1.tmx".equals(previousMap)) {
                    transformComponent.pos.set(503, 49);
                } else {
                    transformComponent.pos.set(700, 550);
                }
                break;
            case "act2.tmx":
                if ("act1.tmx".equals(previousMap)) {
                    transformComponent.pos.set(349, 175);
                } else {
                    transformComponent.pos.set(700, 550);
                }
                break;
            case "act1.tmx":
                if ("witchhouse.tmx".equals(previousMap)) {
                    transformComponent.pos.set(156, 677);
                } if ("doctoroffice.tmx".equals(previousMap)) {
                    transformComponent.pos.set(1429, 487);
                } if ("mainhouse.tmx".equals(previousMap)) {
                transformComponent.pos.set(1721, 680);}
                if ("act2.tmx".equals(previousMap)) {
                    transformComponent.pos.set(949, 864);}
                if ("wellstart.tmx".equals(previousMap)) {
                transformComponent.pos.set(394, 787);}
                if ("bonedoor.tmx".equals(previousMap)) {
                    transformComponent.pos.set(1627, 250);}
                break;
            default:
                // Handle default case if map does not match any of the above cases
                // You may choose to log an error or handle it differently based on your requirements
                break;
        }

        attributes.get(Attributes.ACTION_LISTENERS)
            .forEach(actionListener -> actionListener.enterMap(this, map));

        updatePreviousMap(map);
    }


    public void moveTo(Vector2 pos, String map) {
        moveToMap(map);
        transformComponent.pos = pos;
    }

    public void moveTo(Vector2 pos) {
        transformComponent.pos = pos;
    }

    public void postLoad() {
        ((OrthographicCamera) getCamera()).setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        attributes.set(Attributes.PLAYER_IS_LOGGED_IN, true);
        collisionComponent.bounds = new Rectangle(transformComponent.pos.x, transformComponent.pos.y, 32, 32);
        animationComponent.set(Animations.TODD_IDLE_NORTH, 48, 48);
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

    public void startDialogue(Dialogue dialogue) {
        if(attributes.hasKey(Attributes.CURRENT_DIALOGUE)) {
            attributes.get(Attributes.CURRENT_DIALOGUE).removeDialogue(this);
        }
        attributes.set(Attributes.CURRENT_DIALOGUE, dialogue);
        dialogue.setup(this);
    }
}
