package com.csun.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.csun.game.interfaces.Interface;
import com.csun.game.interfaces.impl.TestInterface;
import com.csun.game.models.Systems;
import com.csun.game.modules.GameModule;
import com.csun.game.modules.InterfaceModule;
import com.csun.game.modules.PlayerModule;
import com.csun.game.modules.ScreenModule;
import com.csun.game.screens.GameScreen;
import com.csun.game.screens.TestScreen;
import com.csun.game.screens.TitleScreen;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {

    @Override
    public void create() {
        Injector injector = Guice.createInjector(
            new GameModule(this),
            new PlayerModule(),
            new ScreenModule(),
            new InterfaceModule()
        );

        PooledEngine engine = injector.getInstance(PooledEngine.class);

        injector.getInstance(Systems.class)
            .list()
            .forEach(it -> engine.addSystem(injector.getInstance(it)));

        setScreen(injector.getInstance(GameScreen.class));
    }
}
