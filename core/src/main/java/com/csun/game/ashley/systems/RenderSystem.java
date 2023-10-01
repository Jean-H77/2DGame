package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.TextureComponent;

public class RenderSystem extends EntitySystem {

    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final Family family = Family.all(TextureComponent.class, MovementComponent.class).get();
    private ImmutableArray<Entity> entities;

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
            spriteBatch.draw(texture.textureRegion, Gdx.input.getX(), Gdx.input.getY());
            Gdx.app.debug("Render", "Drawing: " + texture.textureRegion.toString());
        }
        spriteBatch.end();
    }
}

