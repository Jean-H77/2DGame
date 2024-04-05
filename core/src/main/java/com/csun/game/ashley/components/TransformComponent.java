package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ashley.systems.TransformSystem;
import com.csun.game.models.Direction;
import com.csun.game.models.MovementState;
import com.csun.game.models.WalkDirection;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class TransformComponent implements Component {

    public Vector2 pos;
    public Deque<WalkDirection> walkDirections = new ArrayDeque<>();
    public WalkDirection currentWalkDirection;
    public Direction dir = Direction.N;
    public MovementState state = MovementState.IDLE;
    public float velocity = 0f;
    public boolean disabled;

    public TransformComponent(Vector2 vector2) {
        this.pos = vector2;
    }

    public TransformComponent() {
    }

    public void move(Direction dir) {
        reset();
        walkDirections.add(new WalkDirection(dir, TransformSystem.calculateMovementStep(dir, velocity)));
    }

    public void move(List<WalkDirection> dir) {
        reset();
        walkDirections.addAll(dir);
    }

    public void reset() {
        walkDirections.clear();
        currentWalkDirection = null;
        velocity = 1f;
        state = MovementState.MOVING;
    }
}
