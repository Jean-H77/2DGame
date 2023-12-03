package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Camera;

public class PlayerCameraComponent implements Component {
    public final Camera camera;

    public PlayerCameraComponent(Camera camera) {
        this.camera = camera;
    }
}
