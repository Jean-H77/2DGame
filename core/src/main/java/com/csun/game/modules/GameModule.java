package com.csun.game.modules;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csun.game.MainGame;
import com.csun.game.ashley.systems.AnimationSystem;
import com.csun.game.ashley.systems.MovementSystem;
import com.csun.game.ashley.systems.PlayerInputSystem;
import com.csun.game.ashley.systems.RenderSystem;
import com.csun.game.models.Systems;
import com.csun.game.screens.GameScreen;
import com.csun.game.screens.TitleScreen;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.util.List;

import static com.csun.game.GameConstants.VIEWPORT_HEIGHT;
import static com.csun.game.GameConstants.VIEWPORT_WIDTH;

public class GameModule extends AbstractModule {

    private final MainGame mainGame;

    public GameModule(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    @Override
    protected void configure() {
        bind(MainGame.class).toInstance(mainGame);

        bind(SpriteBatch.class).in(Scopes.SINGLETON);
        bind(ShapeRenderer.class).in(Scopes.SINGLETON);
        bind(BitmapFont.class).in(Scopes.SINGLETON);

        bind(Stage.class)
            .annotatedWith(Names.named("TitleScreenStage"))
            .to(Stage.class).asEagerSingleton();

        bind(ScreenViewport.class)
            .annotatedWith(Names.named("TitleScreenViewport"))
            .to(ScreenViewport.class).in(Scopes.NO_SCOPE);

        bind(PooledEngine.class).asEagerSingleton();

        bind(Screen.class)
            .annotatedWith(Names.named("GameScreen"))
            .to(GameScreen.class)
            .in(Scopes.SINGLETON);

        bind(Screen.class)
            .annotatedWith(Names.named("TitleScreen"))
            .to(TitleScreen.class)
            .in(Scopes.NO_SCOPE);

        bind(OrthographicCamera.class)
            .annotatedWith(Names.named("MapCamera"))
            .to(OrthographicCamera.class);

        bind(TiledMap.class)
            .annotatedWith(Names.named("MainGameMap"))
            .toProvider(() -> new TmxMapLoader().load("tempmap.tmx"));

        bind(Viewport.class)
            .annotatedWith(Names.named("Extended"))
            .toProvider(() -> new ExtendViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
    }

    @Provides
    @Singleton
    private Systems provideSystems() {
        return new Systems(List.of(
            PlayerInputSystem.class,
            MovementSystem.class,
            AnimationSystem.class,
            RenderSystem.class
        ));
    }

    @Provides
    @Singleton
    private OrthogonalTiledMapRenderer provideMapRenderer(@Named("MainGameMap") TiledMap tiledMap) {
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    @Provides
    private ExtendViewport provideExtendedViewport() {
        return new ExtendViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }

    @Provides
    @Singleton
    private TiledMapTileLayer[] provideMapLayers(@Named("MainGameMap") TiledMap tiledMap) {
        TiledMapTileLayer[] tiledMapTileLayer = new TiledMapTileLayer[tiledMap.getLayers().size()];
        for(int i = 0; i < tiledMapTileLayer.length; i++) tiledMapTileLayer[i] = (TiledMapTileLayer) tiledMap.getLayers().get(i);
        return tiledMapTileLayer;
    }
}
