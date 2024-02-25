package com.csun.game.UI.dialogue.impl;

import com.csun.game.UI.dialogue.DialoguePart;
import com.csun.game.player.Player;

public abstract class DialogueStatement implements DialoguePart {

    private DialoguePart next;

    public abstract String getText(Player player);

    public DialogueStatement(DialoguePart next) {
        this.next = next;
    }

    public DialogueStatement() {
    }

    public DialoguePart getNext() {
        return next;
    }

    public DialoguePart setNext(DialoguePart next) {
        this.next = next;
        return this;
    }
}
