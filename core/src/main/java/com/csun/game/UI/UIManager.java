package com.csun.game.UI;

import com.badlogic.gdx.Gdx;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class UIManager {

    public static HashMap<String, BaseUI> uiMap = new HashMap<>();

    public static void openUI(String name) {
        if (uiMap.containsKey(name)) {
            Gdx.app.log("UI", "Opening " + name);
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
