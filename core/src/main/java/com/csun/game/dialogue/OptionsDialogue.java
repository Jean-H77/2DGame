package com.csun.game.dialogue;

public class OptionsDialogue implements DialoguePart {
    private final Option[] options;
    private final String title;

    public OptionsDialogue(Builder builder) {
        this.options = builder.options;
        this.title = builder.title;
    }

    public Option[] getOptions() {
        return options;
    }

    public static class Builder {
        private String title = "Select an option";
        private Option[] options;
        private int index;

        public Builder(int size) {
            this.options = new Option[size];
        }

        public Builder addTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder addOption(Option option) {
            options[index++] = option;
            return this;
        }

        public OptionsDialogue build() {
            return new OptionsDialogue(this);
        }
    }
}
