package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ashley.components.MovementComponent;


public class MovementSystem extends IteratingSystem {

    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    public MovementSystem() {
        super(Family.all(MovementComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        MovementComponent movement = mm.get(entity);
        Vector2 dest = Vector2.Zero;

        if(movement.dir.equals(MovementComponent.Direction.N)) {
            dest.y++;
        }


        //@todo collision

        movement.pos = dest;
    }
}
