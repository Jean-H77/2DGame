package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.models.Direction;
import com.csun.game.models.MovementState;
import com.github.tommyettinger.gand.Path;
import com.github.tommyettinger.gand.points.PointI2;

import java.util.ArrayDeque;
import java.util.Deque;

public class TransformComponent implements Component {

    public Vector2 pos;
    public Deque<Direction> directionQueue = new ArrayDeque<>();
    public Direction dir = Direction.N;
    public MovementState state = MovementState.IDLE;
    public float velocity = 0f;
    public boolean disabled;
    public Path<PointI2> path = new Path<>();

    public TransformComponent(Vector2 vector2) {
        this.pos = vector2;
    }

    public TransformComponent() {
    }

    public void move(Direction dir) {
        directionQueue.clear();
        path.clear();
        velocity = 1f;
        state = MovementState.MOVING;
        directionQueue.add(dir);
    }

    public void setPath(Path<PointI2> path) {
        if (path.isEmpty()) {
            return;
        }
        directionQueue.clear();
        this.path = path;
        velocity = 1f;
        state = MovementState.MOVING;
    }
}
