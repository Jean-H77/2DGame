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
import com.csun.game.npc.Npc;
import com.csun.game.npc.NpcManager;
import com.csun.game.player.Player;
import com.csun.game.quest.Quest;
import com.google.inject.Inject;

import java.util.Objects;

public class TestQuest extends Quest {

    @Inject
    public TestQuest(String title, MainGame mainGame, NpcManager npcManager) {
        super(title, mainGame, npcManager);
    }

    @Override
    public void clickNpc(Player player, String name, String id, float mouseX, float mouseY) {
        MutableInt stage = player.getQuestStage(title);

        if (id.equalsIgnoreCase("gesg3")) {
            switch (stage.getValue()) {
                case 1,2 -> {
                    Dialogue.Builder builder = new Dialogue.Builder();
                    Option walkToDoorOptions = Option.create("Take me to the village doors", (p) -> {
                        npcSay("Ok let's go to the village doors!", id, 1);
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                if (stage.getValue() == 2) {
                                    Vector2 pos = getNpcPosition(id);
                                    if ((int) (pos.x) == 960 && (int) (pos.y) == 864) {
                                        npcSay("Ok we're here!", id, 3);
                                        cancel();
                                        stage.increment();
                                    }
                                } else {
                                    npcSay("Follow me!", id, 2);
                                    walkNpc(id, 960, 864);
                                    stage.increment();
                                }
                            }
                        }, 2, 0.5f);
                    });
                    Option singOption = Option.create("Sing me a song!", (p) -> {
                        stage.set(4);
                        String[] song = new String[] {
                            "In the morning, when the sun rises high",
                            "Golden rays fill the vast, open sky",
                            "Birds take flight, a sight to behold",
                            "Oh, sing, nature's choir, in harmony",
                            "Rustling leaves, waves on the sea",
                            "Dance with the breeze, light and free",
                            "In this wild world, beauty we see",
                            "Beneath the trees, in the forest's heart",
                            "Whispering secrets, where shadows dart",
                            "Sunbeams filter through leaves above",
                            "A tranquil sanctuary, filled with love",
                            "Oh, sing, nature's choir, in harmony",
                            "Rustling leaves, waves on the sea",
                            "Embrace the calm, let your spirit be",
                            "In nature's embrace, we find our key"
                        };
                        Timer.schedule(new Timer.Task() {
                            int i;
                            @Override
                            public void run() {
                                if(i == song.length) {
                                    npcSay("OK i'm done :)", id, 2);
                                    stage.set(1);
                                    cancel();
                                    return;
                                }
                                npcSay(song[i], id, 2);
                                i++;
                            }
                        }, 1, 3);
                    });

                    Option followOption = Option.create("Follow me!", (p) -> follow(id, player.getEntity()));

                    Option exitOption = Option.create("Exit");
                    builder.addOptions(new DialogueOptions(walkToDoorOptions, singOption, followOption, exitOption));
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
        } else if(id.equalsIgnoreCase("534252gfse")) {
            Dialogue.Builder builder = new Dialogue.Builder()
                .addStatement(new DialogueStatement() {
                    @Override
                    public String getText(Player player) {
                        return "Hey " + name + "! Why are you counting?";
                    }
                })
                .addStatement(new DialogueStatement() {
                    @Override
                    public String getText(Player player) {
                        return "I'm trying to count to " + Integer.MAX_VALUE + " don't mess me up or i'd have to start over";
                    }
                })
                .addStatement(new DialogueStatement() {
                    @Override
                    public String getText(Player player) {
                        return "Ok i'm sorry " + name + " :(";
                    }
                });
            player.startDialogue(builder.build());
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

            addNpc("534252gfse", new Vector2(255, 501), Npc.MOM);
            addNpc("gesg3", new Vector2(608, 460), Npc.DOCTOR);
            addNpc("gesgsg", new Vector2(172, 625), Npc.WITCH);

            //we can also make tasks
            Timer.Task numberCountTask = new Timer.Task() {
                int count;

                @Override
                public void run() {
                    npcSay(String.valueOf(count++), "534252gfse", 1);
                }
            };
            //0 start delay then keep repeating every 1.5 seconds until you call cancel()
            Timer.schedule(numberCountTask, 0, 1.5f);
        }
    }
}
