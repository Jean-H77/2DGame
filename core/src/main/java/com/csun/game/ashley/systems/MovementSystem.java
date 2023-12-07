package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.GameMap;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.models.MovementState;
import com.google.inject.Inject;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.csun.game.GameConstants.TILE_SIZE;


public class MovementSystem extends IteratingSystem {

    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private final GameMap gameMap;
    private final AtomicBoolean isBlocked = new AtomicBoolean();
    private final Vector2 dest = Vector2.Zero;

    @Inject
    public MovementSystem(GameMap gameMap) {
        super(Family.all(MovementComponent.class).get());
        this.gameMap = gameMap;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = mm.get(entity);
        if(movement.state.equals(MovementState.IDLE)) return;

        float velocity = movement.velocity * deltaTime;
        float moveX = 0f;
        float moveY = 0f;

        isBlocked.set(false);

        switch (movement.dir) {
            case N -> moveY += velocity;
            case S -> moveY -= velocity;
            case E -> moveX += velocity;
            case W -> moveX -= velocity;
            case NE -> {
                moveX += velocity;
                moveY += velocity;
            }
            case NW -> {
                moveX -= velocity;
                moveY += velocity;
            }
            case SE -> {
                moveX += velocity;
                moveY -= velocity;
            }
            case SW -> {
                moveX -= velocity;
                moveY -= velocity;
            }
        }

        dest.x = moveX;
        dest.y = moveY;
        dest.nor();

        float newX = movement.pos.x + dest.x;
        float newY = movement.pos.y + dest.y;

        Optional<TiledMapTileLayer> optionalLayer = gameMap.getLayer(1);
        optionalLayer.ifPresent(tiledMapTileLayer -> {
            TiledMapTileLayer.Cell cell;
            if((cell = tiledMapTileLayer.getCell((int) (newX/TILE_SIZE), (int) (newY/TILE_SIZE))) != null && cell.getTile().getProperties().containsKey("blocked")) {
                Gdx.app.log("Collision", "Blocked");
                isBlocked.set(true);
            }
        });

        if(isBlocked.get()) return;

        movement.pos.x = newX;
        movement.pos.y = newY;

        Gdx.app.log("Position", "X: " + (int)(movement.pos.x/TILE_SIZE) + " Y: " + (int)(movement.pos.y/TILE_SIZE));
    }
}
