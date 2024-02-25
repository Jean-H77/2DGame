package com.csun.game.dialogue.impl;

import com.csun.game.dialogue.Command;
import com.csun.game.dialogue.DialoguePart;
import com.csun.game.dialogue.Option;
import com.csun.game.player.Player;

public record DialogueOptions(Option... options) implements DialoguePart {

    public void handleSelection(Player player, int selection) {
        if (selection >= options.length) {
            return;
        }

        Command command = options[selection].command();
        command.execute(player);
    }
}
