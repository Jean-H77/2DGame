package com.csun.game.quest.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.csun.game.MainGame;
import com.csun.game.dialogue.Dialogue;
import com.csun.game.dialogue.Option;
import com.csun.game.dialogue.impl.DialogueOptions;
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

        if (npc.equalsIgnoreCase("Knob")) {
            switch (stage.getValue()) {
                case 1,2 -> {
                    Dialogue.Builder builder = new Dialogue.Builder();
                    Option walkToDoorOptions = Option.create("Take me to the village doors", (p) -> {
                        npcSay("Ok let's go to the village doors!", npc, 1);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                if (stage.getValue() == 2) {
                                    Vector2 pos = getNpcPosition(npc);
                                    if ((int) (pos.x) == 960 && (int) (pos.y) == 864) {
                                        npcSay("Ok we're here!", npc, 3);
                                        cancel();
                                        stage.increment();
                                    } else {
                                        Gdx.app.log("Pos", pos.toString());
                                    }
                                } else {
                                    npcSay("Follow me!", npc, 2);
                                    walkNpc(npc, 960, 864);
                                    stage.increment();
                                }
                            }
                        }, 2, 0.5f);
                    });
                    Option exitOption = Option.create("Exit");
                    builder.addOptions(new DialogueOptions(walkToDoorOptions, exitOption));
                    player.startDialogue(builder.build());
                }
                case 3 -> {
                    Dialogue.Builder builder = new Dialogue.Builder();
                    builder.addOptions(new DialogueOptions(
                        Option.create("Take me back to the center of town!", (p) -> {
                            fadeScreen("Going back to town!");
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    player.moveTo(new Vector2(950, 400));
                                    stage.increment();
                                }
                            }, 4f);
                        }),
                        Option.create("No thanks")
                    ));

                    player.startDialogue(builder.build());
                }
            }
        }
    }

    @Override
    public void hoverOverNpc(Player player, String npc, float mouseX, float mouseY) {
        super.hoverOverNpc(player, npc, mouseX, mouseY);
    }

    @Override
    public void enterMap(Player player, String mapName) {
        int stage = player.getQuestStage(title).getValue();
        if (Objects.equals(mapName, "act1.tmx")) {
            addNpc(new Vector2(255, 501), 48, 48, "Blob");
            addNpc(new Vector2(608, 500), 48, 48, "Knob");
            addNpc(new Vector2(172, 625), 48, 48, "Shob");


            //we can also make tasks
            Timer.Task numberCountTask = new Timer.Task() {
                int count;

                @Override
                public void run() {
                    npcSay(String.valueOf(count++), "Blob", 1);
                }
            };
            //0 start delay then keep repeating every 1.5 seconds until you call cancel()
            Timer.schedule(numberCountTask, 0, 1.5f);
        }
    }
}
