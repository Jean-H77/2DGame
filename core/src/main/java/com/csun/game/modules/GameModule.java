package com.csun.game.modules;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.csun.game.MainGame;
import com.csun.game.ashley.systems.AnimationSystem;
import com.csun.game.ashley.systems.MovementSystem;
import com.csun.game.ashley.systems.PlayerInputSystem;
import com.csun.game.ashley.systems.RenderSystem;
import com.csun.game.screens.GameScreen;
import com.csun.game.screens.TitleScreen;
import com.google.inject.*;
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
        bind(SpriteBatch.class);
        bind(PooledEngine.class);

        bind(Screen.class)
            .annotatedWith(Names.named("GameScreen"))
            .to(GameScreen.class)
            .asEagerSingleton();

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
    @Inject
    private OrthogonalTiledMapRenderer provideMapRenderer(@Named("MainGameMap") TiledMap tiledMap) {
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    public record Systems(List<Class<? extends EntitySystem>> list) { }
}
