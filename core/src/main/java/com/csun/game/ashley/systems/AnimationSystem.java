package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.AnimationComponent;
import com.csun.game.ashley.components.TransformComponent;
import com.google.inject.Inject;

public class AnimationSystem extends IteratingSystem {

    private final SpriteBatch spriteBatch;

    @Inject
    public AnimationSystem(SpriteBatch spriteBatch) {
        super(Family.all(AnimationComponent.class, TransformComponent.class).get());
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void update(float deltaTime) {
        spriteBatch.begin();
        super.update(deltaTime);
        spriteBatch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animation = Mappers.animations.get(entity);
        TransformComponent transform = Mappers.transforms.get(entity);
        if(animation.textureRegionAnimation == null) return;
        TextureRegion textureRegion = animation.textureRegionAnimation.getKeyFrame(animation.stateTime, true);
        spriteBatch.draw(
            textureRegion,
            transform.pos.x, transform.pos.y,
            animation.width, animation.height);
        animation.stateTime += deltaTime;
    }
}
