package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.MovementState;

public class MovementComponent implements Component {
    public Vector2 pos = Vector2.Zero;
    public Direction dir = Direction.N;
    public MovementState state = MovementState.IDLE;

    public enum Direction {
        N,S,E,W
    }
}
