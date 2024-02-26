package com.csun.game.action;

import com.csun.game.player.Player;

public interface ActionListener {

    void hoverOverNpc(Player player, String npc, float mouseX, float mouseY);

    void clickNpc(Player player, String npc, float mouseX, float mouseY);

    void enterMap(Player player, String mapName);
}
