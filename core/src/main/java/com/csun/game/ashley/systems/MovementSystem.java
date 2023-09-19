package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.csun.game.ashley.components.TransformComponent;

public class MovementSystem extends IteratingSystem {

    private final ComponentMapper<TransformComponent> mm = ComponentMapper.getFor(TransformComponent.class);

    public MovementSystem() {
        super(Family.all(TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = mm.get(entity);

        
    }
}
