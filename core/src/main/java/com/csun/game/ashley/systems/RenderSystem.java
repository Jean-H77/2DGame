package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.csun.game.ashley.components.TextureComponent;
import com.csun.game.ashley.components.TransformComponent;

public class RenderSystem extends EntitySystem {

    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final Family family = Family.all(TextureComponent.class, TransformComponent.class).get();
    private Array<Entity> entities;

    @Override
    public void addedToEngine(com.badlogic.ashley.core.Engine engine) {
        entities = engine.getEntitiesFor(family);
    }

    @Override
    public void update(float deltaTime) {
        spriteBatch.begin();

        for (Entity entity : entities) {
            TextureComponent texture = entity.getComponent(TextureComponent.class);
            TransformComponent transform = entity.getComponent(TransformComponent.class);

            spriteBatch.draw(texture.textureRegion, transform.vector2.x, transform.vector2.y);
        }

        spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Entity entity : entities) {
            TransformComponent transform = entity.getComponent(TransformComponent.class);

            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.circle(transform.vector2.x, transform.vector2.y, 20);
        }

        shapeRenderer.end();
    }
}

