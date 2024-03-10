package com.csun.game.quest;

import com.badlogic.gdx.math.Vector2;
import com.csun.game.MainGame;
import com.csun.game.action.ActionAdapter;
import com.csun.game.npc.Npc;
import com.csun.game.npc.NpcManager;
import com.google.inject.Inject;

public class Quest extends ActionAdapter {

    protected final String title;
    private final MainGame mainGame;
    private final NpcManager npcManager;

    @Inject
    public Quest(String title, MainGame mainGame, NpcManager npcManager) {
        this.title = title;
        this.mainGame = mainGame;
        this.npcManager = npcManager;
    }

    public void fadeScreen() {
        mainGame.fadeGameScreen();
    }

    public void fadeScreen(String text) {
        mainGame.fadeGameScreen(text);
    }

    protected void addNpc(String id, Vector2 pos, Npc npc) {
        npcManager.add(id, pos, npc);
    }

    protected void removeNpc(String identification) {
        npcManager.remove(identification);
    }

    protected void moveNpc(String identification, Vector2 newPos) {
        npcManager.forceMove(identification, newPos);
    }

    protected boolean containsNpc(String identification) {
        return npcManager.get(identification) != null;
    }

    protected void walkNpc(String identification, float tileX, float tileY) {
        npcManager.walkTo(identification, tileX, tileY);
    }

    protected void npcSay(String statement, String identification, int seconds) {
        npcManager.speak(statement, identification, seconds);
    }

    protected Vector2 getNpcPosition(String identification) {
        return npcManager.getPosition(identification);
    }
}
