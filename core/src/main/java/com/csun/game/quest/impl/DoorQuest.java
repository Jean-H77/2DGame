package com.csun.game.quest.impl;

import box2dLight.RayHandler;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.csun.game.MainGame;
import com.csun.game.action.ActionAdapter;
import com.csun.game.dialogue.Dialogue;
import com.csun.game.dialogue.Option;
import com.csun.game.dialogue.impl.DialogueOptions;
import com.csun.game.dialogue.impl.DialogueStatement;
import com.csun.game.map.GameMap;
import com.csun.game.misc.MutableInt;
import com.csun.game.models.Animations;
import com.csun.game.npc.NpcManager;
import com.csun.game.player.Player;
import com.csun.game.quest.Quest;
import jakarta.inject.Inject;

public class DoorQuest extends Quest {

    private Object gameMap;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private RayHandler rayHandler;

    @Inject
    public DoorQuest(String title, MainGame mainGame, NpcManager npcManager) {
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
                                    //make doctor move
                                    stage.set(4);
                                }),
                                Option.create("...", (p) -> {
                                    fadeScreen();
                                    //make doctor move
                                    stage.set(4);
                                })))
                            .build();
                        player.startDialogue(dialogue);
                    }
                }
                if (stage.getValue() == 2) {
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
                                //make doctor move
                                stage.set(3);
                            }),
                            Option.create("Thank you...", (p) -> {
                                fadeScreen();
                                //make doctor move
                                stage.set(3);
                            })))
                        .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() == 1) {
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
                    if (stage.getValue() == 2 || stage.getValue() == 3) {
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
                    if (stage.getValue() == 11) {
                        Dialogue dialogue = new Dialogue.Builder()
                            .addStatement(new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Go find the cure! I believe in you!";
                                }
                            })
                            .build();
                        player.startDialogue(dialogue);
                    }
                    if (stage.getValue() == 10) {
                        Dialogue dialogue = new Dialogue.Builder()
                            .addStatement(new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "What did she say?";
                                }
                            })
                            .addOptions(new DialogueOptions(
                                Option.create("She told me I must go alone into the woods to break the curse to cure father...",
                                    new DialogueStatement(name) {
                                        @Override
                                        public String getText(Player player) {
                                            stage.increment();
                                            return " Son you can do this! You are a strong and smart boy just like your father...I love you, you must do this! Please come back soon.";
                                        }
                                    }),
                                Option.create("I am going into the woods to find a cure! I must go alone but I will be back, I promise.",
                                    new DialogueStatement(name) {
                                        @Override
                                        public String getText(Player player) {
                                            stage.increment();
                                            return "I love you son...please be careful. I will watch over your father while you are gone.";
                                        }
                                    })
                            ))
                            .build();
                        player.startDialogue(dialogue);
                    }
                    if (stage.getValue() == 5 || stage.getValue() == 6) {
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
            case "npc111213":
                if (stage.getValue() > 1) {
                    if (stage.getValue() >= 15) {
                        Dialogue dialogue = new Dialogue.Builder()
                            .addStatement(new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Go blow up the door and find the spirit of the woods, he will guide you further!";
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
                                    return "I know what brings you...but are you willing to face the difficult path ahead?";
                                }
                            })
                            .addOptions(new DialogueOptions(
                                Option.create("Yes! I must help my father.",
                                    new DialogueStatement(name) {
                                        @Override
                                        public String getText(Player player) {
                                            return " You will face many trials... your path will be long. dark and dangerous. But you alone must do it. To save your father you must break the curse of the headless horseman...";
                                        }
                                    }),
                                Option.create(" I am scared...",
                                    new DialogueStatement(name) {
                                        @Override
                                        public String getText(Player player) {
                                            return "  You will face many trials... your path will be long. dark and dangerous. But you alone must do it. To save your father you must break the curse of the headless horseman...";
                                        }
                                    })
                            ))
                            .addOptions(new DialogueOptions(
                                Option.create("Who is the headless horseman?", new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return " The horseman is a spirit that is being used by evil forces you will have to find and unveil the truth to dispel the darkness, You are a pure child with a righteous heart, The power of your love is the only thing that can save your father.";
                                    }
                                }),
                                Option.create("How can I do it... I am just a boy...", new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return " The horseman is a spirit that is being used by evil forces you will have to find and unveil the truth to dispel the darkness, You are a pure child with a righteous heart, The power of your love is the only thing that can save your father.";
                                    }
                                })
                            ))
                            .addOptions(new DialogueOptions(
                                Option.create("I will do it! I will save my father!", new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return "The path to the horseman lies beyond the woods, however they have locked the door and would never let a child out of the towns walls. Bring the three ingredients and I will create a key for you which shall open the door to the woods. The first ingredient is a simple key... this may be found anywhere...I may even have one in my storage room.";
                                    }
                                }),
                                Option.create(" I mean I guess... if i must...", new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return " The path to the horseman lies beyond the woods, however they have locked the door and would never let a child out of the towns walls. Bring the three ingredients and I will create a key for you which shall open the door to the woods. The first ingredient is a simple key... this may be found anywhere...I may even have one in my storage room.";
                                    }
                                })))
                            .addOptions(new DialogueOptions(
                                Option.create("Okay, I will find the stuff.", (p) -> {
                                    stage.set(6);
                                }),
                                Option.create("I will be right back!", (p) -> {
                                    stage.set(6);
                                })))
                            .build();
                        player.startDialogue(dialogue);
                    }
                }
                if (stage.getValue() == 6) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                                          @Override
                                          public String getText(Player player) {
                                              return "Bring me the key.";
                                          }
                                      }
                        )
                        .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() == 7) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                stage.increment();
                                return "You found the key! The second ingredient is a piece of bone from a righteous warrior. Go find it!";
                            }
                        }
                        )
                        .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() == 8) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                                          @Override
                                          public String getText(Player player) {
                                              return "You found the key! The second ingredient is a piece of bone from a righteous warrior. Go find it!";
                                          }
                                      }
                        )
                        .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() == 10) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                                          @Override
                                          public String getText(Player player) {
                                              stage.increment();
                                              return "You found the bone! The final ingredient is a tear drop from a thief. Go find it!";
                                          }
                                      }
                        )
                        .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() >= 1 && stage.getValue() <= 4) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                return "I am the Moon Maiden Cailin. May the gods guide your path.";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                }
                break;

            case "npc05131":
                if (stage.getValue() == 1 || stage.getValue() == 2 || stage.getValue() == 3) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                return "Howdy! I am Devon.";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() == 4 || stage.getValue() == 5 || stage.getValue() == 6 || stage.getValue() == 7) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                return "Hi I am Devon. How ya doing child?";
                            }
                        })
                        .addOptions(new DialogueOptions(
                            Option.create(" I am sad my father has been hurt...",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return "Well.. while I was out on the road with my wife we had no cares in the world. We were bad people, stealin and fightin any chance we got but god gots a weird way of tellin ya your meant for something more and she became pregnant. ";
                                    }
                                }),
                            Option.create("meh, could be better.",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return "Well.. while I was out on the road with my wife we had no cares in the world. We were bad people, stealin and fightin any chance we got but god gots a weird way of tellin ya your meant for something more and she became pregnant.";
                                    }
                                })
                        ))
                        .addOptions(new DialogueOptions(
                            Option.create("okay...?", new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return " I knew I needed to change my life around and we had heard stories of your small town. I told your mayor I got by as a thief but I will change my ways for my baby.. heck I would do anything for my family... He told me they would throw me down the well if I even thought about stealing from his people...but he gave me a chance and I have been thankful ever since.";
                                }
                            }),
                            Option.create("How'd you get here?", new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return " I knew I needed to change my life around and we had heard stories of your small town. I told your mayor I got by as a thief but I will change my ways for my baby.. heck I would do anything for my family... He told me they would throw me down the well if I even thought about stealing from his people...but he gave me a chance and I have been thankful ever since.";
                                }
                            })
                        ))
                        .addOptions(new DialogueOptions(
                            Option.create("okay...?", new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "What I am trying to say is... life will find a way to get better especially in the worst times... just have hope.";
                                }
                            }),
                            Option.create("How does this have to do with me", new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "What I am trying to say is... life will find a way to get better especially in the worst times... just have hope.";
                                }
                            })))
                        .addOptions(new DialogueOptions(
                            Option.create("Thanks!", new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Your welcome!";
                                }
                            }),
                            Option.create("Bye", new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Bye!.";
                                }
                            })))
                        .build();
                    player.startDialogue(dialogue);
                }
                break;
            case "redchestbone":
                if (stage.getValue() >= 1 && stage.getValue() <= 10) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                return "LOCKED";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() == 13) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                return "Y";
                            }
                        })
                        .addOptions(new DialogueOptions(
                            Option.create(" Do you like it here?",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return " I love it here...I feel so safe. The doctor was telling me about your towns old mayor and how he saved the town from the horseman and built the walls before he died. I visit his grave everyday, he loves pink roses so I often bring him one...";
                                    }
                                }),
                            Option.create("Wow you are from outside the village? You must have seen the whole world.",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return " I say a lot of stuff out on the road...good and bad...but I must say I love it here...I feel so safe. The doctor was telling me about your towns old mayor and how he saved the town from the horseman and built the walls before he died. I visit his grave everyday, he loves pink roses so I often bring him one...";
                                    }
                                })
                        ))
                        .addOptions(new DialogueOptions(
                            Option.create("I am glad you are here!", new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Amen!";
                                }
                            }),
                            Option.create("Bye!", new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Goodbye!";
                                }
                            })
                        ))
                        .build();
                    player.startDialogue(dialogue);
                }
                break;
            case "npc05013":
                if (stage.getValue() == 1 || stage.getValue() == 2 || stage.getValue() == 3) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                return "Hello child. I am Cali.";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() == 4 || stage.getValue() == 5 || stage.getValue() == 6 || stage.getValue() == 7) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                return "Hello child. I am Cali. I am so glad they let us into your town. My husband came from a life of crime but he has changed since our baby came into our life.";
                            }
                        })
                        .addOptions(new DialogueOptions(
                            Option.create(" Do you like it here?",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return " I love it here...I feel so safe. The doctor was telling me about your towns old mayor and how he saved the town from the horseman and built the walls before he died. I visit his grave everyday, he loves pink roses so I often bring him one...";
                                    }
                                }),
                            Option.create("Wow you are from outside the village? You must have seen the whole world.",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return " I say a lot of stuff out on the road...good and bad...but I must say I love it here...I feel so safe. The doctor was telling me about your towns old mayor and how he saved the town from the horseman and built the walls before he died. I visit his grave everyday, he loves pink roses so I often bring him one...";
                                    }
                                })
                        ))
                        .addOptions(new DialogueOptions(
                            Option.create("I am glad you are here!", new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Amen!";
                                }
                            }),
                            Option.create("Bye!", new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Goodbye!";
                                }
                            })
                        ))
                        .build();
                    player.startDialogue(dialogue);
                }
                break;
            case "npc08049":
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                return "I am bored";
                            }
                        })
                        .addOptions(new DialogueOptions(
                            Option.create("Why are you in here?",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return "I tried to break up with her. So she killed me and put me in here.";
                                    }
                                }),
                            Option.create("What are you doing?",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return "Just playing around having fun...dumb kid I am locked in here duh";
                                    }
                                })
                        ))
                        .build();
                    player.startDialogue(dialogue);

                break;

            case "npcZZZZZ1":
                dialogue = new Dialogue.Builder()
                    .addStatement(new DialogueStatement(name) {
                        @Override
                        public String getText(Player player) {
                            return "I need to get out!";
                        }
                    })
                    .addOptions(new DialogueOptions(
                        Option.create("How did you get here?",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "I stole from the mayor and he threw me down here.";
                                }
                            }),
                        Option.create("Okay? so leave.",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "...my head hurts...what were we talking about?";
                                }
                            })
                    ))
                    .build();
                player.startDialogue(dialogue);
                break;
            case "npc66601":
                dialogue = new Dialogue.Builder() // Reusing the existing 'dialogue' variable
                    .addStatement(new DialogueStatement(name) {
                        @Override
                        public String getText(Player player) {
                            return "You got thrown done here too?";
                        }
                    })
                    .addOptions(new DialogueOptions(
                        Option.create("Ummmmm sure...",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Yeah I stole a chicken and flew down in here, it is pretty cool.";
                                }
                            }),
                        Option.create("No. I am looking for a tear.",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "The only water I have seen is that well water. Maybe ask Annette if she has seen any.";
                                }
                            })
                    ))
                    .build();
                player.startDialogue(dialogue);
                break;
            case "npcZZZZZ2":
                dialogue = new Dialogue.Builder() // Reusing the existing 'dialogue' variable
                    .addStatement(new DialogueStatement(name) {
                        @Override
                        public String getText(Player player) {
                            return "There is a puzzle down here...but I can't figure it out";
                        }
                    })
                    .addOptions(new DialogueOptions(
                        Option.create("Have you see a tear drop?",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Leave me alone...I need to think...";
                                }
                            }),
                        Option.create("Okay? Where is it?",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "I am not telling! You can't have my treaure!";
                                }
                            })
                    ))
                    .build();
                player.startDialogue(dialogue);
                break;
            case "npc66602":
                dialogue = new Dialogue.Builder() // Reusing the existing 'dialogue' variable
                    .addStatement(new DialogueStatement(name) {
                        @Override
                        public String getText(Player player) {
                            return "Enjoying the weather today?";
                        }
                    })
                    .addOptions(new DialogueOptions(
                        Option.create("Yeah it is very dry for a well. Have you seen any tears?",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "I haven't seen any water but I once heard a poem that helped me find my way. One skull two skull, red skull blue skull or was that poem about fish...";
                                }
                            }),
                        Option.create("No. I am looking for a tear.",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "I haven't seen any water but I once heard a poem that helped me find my way. One skull two skull, red skull blue skull or was that poem about fish...";
                                }
                            })
                    ))
                    .build();
                player.startDialogue(dialogue);
                break;
            case "npc66603":
                dialogue = new Dialogue.Builder()
                    .addStatement(new DialogueStatement(name) {
                        @Override
                        public String getText(Player player) {
                            return "I get to hang out with my friends forever!";
                        }
                    })
                    .addOptions(new DialogueOptions(
                        Option.create("Thats cool...have you seen any tears?",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "No, but there are some lights in the next room. I tried to light them but they won't stay lit and those other guys won't share their fire.";
                                }
                            }),
                        Option.create("I am looking for a tear.",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Well I would cry if I did not have friends.";
                                }
                            })
                    ))
                    .build();
                player.startDialogue(dialogue);
                break;
            case "redchestkey":
                if (stage.getValue() >= 1 && stage.getValue() <= 5) {
                    dialogue = new Dialogue.Builder()
                            .addStatement(new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "LOCKED";
                                }
                            })
                            .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() == 6) {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                //you can just change the animation of it like
                                setAnimation(id, Animations.REDCHEST_IDLE_EAST);
                                return "YOU GAIN A KEY";
                            }
                        })
                        .addOptions(new DialogueOptions(
                            Option.create("Yes! I got the key!.", (p) -> {
                                stage.set(7);
                            }),
                            Option.create("I must bring it back to the witch.", (p) -> {
                                stage.set(7);
                            })))
                        .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() >= 7) {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                setAnimation(id, Animations.REDCHEST_IDLE_EAST);
                                return "EMPTY";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);}
                break;
            case "torch4":
                if (stage.getValue() == 4) {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                setAnimation(id, Animations.TORCH_IDLE_SOUTH);
                                stage.increment();
                                return "LIT";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                } else {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                setAnimation(id, Animations.TORCH_IDLE_SOUTH);
                                Timer.Task delayTask = new Timer.Task() {
                                    float countdown = 5;

                                    @Override
                                    public void run() {
                                        setAnimation(id, Animations.TORCH_IDLE_NORTH);
                                    }
                                };
                                Timer.Task task = Timer.instance().scheduleTask(delayTask, 5);
                                return "LIT";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                }
                break;
            case "door":
                if (stage.getValue() == 1) {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                stage.increment();
                                return "LOCKED";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                } else {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                setAnimation(id, Animations.DOOR_IDLE_SOUTH);
                                return "YOU UNLOCKED THE DOOR!";
                            }
                        })
                        .addOptions(new DialogueOptions(
                            Option.create("I must go on!.", (p) -> {
                            stage.set(7);
                            player.moveToMap("act2.tmx");
                            }),
                            Option.create("I should stay here...", (p) -> {
                            })))
                        .build();
                    player.startDialogue(dialogue);
                }
                break;
            case "torch3":
                if (stage.getValue() == 3) {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                setAnimation(id, Animations.TORCH_IDLE_SOUTH);
                                stage.increment();
                                return "LIT";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                } else {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                setAnimation(id, Animations.TORCH_IDLE_SOUTH);
                                Timer.Task delayTask = new Timer.Task() {
                                    float countdown = 5;

                                    @Override
                                    public void run() {
                                        setAnimation(id, Animations.TORCH_IDLE_NORTH);
                                    }
                                };
                                Timer.Task task = Timer.instance().scheduleTask(delayTask, 5);
                                return "LIT";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                }
                break;
            case "torch2":
                if (stage.getValue() == 2) {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                setAnimation(id, Animations.TORCH_IDLE_SOUTH);
                                stage.increment();
                                return "LIT";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                } else {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                setAnimation(id, Animations.TORCH_IDLE_SOUTH);
                                Timer.Task delayTask = new Timer.Task() {
                                    float countdown = 5;

                                    @Override
                                    public void run() {
                                        setAnimation(id, Animations.TORCH_IDLE_NORTH);
                                    }
                                };
                                Timer.Task task = Timer.instance().scheduleTask(delayTask, 5);
                                return "LIT";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                }
                break;
                case "redchesttear":
                if (stage.getValue() >= 1 && stage.getValue() <= 5) {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                return "LOCKED";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() == 6) {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                //you can just change the animation of it like
                                setAnimation(id, Animations.REDCHEST_IDLE_EAST);
                                return "YOU GAIN A TEAR";
                            }
                        })
                        .addOptions(new DialogueOptions(
                            Option.create("Yes! I got the tear!.", (p) -> {
                                stage.set(7);
                            }),
                            Option.create("I must bring it back to the witch.", (p) -> {
                                stage.set(7);
                            })))
                        .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() >= 7) {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                setAnimation(id, Animations.REDCHEST_IDLE_EAST);
                                return "EMPTY";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);}
                break;
            case "torch1":
                if (stage.getValue() == 1) {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                setAnimation(id, Animations.TORCH_IDLE_SOUTH);
                                stage.increment();
                                return "LIT";
                            }
                        })
                        .build();
                    player.startDialogue(dialogue);
                } else {
                    dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                setAnimation(id, Animations.TORCH_IDLE_SOUTH);
                                Timer.Task delayTask = new Timer.Task() {
                                    float countdown = 5;

                                    @Override
                                    public void run() {
                                        setAnimation(id, Animations.TORCH_IDLE_NORTH);
                                    }
                                };
                                Timer.Task task = Timer.instance().scheduleTask(delayTask, 5);
                                return "LIT";
                            }
                        })
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
