package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.csun.game.GameMap;
import com.csun.game.ashley.components.CameraComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.google.inject.Inject;

import static com.csun.game.GameConstants.*;

public class CameraSystem extends IteratingSystem {

    private final ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<CameraComponent> cm = ComponentMapper.getFor(CameraComponent.class);
    private final GameMap gameMap;

    @Inject
    public CameraSystem(GameMap gameMap) {
        super(Family.all(CameraComponent.class, MovementComponent.class).get());
        this.gameMap = gameMap;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movementComponent = mm.get(entity);
        CameraComponent cameraComponent = cm.get(entity);

        if(cameraComponent.camera() instanceof OrthographicCamera camera) {
            camera.position.x = movementComponent.pos.x + 8;
            camera.position.y = movementComponent.pos.y + 8;

            float halfViewportWidth = (camera.viewportWidth * camera.zoom) / 2f;
            float halfViewportHeight = (camera.viewportHeight * camera.zoom) / 2f;
            Rectangle boundary = gameMap.getBoundaries();

            camera.position.x = MathUtils.clamp(camera.position.x, boundary.getX() + halfViewportWidth, boundary.getX() + boundary.getWidth() - halfViewportWidth);
            camera.position.y = MathUtils.clamp(camera.position.y, boundary.getY() + halfViewportHeight, boundary.getY() + boundary.getHeight() - halfViewportHeight);

            camera.update();
        }
    }
}
