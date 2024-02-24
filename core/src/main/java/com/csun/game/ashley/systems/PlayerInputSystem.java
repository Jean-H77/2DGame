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
import com.csun.game.ai.paths.PathFinder;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.ClickableComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;
import com.csun.game.attributes.Attributes;
import com.csun.game.map.GameMap;
import com.csun.game.models.Direction;
import com.csun.game.models.LightType;
import com.csun.game.player.Player;
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

        if(handleHotKeyInput());
    }

    private boolean handleHotKeyInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            player.save()
                .thenRun(() -> Gdx.app.log("Saved", "Player saved"));
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            player.toggleLight(LightType.CONE_LIGHT, 200);
        }
        return false;
    }

    public Vector2 getWorldPositionFromScreen(Vector3 v){
        Vector3 vc = player.getCamera().unproject(new Vector3(v.x, v.y, v.z));
        return new Vector2(vc.x, vc.y);
    }

    private boolean handleEntityClick(Entity entity) {
        ClickableComponent component = Mappers.clickables.get(entity);
        if(component != null && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 mouseAtWorld = getWorldPositionFromScreen(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (mouseAtWorld.x >= component.bounds.x - component.bounds.width && mouseAtWorld.x <= component.bounds.x + component.bounds.width &&
                mouseAtWorld.y >= component.bounds.y - component.bounds.height && mouseAtWorld.y <= component.bounds.y + component.bounds.height) {

                String identification = component.identification;
                Gdx.app.log("Clicking ", identification);
                return true;
            }
        }
        return false;
    }

    private boolean handleMovementInput(MovementComponent mc) {
        if(mc.disabled) return false;

        GameMap.INSTANCE.getLayer(1).ifPresent(tiledMapTileLayer -> {
            TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell((int) player.getTilePosX(), (int) player.getTilePosY());
            if(cell != null && cell.getTile().getProperties().containsKey("nm_door")) {
                mc.disabled = true;
                player.moveToMap((String) cell.getTile().getProperties().get("nm_door"));
            }

            /*if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                Vector2 mouse = getWorldPositionFromScreen(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                int tileX = (int) (mouse.x / TILE_SIZE);
                int tileY = (int) (mouse.y / TILE_SIZE);
                cell = tiledMapTileLayer.getCell(tileX, tileY);
                if(cell != null && cell.getTile().getProperties().containsKey("blocked")) {
                    Gdx.app.log("Collision", "Blocked [" + tileX + ", " + tileY + "]");
                } else {
                    List<Vector2> path = PathFinder.findPath(new Vector2(player.getTilePosX(), player.getTilePosY()), new Vector2(tileX, tileY), GameMap.INSTANCE);
                    if (!path.isEmpty()) {
                        path.remove(path.size() - 1);
                        path.add(new Vector2((mouse.x / TILE_SIZE), (mouse.y / TILE_SIZE)));
                    }
                    mc.addToMovementQueue(path);
                    GameMap.INSTANCE.vect = path;
                }
            }*/
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
