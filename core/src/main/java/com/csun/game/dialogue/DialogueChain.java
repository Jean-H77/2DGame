package com.csun.game.dialogue;

public class DialogueChain {

    private final DialoguePart[] parts;
    int step;

    public DialogueChain(Builder builder) {
        this.parts = builder.parts;
    }

    public DialogueChain(DialoguePart[] parts) {
        this.parts = parts;
    }

    public static class Builder {
        private final DialoguePart[] parts;
        private int index;

        public Builder(int size) {
            this.parts = new DialoguePart[size];
        }

        public Builder addChatDialogue(ChatDialogue chatDialogue) {
            parts[index++] = chatDialogue;
            return this;
        }

        public Builder addOptionsDialogue(OptionsDialogue optionsDialogue) {
            parts[index++] = optionsDialogue;
            return this;
        }

        public DialogueChain build() {
            return new DialogueChain(this);
        }
    }
}
