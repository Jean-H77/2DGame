package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class ClickableComponent implements Component {

    public Rectangle bounds;
    public String identification;

    public ClickableComponent(Rectangle bounds, String identification) {
        this.bounds = bounds;
        this.identification = identification;
    }

    public ClickableComponent(String identification) {
        this.bounds = new Rectangle(0,0,0,0);
        this.identification = identification;
    }
}
