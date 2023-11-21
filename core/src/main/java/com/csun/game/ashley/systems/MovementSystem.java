package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ashley.components.MovementComponent;
import com.google.inject.Inject;


public class MovementSystem extends IteratingSystem {
    private static final int TILE_SIZE = 32;

    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    private final TiledMapTileLayer collisionLayer;

    @Inject
    public MovementSystem(TiledMapTileLayer[] tiledMapTileLayers) {
        super(Family.all(MovementComponent.class).get());
        this.collisionLayer = tiledMapTileLayers[1];
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = mm.get(entity);
        if(movement.state.equals(MovementComponent.MovementState.IDLE)) return;

        float velocity = movement.velocity * deltaTime;
        float moveX = 0f;
        float moveY = 0f;

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
        Vector2 dest = new Vector2(moveX, moveY).nor();

        float newX = movement.pos.x + dest.x;
        float newY = movement.pos.y + dest.y;

        TiledMapTileLayer.Cell cell;
        if((cell = collisionLayer.getCell((int) (newX/TILE_SIZE), (int) (newY/TILE_SIZE))) != null && cell.getTile().getProperties().containsKey("blocked")) {
            Gdx.app.log("Collision", "Blocked");
            return;
        }

        movement.pos.x = newX;
        movement.pos.y = newY;

        Gdx.app.log("Position", "X: " + (int)(movement.pos.x/TILE_SIZE) + " Y: " + (int)(movement.pos.y/TILE_SIZE));
    }
}
