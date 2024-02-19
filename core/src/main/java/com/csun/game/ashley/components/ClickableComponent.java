package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class ClickableComponent implements Component {

    public Rectangle bounds;

    public ClickableComponent(Rectangle bounds) {
        this.bounds = bounds;
    }

    public ClickableComponent() {
        this.bounds = new Rectangle(0,0,0,0);
    }
}
