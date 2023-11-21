package com.csun.game.dialogue;

public class DialogueTest {

    public DialogueChain t() {
        return new DialogueChain.Builder(3)
            .addChatDialogue(new ChatDialogue(
                "This is npc talking line 1",
                "This is line 2"))
            .addOptionsDialogue(
                new OptionsDialogue("Select an option",
                    new Option("Do something1", this::doingSomething),
                    new Option("Do something2", this::doingSomething),
                    new Option("Do something3", this::doingSomething))
            )
            .addChatDialogue(new ChatDialogue(
                "This is npc talking line 1",
                "This is line 2"))
            .build();
    }

    public void doingSomething() {

    }
}
