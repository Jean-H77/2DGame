package com.csun.game.dialogue;

public class DialogueTest {

    public DialogueChain t() {
        return new DialogueChain.Builder(3)
            .addChatDialogue(new ChatDialogue(new String[]{
                "This is npc talking line 1",
                "This is line 2"
            }))
            .addOptionsDialogue(
                new OptionsDialogue.Builder(3)
                    .addOption(new Option("Do something", this::doingSomething))
                    .addOption(new Option("Do something 2", this::doingSomething))
                    .addOption(new Option("Do something 3", this::doingSomething))
                    .build()
            )
            .addChatDialogue(new ChatDialogue(new String[]{
                "This is npc talking line 1",
                "This is line 2"
            }))
            .build();
    }

    public void doingSomething() {

    }
}
