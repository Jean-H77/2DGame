package com.csun.game.quest.impl;

import com.csun.game.MainGame;
import com.csun.game.dialogue.Dialogue;
import com.csun.game.dialogue.Option;
import com.csun.game.dialogue.impl.DialogueOptions;
import com.csun.game.dialogue.impl.DialogueStatement;
import com.csun.game.misc.MutableInt;
import com.csun.game.npc.NpcManager;
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
            case "doctor1":
                if (stage.getValue() > 1) {
                    if (stage.getValue() >= 4) {
                        Dialogue dialogue = new Dialogue.Builder()
                            .addStatement(new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "I am sorry I could not do more for your father...";
                                }
                            })
                            .build();
                        player.startDialogue(dialogue);
                    }
                    if (stage.getValue() == 3) {
                        Dialogue dialogue = new Dialogue.Builder()
                            .addStatement(new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "The wound looks strange...I have stopped the bleeding for now but it looks like an infection I have never seen before....";
                                }
                            })
                            .addOptions(new DialogueOptions(
                                Option.create("So he is going to die...",
                                    new DialogueStatement(name) {
                                        @Override
                                        public String getText(Player player) {
                                            return "  I do not know let us have hope. I am going to get the mayor to lock the door to the woods, I will be back to check on him...I am sorry I could not do more...";
                                        }
                                    }),
                                Option.create("What should we do?",
                                    new DialogueStatement(name) {
                                        @Override
                                        public String getText(Player player) {
                                            return "  There is nothing we can do but wait and see if he pulls through, I am going to get the mayor to lock the door to the woods, I will be back to check on him...I am sorry I could not do more...";
                                        }
                                    })
                            ))
                            .addOptions(new DialogueOptions(
                                Option.create("okay", (p) -> {
                                    fadeScreen();
                                    stage.set(4);
                                }),
                                Option.create("...", (p) -> {
                                    fadeScreen();
                                    stage.set(4);
                                })))
                            .build();
                        player.startDialogue(dialogue);
                    }
                } if (stage.getValue() == 2) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                return "Hello! I am Doctor John, what brings you in, son?";
                            }
                        })
                        .addOptions(new DialogueOptions(
                            Option.create("My father was attacked in the woods and is losing a lot of blood...",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return " I will head right over. Don't worry I will make sure he is okay";
                                    }
                                }),
                            Option.create("Please help, my father has been injured!",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return " I will head right over. Don't worry I will make sure he is okay";
                                    }
                                })
                        ))
                        .addOptions(new DialogueOptions(
                            Option.create("Please hurry!", (p) -> {
                                fadeScreen();
                                stage.set(3);
                            }),
                            Option.create("Thank you...", (p) -> {
                                fadeScreen();
                                stage.set(3);
                            })))
                        .build();
                    player.startDialogue(dialogue);
                } if (stage.getValue() == 1) {
                Dialogue dialogue = new Dialogue.Builder()
                    .addStatement(new DialogueStatement(name) {
                        @Override
                        public String getText(Player player) {
                            return "Hello! I am Doctor John";
                        }
                    })
                    .build();
                player.startDialogue(dialogue);
            }
                break;

            case "mom1":
                if (stage.getValue() > 1) {
                    if (stage.getValue() == 2) {
                        Dialogue dialogue = new Dialogue.Builder()
                            .addStatement(new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "I am scared please talk to the Doctor...";
                                }
                            })
                            .build();
                        player.startDialogue(dialogue);
                    }
                        if (stage.getValue() == 5) {
                            Dialogue dialogue = new Dialogue.Builder()
                                .addStatement(new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return "Find the witch and ask for her help!";
                                    }
                                })
                                .build();
                            player.startDialogue(dialogue);
                        }
                    if (stage.getValue() == 4) {
                        Dialogue dialogue = new Dialogue.Builder()
                            .addStatement(new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "The Doctor can't help us now...please go find the witch maybe she can cure his illness. She is on the far left of the village.";
                                }
                            })
                            .addOptions(new DialogueOptions(
                                Option.create("I am scared...I don't want him to die...",
                                    new DialogueStatement(name) {
                                        @Override
                                        public String getText(Player player) {
                                            return " It will be okay...she can heal his cursed wound.";
                                        }
                                    }),
                                Option.create("What can she do?",
                                    new DialogueStatement(name) {
                                        @Override
                                        public String getText(Player player) {
                                            return "  If the horseman has something to do with this than maybe she can reverse the curse and save your father... we have to try everything we can.";
                                        }
                                    })
                            ))
                            .addOptions(new DialogueOptions(
                                Option.create("I hope she can help him...", (p) -> {
                                    stage.set(5);
                                }),
                                Option.create("I don't know if that is true but we have to try...", (p) -> {
                                    stage.set(5);
                                })))
                            .build();
                        player.startDialogue(dialogue);
                    }
                } else {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                return "Todd your father has been badly injured while out in the woods please go find the doctor...";
                            }
                        })
                        .addOptions(new DialogueOptions(
                            Option.create("Where is the doctor? What should I say?",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return " The red building beyond the corner, his door is always open. Say he was attacked by a man on a horse in the woods. Please hurry!";
                                    }
                                }),
                            Option.create("What happened? Where should I go?",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return " He is mumbling something about a horseman. I am scared he has lost a lot of blood. Go to the building on the corner with the open door. Please hurry!";
                                    }
                                })
                        ))
                        .addOptions(new DialogueOptions(
                            Option.create("Okay!", (p) -> {
                                stage.set(2);
                            }),
                            Option.create("I will be right back!", (p) -> {
                                stage.set(2);
                            })))
                        .build();
                    player.startDialogue(dialogue);

                }
                break;
        }
    }

    @Override
    public void enterMap(Player player, String mapName) {
        super.enterMap(player, mapName);
    }
}


//Option.create("Move to my position in 10 seconds", (p) -> {
//    Timer.Task moveTask = new Timer.Task() {
//        int countDown = 10;

//       @Override
//      public void run() {
//          if (countDown == 0) {
//              Vector2 playerPos = player.getMovementComponent().pos;
//             moveNpc(id, playerPos);
//           cancel();
//     } else {
//       npcSay("I'm moving to you in " + countDown + " seconds", id, 1);
// }

// countDown--;
//   }
// };
// Timer.schedule(moveTask, 0, 1);
