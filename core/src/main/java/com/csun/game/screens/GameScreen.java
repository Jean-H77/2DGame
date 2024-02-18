package com.csun.game.screens;

import box2dLight.RayHandler;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csun.game.map.GameMap;
import com.csun.game.player.Player;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import static com.csun.game.GameConstants.VIEWPORT_HEIGHT;
import static com.csun.game.GameConstants.VIEWPORT_WIDTH;

/** First screen of the application. Displayed after the application is created. */
@Singleton
public class GameScreen implements Screen {

    private final GameMap gameMap;
    private final RayHandler rayHandler;
    private final PooledEngine pooledEngine;
    private final OrthographicCamera playerCamera;
    private final OrthogonalTiledMapRenderer renderer;
    private final Player player;
    private final Viewport viewport;

    @Inject
    public GameScreen(PooledEngine pooledEngine, @Named("ExtendedViewport") Viewport viewport, Player player) {
        this.pooledEngine = pooledEngine;
        this.gameMap = player.getGameMap();
        this.renderer = gameMap.getRenderer();
        this.rayHandler = gameMap.getRayHandler();
        this.player = player;
        this.viewport = viewport;
        playerCamera = (OrthographicCamera) player.getCamera();
    }

    @Override
    public void show() {
        viewport.setCamera(playerCamera);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        player.getTextureComponent().shape.setProjectionMatrix(playerCamera.combined);

        renderer.setView(playerCamera);
        renderer.render();

        pooledEngine.update(Gdx.graphics.getDeltaTime());

        rayHandler.setCombinedMatrix(playerCamera.combined);
        rayHandler.updateAndRender();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
