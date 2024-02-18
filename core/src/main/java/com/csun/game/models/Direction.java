package com.csun.game.models;

public enum Direction {
    N(90.0f),
    S(270.0f),
    E(0.0f),
    SW(225.0f),
    SE(315.0f),
    NW(135.0f),
    W(180.0f),
    NE(45.0f);

    private final float degree;

    Direction(float degree) {
        this.degree = degree;
    }

    public float getDegree() {
        return degree;
    }
}
