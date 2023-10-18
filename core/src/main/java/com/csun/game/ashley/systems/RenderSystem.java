package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.google.inject.Inject;

public class RenderSystem extends EntitySystem {

    private final SpriteBatch spriteBatch;

    private ImmutableArray<Entity> entities;

    private final Family family = Family.all(TextureComponent.class, MovementComponent.class).get();

    @Inject
    public RenderSystem(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(family);
    }

    @Override
    public void update(float deltaTime) {
        spriteBatch.begin();
        for (Entity entity : entities) {
            TextureComponent texture = entity.getComponent(TextureComponent.class);
            MovementComponent movement = entity.getComponent(MovementComponent.class);
            if(texture.textureRegion == null) continue;
            spriteBatch.draw(texture.textureRegion, movement.pos.x, movement.pos.y);
        }
        spriteBatch.end();
    }
}

