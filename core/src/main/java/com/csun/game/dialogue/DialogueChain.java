package com.csun.game.dialogue;

import java.util.ArrayList;
import java.util.List;

public class DialogueChain {
    private final DialoguePart[] parts;
    int step;

    public DialogueChain(Builder builder) {
        this.parts = builder.parts.toArray(new DialoguePart[0]);
    }

    public DialogueChain(DialoguePart[] parts) {
        this.parts = parts;
    }

    public static class Builder {
        private final List<DialoguePart> parts = new ArrayList<>();

        public Builder addChatDialogue(String... lines) {
            parts.add(new ChatDialogue(lines));
            return this;
        }

        public Builder addOptionsDialogue(String title, Option... options) {
            parts.add(new OptionsDialogue(title, options));
            return this;
        }

        public DialogueChain build() {
            return new DialogueChain(this);
        }
    }
}
