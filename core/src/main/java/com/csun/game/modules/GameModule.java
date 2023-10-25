package com.csun.game.modules;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.csun.game.MainGame;
import com.csun.game.ashley.systems.AnimationSystem;
import com.csun.game.ashley.systems.MovementSystem;
import com.csun.game.ashley.systems.PlayerInputSystem;
import com.csun.game.ashley.systems.RenderSystem;
import com.csun.game.managers.DialogueManager;
import com.csun.game.managers.NPCManager;
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
            .to(Stage.class).in(Scopes.NO_SCOPE);

        bind(ScreenViewport.class)
            .annotatedWith(Names.named("TitleScreenViewport"))
            .to(ScreenViewport.class).in(Scopes.NO_SCOPE);

        bind(DialogueManager.class).in(Scopes.SINGLETON);
        bind(NPCManager.class).in(Scopes.SINGLETON);

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
    private ExtendViewport provideFitViewport() {
        return new ExtendViewport(1920, 1080);
    }
}
