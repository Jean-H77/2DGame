package com.csun.game.modules;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.csun.game.Player;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Scopes;

public class PlayerModule extends AbstractModule {

    @Provides
    public Player createPlayer(PooledEngine pooledEngine) {
        Entity entity = pooledEngine.createEntity();
        entity.add(new PlayerComponent());
        entity.add(new TextureComponent());
        entity.add(new MovementComponent());
        pooledEngine.addEntity(entity);
        return new Player(entity);
    }
}
