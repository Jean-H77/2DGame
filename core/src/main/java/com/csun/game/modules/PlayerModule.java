package com.csun.game.modules;

import com.csun.game.player.Player;
import com.csun.game.player.PlayerProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class PlayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Player.class).toProvider(PlayerProvider.class).in(Scopes.SINGLETON);
    }
}
