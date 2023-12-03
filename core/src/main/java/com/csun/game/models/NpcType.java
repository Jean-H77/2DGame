package com.csun.game.models;

import com.csun.game.ashley.components.TextureComponent;
import com.google.common.collect.ImmutableList;

public enum NpcType {
    MAN(new TextureComponent()),
    WOMEN(new TextureComponent());

    private final TextureComponent textureComponent;

    NpcType(TextureComponent textureComponent) {
        this.textureComponent = textureComponent;
    }

    public TextureComponent getTextureComponent() {
        return textureComponent;
    }

    public static final ImmutableList<NpcType> VALUES = ImmutableList.copyOf(values());
}
