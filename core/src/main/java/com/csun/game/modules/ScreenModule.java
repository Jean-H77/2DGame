package com.csun.game.modules;

import com.badlogic.gdx.Screen;
import com.csun.game.screens.GameScreen;
import com.csun.game.screens.TitleScreen;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

public class ScreenModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(Screen.class)
            .annotatedWith(Names.named("GameScreen"))
            .to(GameScreen.class)
            .in(Scopes.SINGLETON);

        bind(Screen.class)
            .annotatedWith(Names.named("TitleScreen"))
            .to(TitleScreen.class)
            .in(Scopes.NO_SCOPE);
    }
}
