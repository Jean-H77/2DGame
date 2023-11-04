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
            case W -> moveX-= velocity;
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
        //Gdx.app.log("Movement Magnitude", String.valueOf(Math.sqrt(Math.pow(dest.x, 2) + Math.pow(dest.y, 2))));

        float newX = movement.pos.x + dest.x;
        float newY = movement.pos.y + dest.y;

        //@todo collision checking here

        //TiledMapTileLayer.Cell cell;
       // if((cell = collisionLayer.getCell((int) newX, (int) newY)) != null && cell.getTile().getProperties().containsKey("blocked")) {
       //     Gdx.app.log("Collision", "Blocked");
       //     return;
        //}
        //edited it to see the tile number because it seemed that the tiles were blocked randomly
        for (int x = (int) movement.pos.x; x <= (int) newX; x++) {
            for (int y = (int) movement.pos.y; y <= (int) newY; y++) {
                TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
                Gdx.app.log("Collision", "Checking tile at (" + x + ", " + y + ")");
                if (cell != null) {
                    if((cell = collisionLayer.getCell((int) newX, (int) newY)) != null && cell.getTile().getProperties().containsKey("Blocked")) {
                        Gdx.app.log("Collision", "Blocked");
                        return;
                    }
                }
            }
        }
        movement.pos.x  = newX;
        movement.pos.y = newY;

        Gdx.app.log("Position", "X: " + movement.pos.x + " Y: " + movement.pos.y);
    }
}
