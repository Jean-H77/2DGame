package com.csun.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.csun.game.GameMap;
import com.csun.game.MainGame;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.csun.game.interfaces.impl.TestInterface;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/** First screen of the application. Displayed after the application is created. */
@Singleton
public class GameScreen implements Screen {

    private final OrthographicCamera playerCamera;
    private final MainGame game;
    private final GameMap gameMap;
    private final PooledEngine pooledEngine;
    private PlayerComponent playerComponent;

    @Inject
    public GameScreen(MainGame game, PooledEngine pooledEngine, GameMap gameMap,
                      @Named("PlayerCamera") OrthographicCamera playerCamera) {
        this.game = game;
        this.pooledEngine = pooledEngine;
        this.playerCamera = playerCamera;
        this.gameMap = gameMap;
    }

    private void createPlayer() {
        Entity entity = pooledEngine.createEntity();
        playerComponent = new PlayerComponent();
        entity.add(new TextureComponent(new ShapeRenderer()));
        entity.add(playerComponent);
        entity.add(new MovementComponent());
        pooledEngine.addEntity(entity);
        Gdx.app.log("CreatePlayer", "Created Player: Size: " + pooledEngine.getEntities().size());
    }

    @Override
    public void show() {
        playerCamera.setToOrtho(false, 1920, 1080);
        if(playerComponent == null) {
            createPlayer();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        playerCamera.update();

        gameMap.getRenderer().setView(playerCamera);
        gameMap.getRenderer().render();

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
