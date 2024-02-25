package com.csun.game.UI;

import com.badlogic.gdx.Gdx;
import com.csun.game.attributes.Attributes;
import com.csun.game.player.Player;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class UIManager {

    public static final HashMap<String, BaseUI> uiMap = new HashMap<>();

    public static void openUI(Player player, String name) {
        Gdx.app.log("UI", "Opening " + name);
        BaseUI ui = uiMap.get(name);
        ui.setup(player);
        List<BaseUI> openedUi = player.getAttributes().get(Attributes.ALL_OPENED_UI);
        openedUi.add(ui);
    }

    public static void removeUI(Player player, String name) {
        List<BaseUI> openedUi = player.getAttributes().get(Attributes.ALL_OPENED_UI);
        if (openedUi.contains(uiMap.get(name))) {
            Gdx.app.log("UI", "Removing " + name);
            BaseUI ui = uiMap.get(name);
            ui.exit();
            openedUi.remove(ui);
        }
    }

    public static void scan() {
        Gdx.app.getApplicationLogger().log("Ui", "Starting UI scan");
        String pkg = "com.csun.game.UI.impl";

        try (ScanResult scanResult =
                 new ClassGraph()
                     .enableAllInfo()
                     .acceptPackages(pkg)
                     .scan()) {

            for (ClassInfo subclassInfo : scanResult.getSubclasses("com.csun.game.UI.BaseUI").directOnly()) {
                Class<?> subclass = subclassInfo.loadClass();
                try {
                    Object instance = subclass.getDeclaredConstructor().newInstance();
                    String key = subclass.getName().substring(subclass.getName().lastIndexOf(".") + 1);
                    uiMap.put(key, (BaseUI) instance);
                    Gdx.app.getApplicationLogger().log("Ui", "Created " + subclass + " key: " + key);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    Gdx.app.getApplicationLogger().log("UI", "Unable to create UI " + subclass, e);
                }
            }
        }
        Gdx.app.getApplicationLogger().log("Ui", "Ending UI scan");
    }
}
