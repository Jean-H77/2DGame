package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.csun.game.ashley.components.PlayerCameraComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.TextureComponent;

public class CameraSystem extends IteratingSystem {

    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<PlayerCameraComponent> cm = ComponentMapper.getFor(PlayerCameraComponent.class);
    private final ComponentMapper<TextureComponent> tc = ComponentMapper.getFor(TextureComponent.class);

    public CameraSystem() {
        super(Family.all(PlayerCameraComponent.class, MovementComponent.class, TextureComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = mm.get(entity);
        PlayerCameraComponent camera = cm.get(entity);
        TextureComponent texture = tc.get(entity);

        camera.camera.position.x = movement.pos.x + 8; // width(16) / 2 = 8, hard coded for now until we get player sprites
        camera.camera.position.y = movement.pos.y + 8;
        camera.camera.update();
        texture.shape.setProjectionMatrix(camera.camera.combined);
    }
}
