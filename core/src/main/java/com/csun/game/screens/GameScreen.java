package com.csun.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.csun.game.GameMap;
import com.csun.game.MainGame;
import com.csun.game.ashley.components.BackpackComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.csun.game.player.Player;
import com.csun.game.screens.backpack.BackpackOverlayScreen;
import com.csun.game.screens.backpack.GameOverlayScreen;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import static com.csun.game.GameConstants.VIEWPORT_HEIGHT;
import static com.csun.game.GameConstants.VIEWPORT_WIDTH;

/** First screen of the application. Displayed after the application is created. */
@Singleton
public class GameScreen implements Screen {


    private final MainGame game;

    private final GameMap gameMap;
    private final PooledEngine pooledEngine;
    private final OrthographicCamera playerCamera;
    private final OrthogonalTiledMapRenderer renderer;
    private final Player player;

    private BackpackOverlayScreen backpackOverlayScreen;
    private GameOverlayScreen gameOverlayScreen;

    @Inject
    public GameScreen(MainGame game, PooledEngine pooledEngine, GameMap gameMap, Player player) {
        this.game = game;
        this.pooledEngine = pooledEngine;
        this.gameMap = gameMap;
        this.renderer = gameMap.getRenderer();
        this.player = player;
        playerCamera = (OrthographicCamera) player.getCamera();
    }

    @Override
    public void show() {
        playerCamera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        backpackOverlayScreen = new BackpackOverlayScreen(game,pooledEngine);
        gameOverlayScreen = new GameOverlayScreen(game,pooledEngine,backpackOverlayScreen);

        backpackOverlayScreen.show();
        gameOverlayScreen.show();

        InputMultiplexer  inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(backpackOverlayScreen.getInputProcessor());
        inputMultiplexer.addProcessor(gameOverlayScreen.getInputProcessor());

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //@Todo remove after we get player sprites
        player.getTextureComponent().shape.setProjectionMatrix(playerCamera.combined);

        renderer.setView(playerCamera);
        renderer.render();

        pooledEngine.update(Gdx.graphics.getDeltaTime());

        backpackOverlayScreen.render(delta);
        gameOverlayScreen.render(delta);
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
        backpackOverlayScreen.dispose();
        gameOverlayScreen.dispose();
    }
}
