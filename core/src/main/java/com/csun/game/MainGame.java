package com.csun.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.csun.game.models.Systems;
import com.csun.game.screens.GameScreen;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class MainGame extends Game {

    private final Injector injector;

    @Inject
    public MainGame(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void create() {
        PooledEngine engine = injector.getInstance(PooledEngine.class);

        injector.getInstance(Systems.class)
            .list()
            .forEach(it -> engine.addSystem(injector.getInstance(it)));

        setScreen(injector.getInstance(GameScreen.class));
    }
}
