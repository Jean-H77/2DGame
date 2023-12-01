package com.csun.game.interfaces.impl;

import com.csun.game.interfaces.Interface;

public class TestInterface extends Interface {

    @Override
    public void buildView() {
        addImageButton("/3.png", 5, 5, () -> {})
            .addImageButton("/4.png", 6, 6, () -> {})
            .addImageButtonWithHover("/5.png", "/6.png", 7, 7, () -> {});
    }
}
