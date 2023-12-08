package com.csun.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.csun.game.models.Systems;
import com.csun.game.modules.GameModule;
import com.csun.game.modules.PlayerModule;
import com.csun.game.modules.ScreenModule;
import com.csun.game.screens.GameScreen;
import com.google.inject.Guice;
import com.google.inject.Injector;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {

    @Override
    public void create() {
        Injector injector = Guice.createInjector(
            new GameModule(this),
            new PlayerModule(),
            new ScreenModule()
        );

        PooledEngine engine = injector.getInstance(PooledEngine.class);

        injector.getInstance(Systems.class)
            .list()
            .forEach(it -> engine.addSystem(injector.getInstance(it)));

        setScreen(injector.getInstance(GameScreen.class));
    }
}
