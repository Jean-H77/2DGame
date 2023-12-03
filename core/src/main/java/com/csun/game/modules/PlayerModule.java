package com.csun.game.modules;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class PlayerModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(OrthographicCamera.class)
            .annotatedWith(Names.named("PlayerCamera"))
            .to(OrthographicCamera.class);
    }
}
