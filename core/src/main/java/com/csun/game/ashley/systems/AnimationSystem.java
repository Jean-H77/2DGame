package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.AnimationComponent;
import com.csun.game.ashley.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {

    public AnimationSystem() {
        super(Family.all(TextureComponent.class, AnimationComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animation = Mappers.animations.get(entity);
        TextureComponent texture = Mappers.textures.get(entity);
    }
}
