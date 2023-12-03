package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.google.inject.Inject;

public class TextureComponent implements Component {
    public TextureRegion textureRegion;
    public final ShapeRenderer shape;

    @Inject
    public TextureComponent() {
        this.shape = new ShapeRenderer();
    }

    @Override
    public String toString() {
        return "TextureComponent{" +
            "textureRegion=" + textureRegion +
            '}';
    }
}
