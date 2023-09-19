package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.csun.game.ashley.components.TextureComponent;
import com.csun.game.ashley.components.TransformComponent;

public class RenderSystem extends IteratingSystem {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public RenderSystem() {
        super(Family.all(TextureComponent.class, TransformComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TextureComponent texture;
        TransformComponent transform;

        texture = entity.getComponent(TextureComponent.class);
        transform = entity.getComponent(TransformComponent.class);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.circle(transform.vector2.x, transform.vector2.y, 20);
        shapeRenderer.end();
    }
}
