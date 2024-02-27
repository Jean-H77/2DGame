package com.csun.game.modules;

import com.badlogic.gdx.Gdx;
import com.csun.game.UI.BaseUI;
import com.csun.game.UI.UIManager;
import com.csun.game.annotations.UIComponent;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.MapBinder;
import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

public class UIModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<String, BaseUI> mapBinder = MapBinder.newMapBinder(binder(), String.class, BaseUI.class);
        String pkg = "com.csun.game.UI.impl";

        try (ScanResult scanResult =
                 new ClassGraph()
                     .enableAllInfo()
                     .acceptPackages(pkg)
                     .scan()) {

            for (ClassInfo classInfo : scanResult.getClassesWithAnnotation(UIComponent.class).directOnly()) {
                Class<?> clazz = classInfo.loadClass();
                if (BaseUI.class.isAssignableFrom(clazz)) {
                    AnnotationInfo uiComponentAnnotationInfo = classInfo.getAnnotationInfo(UIComponent.class);
                    if (uiComponentAnnotationInfo != null) {
                        Object value = uiComponentAnnotationInfo.getParameterValues().getValue("key");
                        if (value != null) {
                            String key = value.toString();
                            System.out.println("Binding: " + key + " to " + clazz.asSubclass(BaseUI.class));
                            mapBinder.addBinding(key).to(clazz.asSubclass(BaseUI.class));
                        }
                    }
                } else {
                    Gdx.app.log("Warning", "Class " + classInfo.getName() + " is not a subclass of BaseUI");
                }
            }
        }

        bind(UIManager.class).in(Scopes.SINGLETON);
    }
}
