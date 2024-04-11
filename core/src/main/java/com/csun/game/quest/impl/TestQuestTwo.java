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
import com.csun.game.npc.NpcManager;
import com.csun.game.npc.NpcType;
import com.csun.game.player.Player;
import com.csun.game.quest.Quest;
import jakarta.inject.Inject;

public class TestQuestTwo extends Quest {

    @Inject
    public TestQuestTwo(String title, MainGame mainGame, NpcManager npcManager) {
        super(title, mainGame, npcManager);
    }

    @Override
    public void clickNpc(Player player, String name, String id, float mouseX, float mouseY) {
        MutableInt stage = player.getQuestStage(title);

        switch (id) {
            case "doctor1" -> {
                if(stage.getValue() > 1) {
                    if(stage.getValue() == 3) {
                        Dialogue dialogue = new Dialogue.Builder()
                            .addOptions(new DialogueOptions(
                                Option.create("Move to my position in 10 seconds", (p) -> {
                                    Timer.Task moveTask = new Timer.Task() {
                                        int countDown = 10;
                                        @Override
                                        public void run() {
                                            if(countDown == 0) {
                                                Vector2 playerPos = player.getMovementComponent().pos;
                                                moveNpc(id, playerPos);
                                                cancel();
                                            } else {
                                                npcSay("I'm moving to you in " + countDown + " seconds", id, 1);
                                            }

                                            countDown--;
                                        }
                                    };
                                    Timer.schedule(moveTask, 0, 1);
                                }),
                                Option.create("Do nothing.")
                            )).build();
                        player.startDialogue(dialogue);
                        stage.increment();
                    } else {
                        npcSay("Hey your quest stage is " + stage.getValue(), id, 5);
                        stage.increment();
                    }
                } else {
                    Dialogue dialogue = new Dialogue.Builder().addStatement(
                            new DialogueStatement() {
                                @Override
                                public String getText(Player player) {
                                    return "I'm the doctor!";
                                }
                            }
                        ).addOptions(
                            new DialogueOptions(
                                Option.create("If you click me then your screen will fade", (p) -> fadeScreen()),
                                Option.create("This will increase your quest stage to 2", (p) -> stage.set(2))))
                        .build();

                    player.startDialogue(dialogue);
                }
            }
        }
    }

    @Override
    public void enterMap(Player player, String mapName) {
        super.enterMap(player, mapName);
    }
}
