package com.csun.game.dialogue;

public class OptionsDialogue implements DialoguePart {
    private final Option[] options;
    private final String title;

    public OptionsDialogue(String title, Option... options) {
        this.title = title;
        this.options = options;
    }

    public Option[] getOptions() {
        return options;
    }

    public String getTitle() {
        return title;
    }
}
