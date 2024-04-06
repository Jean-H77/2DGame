package com.csun.game.ai.paths;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public record PathResult(List<Vector2> path, List<Vector2> waypoints) {
}
