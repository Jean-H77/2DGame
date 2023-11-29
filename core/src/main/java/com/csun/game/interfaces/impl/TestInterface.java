package com.csun.game.interfaces.impl;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.csun.game.interfaces.Interface;

public class TestInterface extends Interface {

    @Override
    public void populateStage() {
        addImageButton("/image.png", new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
}
