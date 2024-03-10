package com.csun.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.csun.game.models.Systems;
import com.csun.game.player.Player;
import com.csun.game.screens.FadeScreen;
import com.csun.game.screens.GameScreen;
import com.csun.game.screens.MenuScreen;
import com.csun.game.screens.TitleScreen;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.concurrent.CompletableFuture;

public class MainGame extends Game {

    private final Injector injector;
    private PooledEngine pooledEngine;

    @Inject
    public MainGame(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void create() {
        Gdx.graphics.setUndecorated(true);
        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setWindowedMode(displayMode.width+1, displayMode.height+1);

        PooledEngine engine = injector.getInstance(PooledEngine.class);
        this.pooledEngine = engine;

        injector.getInstance(Systems.class)
            .list()
            .forEach(it -> engine.addSystem(injector.getInstance(it)));

        switchScreen(ScreenType.TITLE_SCREEN);
    }

    public void switchScreen(ScreenType screenType) {
        switch (screenType) {
            case GAME_SCREEN -> setScreen(injector.getInstance(GameScreen.class));
            case MENU_SCREEN -> setScreen(injector.getInstance(MenuScreen.class));
            case TITLE_SCREEN -> setScreen(injector.getInstance(TitleScreen.class));
        }
    }

    public CompletableFuture<Void> loadPlayer() {
        return CompletableFuture.runAsync(() -> {
            Player player = injector.getInstance(Player.class);
            player.setDefaults()
                .load()
                .thenRun(() -> Gdx.app.postRunnable(player::postLoad));
        });
    }


    public enum ScreenType {
        GAME_SCREEN,
        TITLE_SCREEN,
        MENU_SCREEN
        ;
    }

    public void fadeGameScreen() {
        GameScreen gameScreen = injector.getInstance(GameScreen.class);
        FadeScreen.fade(gameScreen, gameScreen, this);
    }

    public void fadeGameScreen(String text) {
        GameScreen gameScreen = injector.getInstance(GameScreen.class);
        FadeScreen.fade(gameScreen, gameScreen, this, text);
    }

    public void fadeScreen(String text, Screen to, Screen from) {
        FadeScreen.fade(from, to, this, text);
    }

    public PooledEngine getPooledEngine() {
        return pooledEngine;
    }

    public Injector getInjector() {
        return injector;
    }
}
