package com.csun.game.screens;

import box2dLight.RayHandler;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.*;
import com.csun.game.map.GameMap;
import com.csun.game.player.Player;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import static com.csun.game.GameConstants.PLAYER_START_X;
import static com.csun.game.GameConstants.PLAYER_START_Y;

/** First screen of the application. Displayed after the application is created. */
@Singleton
public class GameScreen implements Screen {

    private final RayHandler rayHandler;
    private final PooledEngine pooledEngine;
    private final OrthographicCamera playerCamera;
    private final OrthogonalTiledMapRenderer renderer;
    private final Player player;
    private final Viewport viewport;

    @Inject
    public GameScreen(PooledEngine pooledEngine, @Named("ExtendedViewport") Viewport viewport, Player player) {
        this.pooledEngine = pooledEngine;
        this.renderer = GameMap.INSTANCE.getRenderer();
        this.rayHandler = GameMap.INSTANCE.getRayHandler();
        this.player = player;
        this.viewport = viewport;
        playerCamera = (OrthographicCamera) player.getCamera();
    }

    @Override
    public void show() {
        viewport.setCamera(playerCamera);
        Entity entity = pooledEngine.createEntity();
        entity.add(new MovementComponent(new Vector2(400, 400)));
        entity.add(new ClickableComponent(new Rectangle(400, 400, 16, 16), "Blob"));
        entity.add(new TextureComponent());
        pooledEngine.addEntity(entity);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        pooledEngine.getEntitiesFor(Family.all(TextureComponent.class).get()).forEach(entity -> Mappers.textures.get(entity).shape.setProjectionMatrix(playerCamera.combined));
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
        GameMap.INSTANCE.dispose();
    }
}
