package com.csun.game.interfaces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Rectangle extends Actor {


    private final ShapeRenderer shapeRenderer;
    private final float xPos, yPos;
    private final float width, height;
    private final Color color;
    private final ShapeRenderer.ShapeType shapeType;

    public Rectangle(ShapeRenderer shapeRenderer, float xPos, float yPos, float width, float height, Color color, ShapeRenderer.ShapeType shapeType) {
        this.shapeRenderer = shapeRenderer;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.color = color;
        this.shapeType = shapeType;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        shapeRenderer.set(shapeType);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(xPos, yPos, width, height);
    }
}
