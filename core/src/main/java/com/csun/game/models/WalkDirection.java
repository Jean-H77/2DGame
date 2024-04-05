package com.csun.game.models;

import com.badlogic.gdx.math.Vector2;

public record WalkDirection(
    Direction direction,
    Vector2 point
) {

}
