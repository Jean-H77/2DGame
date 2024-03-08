package com.csun.game.modules;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csun.game.MainGame;
import com.csun.game.ashley.systems.*;
import com.csun.game.map.GameMap;
import com.csun.game.models.Systems;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import java.util.List;

import static com.csun.game.GameConstants.VIEWPORT_HEIGHT;
import static com.csun.game.GameConstants.VIEWPORT_WIDTH;

public class GameModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(MainGame.class).in(Scopes.SINGLETON);
        bind(AssetManager.class).in(Scopes.SINGLETON);
        bind(SpriteBatch.class).in(Scopes.SINGLETON);
        bind(ShapeRenderer.class).in(Scopes.SINGLETON);
        bind(BitmapFont.class).in(Scopes.SINGLETON);
        bind(PooledEngine.class).in(Scopes.SINGLETON);
        bind(GameMap.class).in(Scopes.SINGLETON);
        bind(Viewport.class)
            .annotatedWith(Names.named("ExtendedViewport"))
            .toProvider(() -> new ExtendViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT))
            .in(Scopes.SINGLETON);
    }

    @Provides
    @Singleton
    private Systems provideSystems() {
        return new Systems(List.of(
            PlayerInputSystem.class,
            MovementSystem.class,
            AnimationSystem.class,
            RenderSystem.class,
            CameraSystem.class,
            NpcSystem.class
        ));
    }
}
