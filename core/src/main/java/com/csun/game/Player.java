package com.csun.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.TextureComponent;

public class Player {

    private final OrthographicCamera camera = new OrthographicCamera();
    private final Entity entity;
    private final MovementComponent movementComponent;
    private final TextureComponent textureComponent;

    public Player(Entity entity) {
        this.entity = entity;
        this.movementComponent = entity.getComponent(MovementComponent.class);
        this.textureComponent = entity.getComponent(TextureComponent.class);
    }

    public float getX() {
        return movementComponent.pos.x;
    }

    public float getY() {
        return movementComponent.pos.y;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Entity getEntity() {
        return entity;
    }

    public MovementComponent getMovementComponent() {
        return movementComponent;
    }

    public TextureComponent getTextureComponent() {
        return textureComponent;
    }

    //@Todo think of new design
    public void update(float deltaTime) {
        camera.position.x = getX() + 8; // width(16) / 2 = 8, hard coded for now until we get player sprites
        camera.position.y = getY() + 8;
        camera.update();
        textureComponent.shape.setProjectionMatrix(camera.combined);
    }
}
