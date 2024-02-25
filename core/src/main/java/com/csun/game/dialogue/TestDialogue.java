package com.csun.game.dialogue;

import com.badlogic.gdx.Gdx;
import com.csun.game.attributes.Attributes;
import com.csun.game.dialogue.impl.DialogueOptions;
import com.csun.game.dialogue.impl.DialogueStatement;
import com.csun.game.map.GameMap;
import com.csun.game.models.LightType;
import com.csun.game.player.Player;

import java.security.SecureRandom;
import java.util.Random;

public class TestDialogue {

    public static Dialogue get() {
        return new Dialogue.Builder()
            .addOptions(new DialogueOptions(
                Option.create("Toggle Light", (player) -> {
                    if (!player.getAttributes().hasKey(Attributes.PLAYER_LIGHT)) {
                        player.enableLight(LightType.CONE_LIGHT, 200);
                    }
                }, new DialogueStatement() {
                    @Override
                    public String getText(Player player) {
                        return "I clicked toggle light";
                    }
                }),
                Option.create("Dim area", (player) -> GameMap.INSTANCE.setAmbientLight(0.3f),
                    new DialogueStatement() {
                        @Override
                        public String getText(Player player) {
                            return "I clicked dim area";
                        }
                    }),
                Option.create("Lighten area", (player) -> GameMap.INSTANCE.setAmbientLight(1.0f),
                    new DialogueStatement() {
                        @Override
                        public String getText(Player player) {
                            return "I clicked lighten area";
                        }
                    })
            ))
            .addStatement(new DialogueStatement() {
                @Override
                public String getText(Player player) {
                    return "This is the next dialogue";
                }
            })
            .addStatement(new DialogueStatement() {
                @Override
                public String getText(Player player) {
                    return "This is the next next dialogue coming after the previous dialogue, The next dialogue is going to be an option dialogue";
                }
            })
            .addOptions(new DialogueOptions(
                Option.create("This is the option dialogue will increase light radius", (player) -> player.enableLight(LightType.CONE_LIGHT, 500),
                    new DialogueOptions(
                        Option.create("Change light to point light and exit dialogue", (player) -> player.enableLight(LightType.POINT_LIGHT, 500), null),
                        Option.create("Exit dialogue", (player) -> player.getAttributes().get(Attributes.CURRENT_DIALOGUE).removeDialogue(player), null)
                    )),
                Option.create("This option dialogue will just exit your dialogue", (player) -> player.getAttributes().get(Attributes.CURRENT_DIALOGUE).removeDialogue(player), null),
                Option.create("This option will keep the dialogue going", new DialogueStatement() {
                    @Override
                    public String getText(Player player) {
                        return "You choose to keep the dialogue going!";
                    }
                })
            ))
            .addStatement(new DialogueStatement() {
                @Override
                public String getText(Player player) {
                    return "Since you choose to keep the dialogue going, this is the dialogue going, here's a random dice roll using a 100 sided dice: " + new Random().nextInt(1, 100);
                }
            })
            .build();
    }

    public static class TestDialogueGambleGame {
        private int moneyAmount = 1000;
        private final SecureRandom random = new SecureRandom();

        private Dialogue dialogue = new Dialogue.Builder()
            .addStatement(new DialogueStatement() {
                @Override
                public String getText(Player player) {
                    return "Welcome to the dice game!";
                }
            })
            .addStatement(new DialogueStatement() {
                @Override
                public String getText(Player player) {
                    return "Here's how to play: When the dice rolls over 50 then you win money but if it rolls under 50 then you lose money, rolling exactly 50 will result in a tie.";
                }
            })
            .addOptions(new DialogueOptions(
                Option.create("I'd like to play!", playRound()),
                Option.create("I don't like to gamble :(", (player) -> player.getAttributes().get(Attributes.CURRENT_DIALOGUE).removeDialogue(player))
            ))
            .build();


        public Dialogue getDialogue() {
            return dialogue;
        }

        public DialoguePart playRound() {
            int roll = random.nextInt(100)+1;
            Gdx.app.log("Money", moneyAmount + " roll " + roll);
            return hasWonStatement(roll)
                .setNext(new DialogueOptions(
                    Option.create("Play again", (player) -> {
                        Gdx.app.log("Playing", "Playing again!");
                        playRound(); // ok this does not play again :(
                    }),
                    Option.create("Exit",(player) -> player.getAttributes().get(Attributes.CURRENT_DIALOGUE).removeDialogue(player))
                ));
        }

        public DialogueStatement hasWonStatement(int roll) {
            return new DialogueStatement() {
                @Override
                public String getText(Player player1) {
                    boolean hasWon = roll > 50;
                    if(hasWon) {
                        moneyAmount += 50;
                    } else {
                        moneyAmount -= 50;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("You have ")
                        .append(hasWon ? "won!! " : " lost :( ")
                        .append("The host rolled: ")
                        .append(roll)
                        .append(" You have $")
                        .append(moneyAmount)
                        .append(" left.");
                    return stringBuilder.toString();
                }
            };
        }
    }
}
