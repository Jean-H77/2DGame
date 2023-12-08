package com.csun.game;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class GameConstants {

    private GameConstants() {

    }

    public static final Path PLAYER_SAVE_DIR = Paths.get(System.getProperty("user.home"), "csun");

    public static final String PLAYER_SAVE_FILE_NAME = "h_r_player_save.json";

    public static final String GAME_TITLE = "Hollow's Curse: Pursuit of the headless horseman";

    public static final int TILE_SIZE = 32;

    public static final int VIEWPORT_WIDTH = 720;

    public static final int VIEWPORT_HEIGHT = 480;

    public static final float PLAYER_START_X = 481.16f;

    public static final float PLAYER_START_Y = 415.79f;
}
