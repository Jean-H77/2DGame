package com.csun.game.quest.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.csun.game.MainGame;
import com.csun.game.dialogue.Dialogue;
import com.csun.game.dialogue.Option;
import com.csun.game.dialogue.impl.DialogueOptions;
import com.csun.game.dialogue.impl.DialogueStatement;
import com.csun.game.misc.MutableInt;
import com.csun.game.player.Player;
import com.csun.game.quest.Quest;
import com.google.inject.Inject;

import java.util.Objects;

public class TestQuest extends Quest {

    @Inject
    public TestQuest(String title, MainGame mainGame) {
        super(title, mainGame);
    }

    @Override
    public void clickNpc(Player player, String npc, float mouseX, float mouseY) {
        MutableInt stage = player.getQuestStage(title);
        if(npc.equalsIgnoreCase("Shob")) {
            player.startDialogue(new Dialogue.Builder()
                .addStatement(new DialogueStatement() {
                    @Override
                    public String getText(Player player) {
                        return "Hello my name is shob, you're on quest stage: " + stage.getValue();
                    }
                })
                .build());
        } else if(npc.equalsIgnoreCase("Knob")) {
            Dialogue.Builder builder = new Dialogue.Builder();
            if(stage.getValue() > 1) {
                builder.addStatement(new DialogueStatement() {
                    @Override
                    public String getText(Player player) {
                        return "You already had your screen faded!";
                    }
                });
            } else {
                Option fadeScreenOption = Option.create("Fade screen", (p) -> fadeScreen());
                Option exitOption = Option.create("Exit");
                builder.addOptions(new DialogueOptions(fadeScreenOption, exitOption));
            }
            player.startDialogue(builder.build());
        } else if(npc.equalsIgnoreCase("Blob")) {
            Gdx.app.log("I'm here now?", "ok");
            player.startDialogue(new Dialogue.Builder()
                    .addOptions(new DialogueOptions(
                        Option.create("Remove npc", (p) -> Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                removeNpc(npc);
                            }
                        }, 3)),
                        Option.create("Exit")
                    ))
                .build());
        }
    }

    @Override
    public void hoverOverNpc(Player player, String npc, float mouseX, float mouseY) {
        super.hoverOverNpc(player, npc, mouseX, mouseY);
    }

    @Override
    public void enterMap(Player player, String mapName) {
        int stage = player.getQuestStage(title).getValue();
        if(Objects.equals(mapName, "act1.tmx")) {
            addNpc(new Vector2(477, 400), 16, 16, "Blob");
            addNpc(new Vector2(400, 400), 16, 16, "Knob");
            addNpc(new Vector2(480, 547), 16, 16, "Shob");
        }
    }
}
