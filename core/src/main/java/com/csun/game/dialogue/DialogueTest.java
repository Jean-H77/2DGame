package com.csun.game.dialogue;

public class DialogueTest {

    public DialogueChain t() {
        return new DialogueChain.Builder()
            .addChatDialogue("This is npc talking line 1", "This is line 2")
            .addOptionsDialogue("Select an option",
                new Option("Do something1", this::doingSomething),
                new Option("Do something2", this::doingSomething),
                new Option("Do something3", this::doingSomething))
            .addChatDialogue("This is npc talking line 1", "This is line 2")
            .build();
    }

    public void doingSomething() {

    }
}
