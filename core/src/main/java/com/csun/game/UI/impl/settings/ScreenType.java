package com.csun.game.UI.impl.settings;

public enum ScreenType {
    FULL_SCREEN(0),
    WINDOWED(1)
    ;
    private final int type;

    ScreenType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
