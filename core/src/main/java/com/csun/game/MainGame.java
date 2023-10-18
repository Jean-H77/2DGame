package com.csun.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;
import com.csun.game.ashley.systems.AnimationSystem;
import com.csun.game.ashley.systems.MovementSystem;
import com.csun.game.ashley.systems.PlayerInputSystem;
import com.csun.game.ashley.systems.RenderSystem;
import com.csun.game.screens.TitleScreen;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {

    private PooledEngine engine;

    @Override
    public void create() {
        engine = new PooledEngine();
        Injector injector = Guice.createInjector(new GameModule(this));

        injector.getInstance(GameModule.Systems.class).list()
            .forEach(it -> engine.addSystem(injector.getInstance(it)));

        TitleScreen titleScreen = (TitleScreen) injector.getInstance(Key.get(Screen.class, Names.named("TitleScreen")));
        setScreen(titleScreen);
    }

    public void createPlayer() {
        Entity entity = engine.createEntity();
        entity.add(new MovementComponent());
        entity.add(new PlayerComponent());
        engine.addEntity(entity);
    }

    public PooledEngine getEngine() {
        return engine;
    }
}
