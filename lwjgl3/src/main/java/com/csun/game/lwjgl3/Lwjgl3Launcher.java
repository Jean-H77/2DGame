package com.csun.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.csun.game.MainGame;
import com.csun.game.modules.GameModule;
import com.csun.game.modules.PlayerModule;
import com.csun.game.modules.ScreenModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static com.csun.game.GameConstants.*;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        Injector injector = Guice.createInjector(
            new GameModule(),
            new PlayerModule(),
            new ScreenModule()
        );
        return new Lwjgl3Application(injector.getInstance(MainGame.class), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle(GAME_TITLE);
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor.
        configuration.setForegroundFPS(60);
        configuration.setIdleFPS(60);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
        configuration.setWindowedMode(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        configuration.setResizable(false);
       // configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
