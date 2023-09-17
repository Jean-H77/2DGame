package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;

public class RenderSystem extends EntitySystem {
    private Batch batch;
    
    public RenderSystem(Batch batch) {
        super(Family.all(TextureComponent.class, TransformComponent.class).get());
        this.batch = batch;
    }
    
    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TextureComponent texture;
        TransformComponent transform;

        texture = entity.getComponent(TextureComponent.class);
        transform = entity.getComponent(TransformComponent.class);

        batch.draw(texture.region, transform.position.x, transform.position.y);
    }
}
