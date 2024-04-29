package com.csun.game.quest;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.MainGame;
import com.csun.game.action.ActionAdapter;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.AnimationComponent;
import com.csun.game.models.Animations;
import com.csun.game.npc.Npc;
import com.csun.game.npc.NpcType;
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

    protected void addNpc(String id, String map, Vector2 pos, NpcType npcType) {
        npcManager.create(id, map, pos, npcType);
    }

    protected void despawnNpc(String id) {
        npcManager.despawn(id);
    }

    protected void spawnNpc(String id) {
        npcManager.spawn(id);
    }

    protected void moveNpc(String id, Vector2 newPos) {
        npcManager.forceMove(id, newPos);
    }

    protected boolean containsNpc(String id) {
        return npcManager.get(id) != null;
    }

    protected void walkNpc(String id, float tileX, float tileY) {
        npcManager.walkTo(id, tileX, tileY);
    }

    protected void npcSay(String statement, String id, int seconds) {
        npcManager.speak(statement, id, seconds);
    }

    protected Vector2 getNpcPosition(String id) {
        return npcManager.getPosition(id);
    }

    protected void follow(String id, Entity followEntity) {
        npcManager.follow(id, followEntity);
    }

    protected void setAnimation(String id, Animations.Anim animation) {
        Npc npc = npcManager.get(id);
        if(npc == null) return;
        AnimationComponent ac = Mappers.animations.get(npc.getEntity());
        NpcType type = npc.getNpcType();
        ac.set(animation, type.getWidth(), type.getHeight()); //which one is the open one
    }//i checked i promise east works lol
}
