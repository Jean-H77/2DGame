package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.csun.game.ashley.components.AnimationComponent;
import com.csun.game.ashley.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {

    private final ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);

    private final ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);

    public AnimationSystem() {
        super(Family.all(TextureComponent.class, AnimationComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animation = am.get(entity);
        TextureComponent texture = tm.get(entity);
    }
}
