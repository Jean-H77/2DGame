package com.csun.game.models;

public enum AnimationType {
    CHARACTER_MOVE_UP("TopDownCharacter/character_atlas.atlas", "Character_Up", 0.05f),
    CHARACTER_MOVE_DOWN("TopDownCharacter/character_atlas.atlas", "Character_Down", 0.05f),
    CHARACTER_MOVE_LEFT("TopDownCharacter/character_atlas.atlas", "Character_Left", 0.05f),
    CHARACTER_MOVE_RIGHT("TopDownCharacter/character_atlas.atlas", "Character_Right", 0.05f);

    private final String atlasPath;
    private final String atlasKey;
    private final float frameTime;

    AnimationType(String atlas, String name, float frameTime) {
        this.atlasPath = atlas;
        this.atlasKey = name;
        this.frameTime = frameTime;
    }

    public String getAtlasPath() {
        return atlasPath;
    }

    public String getAtlasKey() {
        return atlasKey;
    }

    public float getFrameTime() {
        return frameTime;
    }
}
