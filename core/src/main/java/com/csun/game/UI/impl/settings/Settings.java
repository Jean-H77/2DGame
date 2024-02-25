package com.csun.game.UI.impl.settings;

public class Settings {

    private int packedInt;

    public void setScreenType(ScreenType screenType) {
        packedInt &= ~1;
        packedInt |= screenType.getType();
    }

    public ScreenType getScreenType() {
        return (packedInt & 1) == 0 ? ScreenType.FULL_SCREEN : ScreenType.WINDOWED;
    }

    public void setAudioLevel(int value) {
        packedInt &= ~(0b1111 << 2);
        packedInt |= ((value & 0b1111) << 2);
    }

    public int getAudioLevel() {
        return (packedInt >> 2) & 0b1111;
    }

    public int getPackedSettings() {
        return packedInt;
    }

    public void setPackedSettings(int packed) {
        this.packedInt = packed;
    }
}
