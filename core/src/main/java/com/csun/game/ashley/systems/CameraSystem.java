package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.csun.game.ashley.components.CameraComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.TextureComponent;

public class CameraSystem extends IteratingSystem {

    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<CameraComponent> cm = ComponentMapper.getFor(CameraComponent.class);

    public CameraSystem() {
        super(Family.all(CameraComponent.class, MovementComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movementComponent = mm.get(entity);
        CameraComponent cameraComponent = cm.get(entity);

        Camera camera = cameraComponent.camera();
        camera.position.x = movementComponent.pos.x + 8; // width(16) / 2 = 8, hard coded for now until we get player sprites
        camera.position.y = movementComponent.pos.y + 8;
        camera.update();
    }
}
