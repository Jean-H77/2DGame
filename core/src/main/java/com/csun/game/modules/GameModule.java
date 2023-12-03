package com.csun.game.modules;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.csun.game.GameMap;
import com.csun.game.MainGame;
import com.csun.game.ashley.systems.*;
import com.csun.game.models.Systems;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;

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
        bind(GameMap.class).in(Scopes.SINGLETON);

        bind(PooledEngine.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    private Systems provideSystems() {
        return new Systems(List.of(
            PlayerInputSystem.class,
            MovementSystem.class,
            AnimationSystem.class,
            RenderSystem.class,
            CameraSystem.class
        ));
    }
}
