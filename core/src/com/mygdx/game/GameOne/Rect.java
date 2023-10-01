package com.mygdx.game.GameOne;

public class Rect {
    public float x,y,w,h;

    public Rect(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public String toString() {
        return "Rect [x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + "]";
    }

}
