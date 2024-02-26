package com.csun.game.ashley.systems;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.csun.game.UI.BaseUI;
import com.csun.game.UI.UIManager;
import com.csun.game.ai.paths.PathFinder;
import com.csun.game.ai.paths.PathResult;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.ClickableComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;
import com.csun.game.attributes.Attributes;
import com.csun.game.dialogue.TestDialogue;
import com.csun.game.map.GameMap;
import com.csun.game.models.Direction;
import com.csun.game.models.LightType;
import com.csun.game.player.Player;
import com.csun.game.screens.GameScreen;
import com.google.inject.Inject;

import java.util.List;

import static com.csun.game.GameConstants.TILE_SIZE;

public class PlayerInputSystem extends IteratingSystem {

    private final Player player;

    @Inject
    public PlayerInputSystem(Player player) {
        super(Family.all(PlayerComponent.class).get());
        this.player = player;
    }

    @Override
    public ImmutableArray<Entity> getEntities() {
        return getEngine().getEntitiesFor(Family.all(ClickableComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent mc = Mappers.movements.get(entity);
        player.getAttributes().set(Attributes.WORLD_MOUSE_POSITION, getWorldPositionFromScreen(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));

        for(int i = 0; i < getEntities().size(); i++) {
            if(handleEntityClick(getEntities().get(i))) {
                return;
            }
        }

        if(handleMovementInput(mc)) {
            if(player.getAttributes().hasKey(Attributes.PLAYER_LIGHT)) {
                Light light = player.getAttributes().get(Attributes.PLAYER_LIGHT);
                if(light instanceof PointLight pointLight) {
                    pointLight.setPosition(mc.pos.x, mc.pos.y);
                } else if(light instanceof ConeLight coneLight) {
                    coneLight.setPosition(mc.pos.x, mc.pos.y);
                    coneLight.setDirection(mc.dir.getDegree());
                }
            }
        }

        if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) return;

        if(handleHotKeyInput()) {
            return;
        }
    }

    private boolean handleHotKeyInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            player.save()
                .thenRun(() -> Gdx.app.log("Saved", "Player saved"));
            return true;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            player.enableLight(LightType.CONE_LIGHT, 200);
            return true;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player.startDialogue(TestDialogue.get());
            return true;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            UIManager.openUI(player, "SettingsUI");
            return true;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            UIManager.removeUI(player, "SettingsUI");
            return true;
        }
        return false;
    }

    public Vector2 getWorldPositionFromScreen(Vector3 v){
        Vector3 vc = player.getCamera().unproject(new Vector3(v.x, v.y, v.z));
        return new Vector2(vc.x, vc.y);
    }

    private boolean handleEntityClick(Entity entity) {
        ClickableComponent component = Mappers.clickables.get(entity);
        if(component != null && player.getAttributes().hasKey(Attributes.WORLD_MOUSE_POSITION)) {
            Vector2 mouseAtWorld = player.getAttributes().get(Attributes.WORLD_MOUSE_POSITION);
            if (mouseAtWorld.x >= component.bounds.x - component.bounds.width && mouseAtWorld.x <= component.bounds.x + component.bounds.width &&
                mouseAtWorld.y >= component.bounds.y - component.bounds.height && mouseAtWorld.y <= component.bounds.y + component.bounds.height) {

                String identification = component.identification;
                player.getAttributes().set(Attributes.NPC_HOVER, identification);

                player.getAttributes().get(Attributes.ACTION_LISTENERS)
                    .forEach(actionListener -> actionListener
                        .hoverOverNpc(player, identification, mouseAtWorld.x, mouseAtWorld.y));

                if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    player.getAttributes().get(Attributes.ACTION_LISTENERS)
                        .forEach(actionListener -> actionListener
                                .clickNpc(player, identification, mouseAtWorld.x, mouseAtWorld.y));

                    player.getAttributes().set(Attributes.LAST_NPC_CLICKED, identification);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean handleMovementInput(MovementComponent mc) {
        if(mc.disabled) return false;

        if(player.getAttributes().get(Attributes.ALL_OPENED_UI)
            .stream()
            .anyMatch(baseUi -> !baseUi.isWalkable())) {
            return false;
        }

        GameMap.INSTANCE.getLayer(1).ifPresent(tiledMapTileLayer -> {
            TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell((int) player.getTilePosX(), (int) player.getTilePosY());
            if(cell != null && cell.getTile().getProperties().containsKey("nm_door")) {
                mc.disabled = true;
                player.moveToMap((String) cell.getTile().getProperties().get("nm_door"));
            }

            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                Vector2 mouse = player.getAttributes().get(Attributes.WORLD_MOUSE_POSITION);
                int tileX = (int) (mouse.x / TILE_SIZE);
                int tileY = (int) (mouse.y / TILE_SIZE);
                cell = tiledMapTileLayer.getCell(tileX, tileY);
                /*if(cell != null && cell.getTile().getProperties().containsKey("blocked")) {
                    Gdx.app.log("Collision", "Blocked [" + tileX + ", " + tileY + "]");
                } else {*/
                PathResult pathResult = PathFinder.findPath(new Vector2(player.getTilePosX(), player.getTilePosY()), new Vector2(tileX, tileY), GameMap.INSTANCE);

                if (!pathResult.path().isEmpty()) {
                    pathResult.path().remove(pathResult.path().size() - 1);
                    pathResult.path().add(new Vector2((mouse.x / TILE_SIZE), (mouse.y / TILE_SIZE)));
                    for(Vector2 vect : pathResult.path()) {
                        Gdx.app.log("Path", "Pathing: " + vect);
                    }
                }
                    //mc.addToMovementQueue(path);
                    GameMap.INSTANCE.pathResult = pathResult;
                }
            //}
        });

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) && !(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))) {
            if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                mc.move(Direction.NE);
            } else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                mc.move(Direction.NW);
            } else {
                mc.move(Direction.N);
            }
            return true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN) && !(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))) {
            if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                mc.move(Direction.SE);
            } else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                mc.move(Direction.SW);
            } else {
                mc.move(Direction.S);
            }
            return true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT) && !(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
            mc.move(Direction.W);
            return true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
            mc.move(Direction.E);
            return true;
        }
        return false;
    }
}
