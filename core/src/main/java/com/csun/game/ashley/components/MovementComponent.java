package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ashley.systems.MovementSystem;
import com.csun.game.models.Direction;
import com.csun.game.models.MovementState;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class MovementComponent implements Component {

    public Vector2 pos;
    public Deque<Direction> movementQueue = new ArrayDeque<>();
    public Direction dir = Direction.N;
    public MovementState state = MovementState.IDLE;
    public float velocity = 0f;
    public boolean disabled;
    public Deque<Vector2> waypoints = new ArrayDeque<>();
    public Vector2 nextWaypoint;

    public MovementComponent(Vector2 vector2) {
        this.pos = vector2;
    }

    public void move(Direction dir) {
        velocity = 1f;
        state = MovementState.MOVING;
        movementQueue.add(dir);
    }

    public void setPath(Deque<Vector2> path) {
        velocity = 1f;
        this.waypoints = path;
        state = MovementState.MOVING;
        this.nextWaypoint = this.waypoints.pop();
    }
}
