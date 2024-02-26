package com.csun.game.quest.impl;

import com.badlogic.gdx.Gdx;
import com.csun.game.misc.MutableInt;
import com.csun.game.player.Player;
import com.csun.game.quest.Quest;

public class TestQuest extends Quest {

    public TestQuest(String title) {
        super(title);
    }

    @Override
    public void clickNpc(Player player, String npc, float mouseX, float mouseY) {
        MutableInt stage = player.getQuestStage(title);
        stage.increment();

        Gdx.app.log("Click "+npc, "Clicking inside TestQuest " + stage.getValue());
    }

    @Override
    public void hoverOverNpc(Player player, String npc, float mouseX, float mouseY) {
        super.hoverOverNpc(player, npc, mouseX, mouseY);
    }

    @Override
    public void enterMap(Player player, String mapName) {
        super.enterMap(player, mapName);
    }
}
