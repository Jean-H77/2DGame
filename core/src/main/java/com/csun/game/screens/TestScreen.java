package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class TestScreen implements Screen {

    private final TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("witch.atlas"));

    Image image;
    SpriteBatch spriteBatch;
    float stateTime = 0f;
    private Animation<TextureRegion> animation;

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        Array<TextureAtlas.AtlasRegion> atlasRegions = textureAtlas.findRegions("east");
        atlasRegions.removeIndex(0);
        atlasRegions.removeIndex(atlasRegions.size-1);
        animation = new Animation<>(1f/7f, atlasRegions);
    }

    @Override
    public void render(float v) {
        stateTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(animation.getKeyFrame(stateTime,true),0,0);
        spriteBatch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
    }
}
