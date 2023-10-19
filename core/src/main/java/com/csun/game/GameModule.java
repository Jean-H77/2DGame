package com.csun.game;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.csun.game.ashley.systems.AnimationSystem;
import com.csun.game.ashley.systems.MovementSystem;
import com.csun.game.ashley.systems.PlayerInputSystem;
import com.csun.game.ashley.systems.RenderSystem;
import com.csun.game.screens.GameScreen;
import com.csun.game.screens.TitleScreen;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
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
        bind(SpriteBatch.class).toInstance(new SpriteBatch());
        bind(PooledEngine.class).toInstance(new PooledEngine());

        bind(Screen.class)
            .annotatedWith(Names.named("GameScreen"))
            .to(GameScreen.class)
            .asEagerSingleton();

        bind(Screen.class)
            .annotatedWith(Names.named("TitleScreen"))
            .to(TitleScreen.class)
            .in(Scopes.NO_SCOPE);

        bind(OrthographicCamera.class)
            .annotatedWith(Names.named("PlayerCamera"))
            .toInstance(new OrthographicCamera());

        bind(OrthographicCamera.class)
            .annotatedWith(Names.named("MapCamera"))
            .toInstance(new OrthographicCamera());

        TiledMap tm = new TmxMapLoader().load("tempmap.tmx");
        bind(TiledMap.class)
            .annotatedWith(Names.named("MainGameMap"))
            .toInstance(tm);

        bind(OrthogonalTiledMapRenderer.class)
            .toInstance(new OrthogonalTiledMapRenderer(tm));

        //bindConstant()
          //  .annotatedWith(Names.named("mapWidth"))
           // .to(tm.getProperties().get("width", Integer.class) * tm.getProperties().get("tilewidth", Integer.class));

     //   bindConstant()
        //    .annotatedWith(Names.named("mapHeight"))
         //   .to(tm.getProperties().get("height", Integer.class) * tm.getProperties().get("tileheight", Integer.class));
    }

    @Provides
    @Singleton
    public Systems getSystems() {
        return new Systems(List.of(
            PlayerInputSystem.class,
            MovementSystem.class,
            AnimationSystem.class,
            RenderSystem.class
        ));
    }

    public record Systems(List<Class<? extends EntitySystem>> list) { }
}
