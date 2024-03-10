package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class ClickableComponent implements Component {

    public Rectangle bounds;
    public String id;

    public ClickableComponent(Rectangle bounds, String id) {
        this.bounds = bounds;
        this.id = id;
    }

    public ClickableComponent() {
        this.bounds = new Rectangle(0,0,0,0);
    }
}
