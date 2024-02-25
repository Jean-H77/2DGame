package com.csun.game.UI.settings;

public enum ScreenType {
    FULL_SCREEN(0),
    FIXED(1)
    ;
    private final int type;

    ScreenType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
