package com.csun.game.dialogue;

import java.util.ArrayList;
import java.util.List;

public class DialogueChain {
    private final DialoguePart[] parts;
    private DialoguePart current;
    private EndDialogueEvent endDialogueEvent;
    int step;

    public DialogueChain(Builder builder) {
        this.parts = builder.parts.toArray(new DialoguePart[0]);
        this.endDialogueEvent = builder.endDialogueEvent;
    }

    public DialogueChain(DialoguePart[] parts, EndDialogueEvent endDialogueEvent) {
        this.parts = parts;
        this.endDialogueEvent = endDialogueEvent;
    }

    public DialogueChain(DialoguePart[] parts) {
        this.parts = parts;
    }

    public void next() {
        if(step == parts.length-1) {
            if(endDialogueEvent != null) endDialogueEvent.execute();
            return;
        }

        current = parts[step++];
    }

    public void handleKeyCode(int keyCode) {
        if (current == null) return;

        if(current instanceof ChatDialogue chat) {

            return;
        }

        if (!(current instanceof OptionsDialogue opt)) return;

        int selected = getOptionSelected(keyCode);
        Option[] options = opt.options();

        if (selected >= options.length) return;

        options[selected].event().execute();
    }

    //@Todo
    private static int getOptionSelected(int keycode) {
        return switch (keycode) {
            default -> -1;
        };
    }

    public static class Builder {
        private final List<DialoguePart> parts = new ArrayList<>();
        private EndDialogueEvent endDialogueEvent;

        public Builder addChat(String... lines) {
            parts.add(new ChatDialogue(lines));
            return this;
        }

        public Builder addOptions(String title, Option... options) {
            parts.add(new OptionsDialogue(title, options));
            return this;
        }

        public Builder addEndDialogueEvent(EndDialogueEvent endDialogueEvent) {
            this.endDialogueEvent = endDialogueEvent;
            return this;
        }

        public DialogueChain build() {
            return new DialogueChain(this);
        }
    }
}
