package com.csun.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.csun.game.models.Systems;
import com.csun.game.modules.GameModule;
import com.csun.game.modules.PlayerModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {

    @Override
    public void create() {
        Injector injector = Guice.createInjector(new GameModule(this), new PlayerModule());
        PooledEngine engine = injector.getInstance(PooledEngine.class);

        injector.getInstance(Systems.class)
            .list()
            .forEach(it -> engine.addSystem(injector.getInstance(it)));

        Gdx.app.log("Systems", "Systems Size: " + engine.getSystems().size());
        setScreen(injector.getInstance(Key.get(Screen.class, Names.named("TitleScreen"))));
    }
}
