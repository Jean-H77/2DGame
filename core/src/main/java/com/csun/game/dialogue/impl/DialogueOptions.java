package com.csun.game.dialogue.impl;

import com.csun.game.dialogue.DialoguePart;
import com.csun.game.dialogue.Option;

public class DialogueOptions implements DialoguePart {

    private final Option[] options;

    private DialoguePart next;

    public DialogueOptions(Option... options) {
        this.options = options;
    }

    public DialoguePart setNext(DialoguePart dialoguePart) {
        this.next = dialoguePart;
        return this;
    }

    public Option[] getOptions() {
        return options;
    }

    public DialoguePart getNext() {
        return next;
    }
}
