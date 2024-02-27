package com.csun.game.UI;

import com.csun.game.attributes.Attributes;
import com.csun.game.player.Player;
import com.google.inject.Inject;

import java.util.List;
import java.util.Map;

public record UIManager(Map<String, BaseUI> uiMap) {

    @Inject
    public UIManager {
    }

    public void openUI(Player player, String name) {
        BaseUI ui = uiMap.get(name);
        ui.setup(player);
        List<BaseUI> openedUi = player.getAttributes().get(Attributes.ALL_OPENED_UI);
        openedUi.add(ui);
    }

    public void removeUI(Player player, String name) {
        List<BaseUI> openedUi = player.getAttributes().get(Attributes.ALL_OPENED_UI);
        if (openedUi.contains(uiMap.get(name))) {
            BaseUI ui = uiMap.get(name);
            ui.exit();
            openedUi.remove(ui);
        }
    }
}
