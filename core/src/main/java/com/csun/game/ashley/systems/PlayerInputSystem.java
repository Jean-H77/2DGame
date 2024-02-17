package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;
import com.csun.game.map.GameMap;
import com.csun.game.models.Direction;
import com.csun.game.player.Player;
import com.google.inject.Inject;

public class PlayerInputSystem extends IteratingSystem {

    private final ComponentMapper<MovementComponent> mp = ComponentMapper.getFor(MovementComponent.class);
    private final Player player;
    private final GameMap gameMap;

    @Inject
    public PlayerInputSystem(Player player, GameMap gameMap) {
        super(Family.all(PlayerComponent.class).get());
        this.player = player;
        this.gameMap = gameMap;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent mc = mp.get(entity);

        if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) return;
        if(handleMovementInput(mc)) return;
        if(handleHotKeyInput()) return;
    }

    private boolean handleHotKeyInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.Y)) {
            player.save()
                .thenRun(() -> Gdx.app.log("Saved", "Player saved"));
        }
        return false;
    }

    private boolean handleMovementInput(MovementComponent mc) {
        gameMap.getLayer(1).ifPresent(tiledMapTileLayer -> {
            TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell((int) player.getTilePosX(), (int) player.getTilePosY());
            if(cell != null) {
                if (cell.getTile().getProperties().containsKey("nm_door")) {
                    player.moveToMap((String) cell.getTile().getProperties().get("nm_door"));
                }
            }
        });

        if(mc.disabled) return false;

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
