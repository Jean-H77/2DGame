package com.csun.game.quest;

import com.badlogic.gdx.math.Vector2;
import com.csun.game.MainGame;
import com.csun.game.action.ActionAdapter;
import com.csun.game.models.AnimationType;
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

    public void fadeScreen(String text) {
        mainGame.fadeGameScreen(text);
    }

    protected void addNpc(Vector2 pos, float width, float height, String identification, AnimationType animationType) {
        mainGame.addNpc(pos, width, height, identification, animationType);
    }

    protected void removeNpc(String identification) {
        mainGame.removeNpc(identification);
    }

    protected void moveNpc(String identification, Vector2 newPos) {
        mainGame.moveNpc(identification, newPos);
    }

    protected boolean containsNpc(String identification) {
        return mainGame.getNpcEntity(identification) != null;
    }

    protected void walkNpc(String identification, float tileX, float tileY) {
        mainGame.walkNpc(identification, tileX, tileY);
    }

    protected void npcSay(String statement, String identification, int seconds) {
        mainGame.npcSay(statement, identification, seconds);
    }

    protected Vector2 getNpcPosition(String identification) {
        return mainGame.getNpcPosition(identification);
    }
}
