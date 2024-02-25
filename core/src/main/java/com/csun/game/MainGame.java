package com.csun.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.csun.game.UI.UIManager;
import com.csun.game.models.Systems;
import com.csun.game.player.Player;
import com.csun.game.screens.GameScreen;
import com.csun.game.screens.TitleScreen;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.concurrent.CompletableFuture;

public class MainGame extends Game {

    private final Injector injector;

    @Inject
    public MainGame(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void create() {
        setupInjector();
        UIManager.scan();

       // UIManager.openUI("SettingsUI");
    }

    public void setupInjector() {
        PooledEngine engine = injector.getInstance(PooledEngine.class);

        injector.getInstance(Systems.class)
            .list()
            .forEach(it -> engine.addSystem(injector.getInstance(it)));

        setScreen(injector.getInstance(TitleScreen.class));
    }

    public void switchScreen(ScreenType screenType) {
        switch (screenType) {
            case GAME_SCREEN -> setScreen(injector.getInstance(GameScreen.class));
            case TITLE_SCREEN -> setScreen(injector.getInstance(TitleScreen.class));
        }
    }

    public CompletableFuture<Void> loadGameScreen() {
       return CompletableFuture.runAsync(() -> {
           Player player = injector.getInstance(Player.class);
           player.setDefaults()
               .load()
               .thenRun(() -> Gdx.app.postRunnable(() -> {
                   player.postLoad();
                   switchScreen(ScreenType.GAME_SCREEN);
               }));
       });
    }

    public enum ScreenType {
        GAME_SCREEN,
        TITLE_SCREEN
        ;
    }
}
