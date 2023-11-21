package com.csun.game.dialogue;

import com.badlogic.gdx.Gdx;

public class DialogueTest {

    public DialogueChain t() {
        return new DialogueChain.Builder()
            .addChat("This is npc talking line 1", "This is line 2")
            .addOptions("Select an option",
                Option.create("Do something1", this::doingSomething),
                Option.create("Do something2", this::doingSomething),
                Option.create("Do something3", this::doingSomething))
            .addChat("This is npc talking line 1", "This is line 2")
            .addEndDialogueEvent(() -> Gdx.app.log("End", "This is the end of the dialogue event running"))
            .build();
    }

    public void doingSomething() {

    }
}
