package com.csun.game.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;

public record Door(
    Rectangle rectangle,
    MapProperties properties
) {
}
