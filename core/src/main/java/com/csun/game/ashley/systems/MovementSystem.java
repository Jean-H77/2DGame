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
        this.collisionLayer = tiledMapTileLayers[0];
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
        //save old position
        /*float oldX = getX(), oldY = getY(), tileWidth = collisionLayer.getTileHeight(),tileHeight = collisionLayer.getTileHeight();
        boolean collisionX = false, collisionY = false;
        //move on x
        setX(getX()+velocity.x * delta);

        if(velocity.x<0){
            //top left
            collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey("blocked");
            //top middle
            if (!collisionX)
                collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()/2) / tileHeight)).getTile().getProperties().containsKey("blocked");
            //bottom left
            if (!collisionX)
                collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties().containsKey("blocked");

        } else if(velocity.x > 0){
            //top right
            collisionX = collisionLayer.getCell((int) (getX() + getWidth())/tileWidth, (int) (getY() + getHeight)).getTile().getProperties().containsKey("blocked");
            //middle right
            if(!collisionX)
                collisionX = collisionLayer.getCell((int) ((getX() +getWidth()) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("blocked");
            //bottom right
            if(!collisionX)
                collisionX = collisionLayer.getCell((int) ((getX() +getWidth()) / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties().containsKey("blocked");
        }
        //react to x collision
        if(collisionX){
            setX(oldX);
            velocity.x = 0;
        }
        //move on y
        setY(getY()+velocity.y * delta);
        if(velocity.y <0){
            //bottom left
            collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties().containsKey("blocked");
            //bottom middle
            if(!collisionY)
                collisionY = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties().containsKey("blocked");
            //bottom right
            if(!collisionY)
                collisionY = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties().containsKey("blocked");
        } else if(velocity.y > 0){
            //top left
            collisionY = collisionLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey("blocked");
            //top middle
            if(!collisionY)
                collisionY = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey("blocked");
            //top right
            if(!collisionY)
                collisionY = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey("blocked");

        }
        //react to y collision
        if(collisionY){
           setY(oldY);
           velocity.y = 0;
        }*/

        TiledMapTileLayer.Cell cell;
        if((cell = collisionLayer.getCell((int) newX, (int) newY)) != null && cell.getTile().getProperties().containsKey("blocked")) {
            Gdx.app.log("Collision", "Blocked");
            return;
        }

        movement.pos.x  = newX;
        movement.pos.y = newY;

        Gdx.app.log("Position", "X: " + movement.pos.x + " Y: " + movement.pos.y);
    }
}
