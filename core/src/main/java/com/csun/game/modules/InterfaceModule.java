package com.csun.game.modules;

import com.csun.game.interfaces.Interface;
import com.csun.game.interfaces.impl.TestInterface;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class InterfaceModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(Interface.class)
            .annotatedWith(Names.named("TestInterface"))
            .to(TestInterface.class);
    }
}
