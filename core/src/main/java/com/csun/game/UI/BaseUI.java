package com.csun.game.UI;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.csun.game.player.Player;

import static com.csun.game.GameConstants.VIEWPORT_HEIGHT;
import static com.csun.game.GameConstants.VIEWPORT_WIDTH;


public abstract class BaseUI {

    protected final Stage stage = new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));

    public abstract void setup(Player player);

    public abstract void loop(float deltaTime);

    /**
     * Having this return true means that the player can walk
     * around while the interface is open
     */
    public abstract boolean isWalkable();

    public void render(float deltaTime) {
        stage.getViewport().apply();
        stage.act(deltaTime);
        stage.draw();
    }

    public void exit() {

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
