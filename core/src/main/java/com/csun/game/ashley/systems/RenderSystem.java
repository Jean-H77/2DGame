package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.google.inject.Inject;

public class RenderSystem extends IteratingSystem {

    private final SpriteBatch spriteBatch;

    @Inject
    public RenderSystem(SpriteBatch spriteBatch) {
        super(Family.all(TextureComponent.class, MovementComponent.class).get());
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
        TextureComponent texture = entity.getComponent(TextureComponent.class);
        MovementComponent movement = entity.getComponent(MovementComponent.class);
        texture.shape.begin(ShapeRenderer.ShapeType.Filled);
        texture.shape.setColor(Color.RED);
        texture.shape.circle(movement.pos.x, movement.pos.y, 15);
        texture.shape.end();
    }

}

