package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.CameraComponent;
import com.csun.game.ashley.components.TransformComponent;
import com.csun.game.map.GameMap;
import com.google.inject.Inject;

public class CameraSystem extends IteratingSystem {

    private final GameMap gameMap;

    @Inject
    public CameraSystem(GameMap gameMap) {
        super(Family.all(CameraComponent.class, TransformComponent.class).get());
        this.gameMap = gameMap;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = Mappers.transforms.get(entity);
        CameraComponent cameraComponent = Mappers.cameras.get(entity);
        Rectangle boundary = gameMap.getBoundaries();

        if(cameraComponent.camera() instanceof OrthographicCamera camera) {
            camera.position.x = transformComponent.pos.x + 8;
            camera.position.y = transformComponent.pos.y + 8;

            float halfViewportWidth = (camera.viewportWidth * camera.zoom) / 2f;
            float halfViewportHeight = (camera.viewportHeight * camera.zoom) / 2f;

            camera.position.x = MathUtils.clamp(camera.position.x, boundary.getX() + halfViewportWidth, boundary.getX() + boundary.getWidth() - halfViewportWidth);
            camera.position.y = MathUtils.clamp(camera.position.y, boundary.getY() + halfViewportHeight, boundary.getY() + boundary.getHeight() - halfViewportHeight);

            camera.update();
        }
    }
}
