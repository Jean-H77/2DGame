package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.MainGame;
import com.csun.game.models.Dialogue;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class TitleScreen implements Screen {

    private final MainGame mainGame;
    private final Screen gameScreen;
    private final SpriteBatch spriteBatch;
    private Texture background;

    @Inject
    public TitleScreen(MainGame mainGame, @Named("GameScreen")Screen gameScreen, SpriteBatch spriteBatch) {
        this.mainGame = mainGame;
        this.gameScreen = gameScreen;
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("titlescreen/background.jpg"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();
    }


    @Override
    public void resize(int width, int height) {

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
        spriteBatch.dispose();
        background.dispose();
    }

}
