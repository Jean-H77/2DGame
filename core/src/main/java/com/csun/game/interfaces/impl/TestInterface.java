package com.csun.game.interfaces.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.csun.game.interfaces.Interface;

public class TestInterface extends Interface {

    public static final int ID = 1;

    public TestInterface() {
        super(ID);
    }

    @Override
    public void buildView() {
        addRectangle(50, 50, 100, 100, Color.CHARTREUSE, ShapeRenderer.ShapeType.Filled)
            .addRectangle(100, 100, 100, 100, Color.RED, ShapeRenderer.ShapeType.Filled);

        addImageButtonWithClickHover("switch_on.png", "switch_off.png",
            225, 225, (x, y) -> Gdx.app.log("Clicked", x + " " + y));
    }
}
