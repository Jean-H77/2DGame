package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Camera;

public record CameraComponent(Camera camera) implements Component {
}
