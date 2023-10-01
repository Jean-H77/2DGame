package com.csun.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    // idk if we need this he talked about it private StaticTiledMapTile

    private OrthographicCamera camera; //character camera
    private OrthographicCamera mapCamera; //map camera
    private final MainGame game;
    private final PooledEngine pooledEngine;

    public FirstScreen(MainGame game) {
        this.game = game;

        pooledEngine = game.getEngine();
    }

    @Override
    public void show() {
        // long way TmxMapLoader loader = new TmxMapLoader();
        //map = loader.load("tempmap.tmx");
        camera = new OrthographicCamera();
        mapCamera = new OrthographicCamera();
        map = new TmxMapLoader().load("tempmap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        float mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
        float mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);
        camera.setToOrtho(false, mapWidth, mapHeight);

    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        renderer.setView(camera);
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
        map.dispose();
        renderer.dispose();
    }
}
