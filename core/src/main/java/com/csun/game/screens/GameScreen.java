package com.csun.game.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.csun.game.GameMap;
import com.csun.game.MainGame;
import com.csun.game.player.Player;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import static com.csun.game.GameConstants.VIEWPORT_HEIGHT;
import static com.csun.game.GameConstants.VIEWPORT_WIDTH;

/** First screen of the application. Displayed after the application is created. */
@Singleton
public class GameScreen implements Screen {

    private final GameMap gameMap;
    private final PooledEngine pooledEngine;
    private final OrthographicCamera playerCamera;
    private final OrthogonalTiledMapRenderer renderer;

    @Inject
    public GameScreen(PooledEngine pooledEngine, GameMap gameMap, Player player) {
        this.pooledEngine = pooledEngine;
        this.gameMap = gameMap;
        this.renderer = gameMap.getRenderer();
        playerCamera = (OrthographicCamera) player.getCamera();
    }

    @Override
    public void show() {
        playerCamera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(playerCamera);
        renderer.render();

        pooledEngine.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.

    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        gameMap.dispose();
    }
}
