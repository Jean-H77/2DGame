package com.csun.game.dialogue;

public class DialogueTest {

    public DialogueChain t() {
        return new DialogueChain.Builder()
            .addChat("This is npc talking line 1", "This is line 2")
            .addOptions("Select an option",
                Option.create("Do something1", this::doingSomething),
                Option.create("Do something2", this::doingSomething),
                Option.create("Do something3", this::doingSomething))
            .addChat("This is npc talking line 1", "This is line 2")
            .addEndDialogueEvent(this::doingSomething)
            .build();
    }

    public void doingSomething() {

    }
}
