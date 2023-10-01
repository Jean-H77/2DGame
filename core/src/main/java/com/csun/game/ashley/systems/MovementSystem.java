package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.MovementState;
import com.csun.game.ashley.components.MovementComponent;

import static com.csun.game.ashley.components.MovementComponent.Direction.*;


public class MovementSystem extends IteratingSystem {

    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    public MovementSystem() {
        super(Family.all(MovementComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = mm.get(entity);
        if(!movement.state.equals(MovementState.MOVING)) return;

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
        Gdx.app.log("Movement Magnitude", String.valueOf(Math.sqrt(Math.pow(dest.x, 2) + Math.pow(dest.y, 2))));

        float newX = movement.pos.x + dest.x;
        float newY = movement.pos.y + dest.y;
        //@todo collision checking here

        movement.pos.x  = newX;
        movement.pos.y = newY;
    }
}
