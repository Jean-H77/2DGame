package com.csun.game.dialogue.impl;

import com.csun.game.dialogue.DialoguePart;
import com.csun.game.player.Player;

public abstract class DialogueStatement implements DialoguePart {

    private final String talkerName;

    private DialoguePart next;

    public abstract String getText(Player player);

    public DialogueStatement(DialoguePart next, String talkerName) {
        this.next = next;
        this.talkerName = talkerName;
    }

    public DialogueStatement(String talkerName) {
        this.talkerName = talkerName;
    }

    public String getTalkerName() {
        return talkerName;
    }

    public DialoguePart getNext() {
        return next;
    }

    public DialoguePart setNext(DialoguePart next) {
        this.next = next;
        return this;
    }
}
