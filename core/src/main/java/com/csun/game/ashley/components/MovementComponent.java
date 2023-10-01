package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component {
    public Vector2 pos = Vector2.Zero;
    public Direction dir = Direction.N;

    public enum Direction {
        N,S,E,W
    }
}
