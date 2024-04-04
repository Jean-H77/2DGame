package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class CollisionComponent implements Component {
    public Rectangle bounds;

    public CollisionComponent(Rectangle bounds) {
        this.bounds = bounds;
    }
}
