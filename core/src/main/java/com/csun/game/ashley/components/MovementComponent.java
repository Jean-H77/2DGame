package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component {

    public enum Direction {N,S,E, SW, SE, NW, W, NE}

    public enum MovementState {IDLE, MOVING}

    public Vector2 pos = Vector2.Zero;
    public Direction dir = Direction.N;
    public MovementState state = MovementState.IDLE;
    public float velocity;

    public MovementComponent(Vector2 vector2) {
        this.pos = vector2;
    }

    public MovementComponent() {

    }
}
