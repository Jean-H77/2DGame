package com.csun.game.quest.impl;

import com.badlogic.gdx.utils.Timer;
import com.csun.game.MainGame;
import com.csun.game.misc.MutableInt;
import com.csun.game.npc.NpcManager;
import com.csun.game.player.Player;
import com.csun.game.quest.Quest;
import com.google.inject.Inject;

public class TestQuest extends Quest {

    @Inject
    public TestQuest(String title, MainGame mainGame, NpcManager npcManager) {
        super(title, mainGame, npcManager);
    }

    @Override
    public void clickNpc(Player player, String name, String id, float mouseX, float mouseY) {
        MutableInt stage = player.getQuestStage(title);
    }

    @Override
    public void hoverOverNpc(Player player, String npc, float mouseX, float mouseY) {
        super.hoverOverNpc(player, npc, mouseX, mouseY);
    }

    @Override
    public void enterMap(Player player, String mapName) {
    }

}
