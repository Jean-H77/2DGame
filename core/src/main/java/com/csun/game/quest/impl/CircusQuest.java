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

public class CircusQuest extends Quest {

    @Inject
    public CircusQuest(String title, MainGame mainGame, NpcManager npcManager) {
        super(title, mainGame, npcManager);
    }

    @Override
    public void clickNpc(Player player, String name, String id, float mouseX, float mouseY) {
        MutableInt stage = player.getQuestStage(title);
        switch (id) {
            case "npc77705":
                if (stage.getValue() > 1) {
                    if (stage.getValue() == 2) {
                        Dialogue dialogue = new Dialogue.Builder()
                            .addStatement(new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Leave me alone boy I summoning right now!";
                                }
                            })
                            .build();
                        player.startDialogue(dialogue);
                    }
                    if (stage.getValue() == 8) {
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
                    if (stage.getValue() == 7) {
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
                                return "HEHEHEHEHEHEHE The horseman spirits has shed more blood I am almost strong enough...";
                            }
                        })
                        .addOptions(new DialogueOptions(
                            Option.create("Strong enough for....?",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return "Who are you? No matter...little boy I am using the horsemans spirit to collcet enough essence to open the portal through time HAHAHAHHA";
                                    }
                                }),
                            Option.create("What are you doing?",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {

                                        return "Who are you? No matter...little boy I am using the horsemans spirit to collect enough essence to open the portal through time HAHAHAHHA";
                                    }
                                })
                        ))
                        .addOptions(new DialogueOptions(
                            Option.create("Okay and do what?",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        stage.increment();
                                        return "I will go back to when my circus was at its best...filled with laughter and screams...I will take all their essence and we will spend eternity in my glorious circus!";
                                    }
                                }),
                            Option.create("So you can stop the girl from getting her head cut off?",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        stage.increment();
                                        return "Dumb boy. I will kill everyone and we will all spend eternity watching my amazing circus!";
                                    }
                                })
                        ))
                        .build();
                    player.startDialogue(dialogue);

                }
                break;
            case "cailin1":
                if (stage.getValue() > 1) {
                    if (stage.getValue() >= 4) {
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
                                        return "The path to the horseman lies beyond the woods, however they have locked the door and would never let a child out of the towns walls. Bring the three ingredients and I will create a key for you which shall open the door to the woods. ";
                                    }
                                }),
                                Option.create(" I mean I guess... if i must...", new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return " The path to the horseman lies beyond the woods, however they have locked the door and would never let a child out of the towns walls. Bring the three ingredients and I will create a key for you which shall open the door to the woods. The first ingredient is a simple key... this may be found anywhere.. I may even have one in my storage room. The second ingredient is a piece of bone from a righteous warrior. The final ingredient is a tear drop from a thief. Go on and find these pieces and bring them back to me...";
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
                                return "Did you find the stuff?";
                            }
                        })
                        .addOptions(new DialogueOptions(
                            Option.create("Yes!",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        //check if stuff is found
                                        stage.increment();
                                        return " Well...it seems my magic is a little rusty. I can't make a key but here is a bomb to blow up the door! And don't forget to say goodbye to your mom.";
                                    }

                                }),
                            Option.create("No, can you remind me what I am looking for?",
                                new DialogueStatement(name) {
                                    @Override
                                    public String getText(Player player) {
                                        return "The first ingredient is a simple key... this may be found anywhere.. I may even have one in my storage room. The second ingredient is a piece of bone from a righteous warrior. The final ingredient is a tear drop from a thief. Go on and find these pieces and bring them back to me...";
                                    }

                                })
                        ))
                        .build();
                    player.startDialogue(dialogue);
                }
                if (stage.getValue() == 1 || stage.getValue() == 2 || stage.getValue() == 3 || stage.getValue() == 4) {
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
            case "npc11111":
                if (stage.getValue() == 1 || stage.getValue() == 2 || stage.getValue() == 3) {
                    Dialogue dialogue = new Dialogue.Builder()
                        .addStatement(new DialogueStatement(name) {
                            @Override
                            public String getText(Player player) {
                                return "Ah, the endless carousel of laughter and spectacle... forever spinning, yet I stand frozen in time, a silent spectator to the eternal show.";
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

            case "npc77704":
                Dialogue dialogue = new Dialogue.Builder()
                    .addStatement(new DialogueStatement(name) {
                        @Override
                        public String getText(Player player) {
                            return "That poor girl...I may be dead but at least I have a head.";
                        }
                    })
                    .addOptions(new DialogueOptions(
                        Option.create("Have you seen the horseman?",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "Well I saw his head get chopped off and than my tent collasped and I woke up dead and ghostly. However, I saw the ringmaster in the tent and he seemed strange...I mean he was standing next to a weird portal but I did not care to ask about it.";
                                }
                            }),
                        Option.create("What happened to her? Where is her head?",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "We were watching the show and as the horseman juggled swords a firework went off in the background...the horse got spooked and bucked. The horseman lost grip of his sword and it flew into the crowd and cut off her head...I tried to stop the mob as it seemed to be an accident but I was too late...";
                                }
                            })
                    ))
                    .build();
                player.startDialogue(dialogue);
                break;
            case "npc77701":
                dialogue = new Dialogue.Builder()
                    .addStatement(new DialogueStatement(name) {
                        @Override
                        public String getText(Player player) {
                            return "...";
                        }
                    })
                    .addOptions(new DialogueOptions(
                        Option.create("What happened to you?",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return ".......";
                                }
                            }),
                        Option.create("Where is your head?",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return ".....";
                                }
                            })
                    ))
                    .build();
                player.startDialogue(dialogue);
                break;
            case "npc77702":
                dialogue = new Dialogue.Builder()
                    .addStatement(new DialogueStatement(name) {
                        @Override
                        public String getText(Player player) {
                            return "Ah, the endless carousel of laughter and spectacle... forever spinning, yet I stand frozen in time, a silent spectator to the eternal show.";
                        }
                    })
                    .addOptions(new DialogueOptions(
                        Option.create("That is really sad. What happened to you?",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "I came to the circus to enjoy a night of wonders with my family. They had a new show...a horseman that juggles swords...whoopy do until a little girl gets her head cut off and an angry mob chases after him ready for payback and tramples you.";
                                }
                            }),
                        Option.create("Have you seen the horseman?",
                            new DialogueStatement(name) {
                                @Override
                                public String getText(Player player) {
                                    return "That monster! I have not seen him since the mob trampled me trying to cut off his head. Now all time does is loop...that stupid clock never moves...";
                                }
                            })
                    ))
                    .build();
                player.startDialogue(dialogue);
                break;

        }
    }

    @Override
    public void enterMap(Player player, String mapName) {
        super.enterMap(player, mapName);
    }
}

