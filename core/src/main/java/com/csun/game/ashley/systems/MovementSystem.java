package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.map.GameMap;
import com.csun.game.models.Direction;
import com.csun.game.models.MovementState;
import com.google.inject.Inject;

import static com.csun.game.GameConstants.TILE_SIZE;


public class MovementSystem extends IteratingSystem {

    private boolean isBlocked;

    private final GameMap gameMap;

    @Inject
    public MovementSystem(GameMap gameMap) {
        super(Family.all(MovementComponent.class).get());
        this.gameMap = gameMap;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = Mappers.movements.get(entity);
        if(movement.state.equals(MovementState.IDLE)) return;
        if(movement.movementQueue.isEmpty()) return;

        isBlocked = false;
        Direction direction = movement.movementQueue.pop();
        movement.dir = direction;
        Vector2 step = calculateMovementStep(direction, movement.velocity);
        Vector2 dest = new Vector2(step.x, step.y).nor();
        dest.x += movement.pos.x;
        dest.y += movement.pos.y;

        gameMap.getLayer(1).ifPresent(tiledMapTileLayer -> {
            TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell((int) (dest.x /TILE_SIZE), (int) (dest.y/TILE_SIZE));
            if(cell != null && cell.getTile().getProperties().containsKey("blocked")) {
                Gdx.app.log("Collision", "Blocked ["+(int) (dest.x /TILE_SIZE) + ", " + (int) (dest.y/TILE_SIZE)+ "]");
                isBlocked = true;
            }
        });

        if(isBlocked) return;

        movement.pos = dest;

        if(movement.movementQueue.isEmpty()) {
            movement.velocity = 0.0f;
            movement.state = MovementState.IDLE;
        }
    }

    public static Direction getDirection(Vector2 newPos, Vector2 oldPos) {
        if (newPos.y > oldPos.y) {
            if (newPos.x > oldPos.x) {
                return Direction.NE;
            } else if (newPos.x < oldPos.x) {
                return Direction.NW;
            } else {
                return Direction.N;
            }
        } else if (newPos.y < oldPos.y) {
            if (newPos.x > oldPos.x) {
                return Direction.SE;
            } else if (newPos.x < oldPos.x) {
                return Direction.SW;
            } else {
                return Direction.S;
            }
        } else {
            if (newPos.x > oldPos.x) {
                return Direction.E;
            } else {
                return Direction.W;
            }
        }
    }

    private static Vector2 calculateMovementStep(Direction dir, float velocity) {
        Vector2 vect = new Vector2();

        switch (dir) {
            case N -> vect.y += velocity;
            case S -> vect.y -= velocity;
            case E -> vect.x += velocity;
            case W -> vect.x -= velocity;
            case NE -> {
                vect.x += velocity;
                vect.y  += velocity;
            }
            case NW -> {
                vect.x -= velocity;
                vect.y  += velocity;
            }
            case SE -> {
                vect.x += velocity;
                vect.y -= velocity;
            }
            case SW -> {
                vect.x -= velocity;
                vect.y -= velocity;
            }
        }
        return vect;
    }
}
