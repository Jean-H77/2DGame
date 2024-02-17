package com.csun.game.modules;

import box2dLight.RayHandler;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.csun.game.map.GameMap;
import com.csun.game.MainGame;
import com.csun.game.ashley.systems.*;
import com.csun.game.models.Systems;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;

import java.util.List;

public class GameModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(Game.class).to(MainGame.class).in(Scopes.SINGLETON);
        bind(SpriteBatch.class).in(Scopes.SINGLETON);
        bind(ShapeRenderer.class).in(Scopes.SINGLETON);
        bind(BitmapFont.class).in(Scopes.SINGLETON);
        bind(GameMap.class).in(Scopes.SINGLETON);
        bind(PooledEngine.class).in(Scopes.SINGLETON);
    }

    @Provides
    @Singleton
    private RayHandler provideRayHandler() {
        RayHandler rayHandler = new RayHandler(new World(new Vector2(0,0),false));
        rayHandler.setAmbientLight(1.0f);
        return rayHandler;
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
