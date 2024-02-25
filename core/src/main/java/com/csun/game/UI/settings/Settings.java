package com.csun.game.UI.settings;

public class Settings {

    private int packedInt; //wanted to try something different and use bitwise operations

    public void setScreenType(ScreenType screenType) {
        packedInt &= ~1;
        packedInt |= screenType.getType();
    }

    public ScreenType getScreenType() {
        return (packedInt & 1) == 0 ? ScreenType.FULL_SCREEN : ScreenType.FIXED;
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

    /*public static void main(String[] args) {
        Settings settings = new Settings();
        System.out.println("Type: " + settings.getScreenType());
        settings.setScreenType(ScreenType.FULL_SCREEN);
        System.out.println("Type: " + settings.getScreenType());
        settings.setScreenType(ScreenType.FIXED);
        System.out.println("Type: " + settings.getScreenType());
        settings.setScreenType(ScreenType.FULL_SCREEN);
        System.out.println("Type: " + settings.getScreenType());
        settings.setScreenType(ScreenType.FIXED);
        System.out.println("Type: " + settings.getScreenType());

        System.out.println("Audio: " + settings.getAudioLevel());
        settings.setAudioLevel(5);
        System.out.println("Audio: " + settings.getAudioLevel());
        System.out.println("Type: " + settings.getScreenType());
        settings.setScreenType(ScreenType.FULL_SCREEN);
        System.out.println("Type: " + settings.getScreenType());
        System.out.println("Audio: " + settings.getAudioLevel());
        settings.setAudioLevel(10);
        System.out.println("Audio: " + settings.getAudioLevel());
        settings.setScreenType(ScreenType.FIXED);
        System.out.println("Audio: " + settings.getAudioLevel());
        System.out.println("Type: " + settings.getScreenType());
    }*/
}
