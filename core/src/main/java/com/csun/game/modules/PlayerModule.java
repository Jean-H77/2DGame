package com.csun.game.modules;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.csun.game.Player;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.google.inject.*;

public class PlayerModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public Player createPlayer(PooledEngine pooledEngine) {
        Entity entity = pooledEngine.createEntity();
        entity.add(new PlayerComponent());
        entity.add(new TextureComponent());
        entity.add(new MovementComponent());
        pooledEngine.addEntity(entity);
        return new Player(entity);
    }
}
