package com.csun.game.dialogue;

import com.csun.game.player.Player;

@FunctionalInterface
public interface Command {
    void execute(Player player);
}
