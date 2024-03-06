package com.csun.game.quest;

import com.badlogic.gdx.math.Vector2;
import com.csun.game.MainGame;
import com.csun.game.action.ActionAdapter;
import com.google.inject.Inject;

public class Quest extends ActionAdapter {

    protected final String title;
    private final MainGame mainGame;

    @Inject
    public Quest(String title, MainGame mainGame) {
        this.title = title;
        this.mainGame = mainGame;
    }

    public void fadeScreen() {
        mainGame.fadeGameScreen();
    }

    protected void addNpc(Vector2 pos, float width, float height, String identification) {
        mainGame.addNpc(pos, width, height, identification);
    }

    protected void removeNpc(String identification) {
        mainGame.removeNpc(identification);
    }

    protected void moveNpc(String identification, Vector2 newPos) {
        mainGame.moveNpc(identification, newPos);
    }
}
