package com.csun.game.map;

import com.badlogic.ashley.core.Entity;

public record MapEntry(
    boolean[][] grid,
    Entity entities,
    Door[] doors
) {
}
