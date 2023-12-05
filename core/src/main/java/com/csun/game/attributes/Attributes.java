package com.csun.game.attributes;

public final class Attributes {

    private Attributes() {

    }

    public static final AttributeKey<String> PLAYER_NAME = new AttributeKey<>(true);

    public static final AttributeKey<Integer> PLAYER_KILLS = new AttributeKey<>(true);
}
