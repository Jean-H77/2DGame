package com.csun.game.dialogue.impl;

import com.csun.game.dialogue.DialoguePart;
import com.csun.game.player.Player;

public abstract class DialogueStatement implements DialoguePart {

    public abstract String getText(Player player);
}
