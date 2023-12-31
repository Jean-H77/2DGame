package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.models.Direction;
import com.csun.game.models.MovementState;

public class MovementComponent implements Component {

    public Vector2 pos = Vector2.Zero;
    public Direction dir = Direction.N;
    public MovementState state = MovementState.IDLE;
    public float velocity = 0f;
    public boolean disabled;

    public MovementComponent(Vector2 vector2) {
        this.pos = vector2;
    }

    public MovementComponent() {

    }

    public void move(Direction dir) {
        velocity = 1;
        state = MovementState.MOVING;
        this.dir = dir;
    }
}
