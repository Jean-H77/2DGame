package com.csun.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.csun.game.attributes.Attributes;
import com.csun.game.models.Systems;
import com.csun.game.player.Player;
import com.csun.game.screens.GameScreen;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.concurrent.ExecutionException;

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
