package com.csun.game.UI.dialogue;

import com.csun.game.player.Player;

@FunctionalInterface
public interface Command {
    void execute(Player player);
}
