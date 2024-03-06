package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.csun.game.MainGame;

public class FadeScreen implements Screen {

    private final Screen current;
    private final Screen next;
    private final MainGame mainGame;

    private float alpha = 0;
    private boolean fadeDirection = true;
    private ShapeRenderer shapeRenderer;

    public FadeScreen(Screen current, Screen next, MainGame game) {
        this.current = current;
        this.next = next;
        game.setScreen(next);
        game.setScreen(current);
        this.mainGame = game;
    }

    public static void fade(Screen current, Screen next, MainGame mainGame) {
        mainGame.setScreen(new FadeScreen(current, next, mainGame));
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        if (fadeDirection) {
            current.render(Gdx.graphics.getDeltaTime());
        } else {
            next.render(Gdx.graphics.getDeltaTime());
        }

        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setColor(0, 0 ,0, alpha);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

        if (alpha >= 1) {
            fadeDirection = false;
        }
        else if (alpha <= 0 && !fadeDirection) {
            mainGame.setScreen(next);
        }
        alpha += (float) (fadeDirection ? 0.01 : -0.01);
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

    }
}
