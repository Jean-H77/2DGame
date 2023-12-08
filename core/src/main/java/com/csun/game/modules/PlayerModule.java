package com.csun.game.modules;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ashley.components.CameraComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.csun.game.player.Player;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import static com.csun.game.GameConstants.PLAYER_START_X;
import static com.csun.game.GameConstants.PLAYER_START_Y;

public class PlayerModule extends AbstractModule {

    @Provides
    @Singleton
    public Player providePlayer(PooledEngine pooledEngine) {
        Entity entity = pooledEngine.createEntity();
        entity.add(new PlayerComponent());
        entity.add(new TextureComponent());
        entity.add(new MovementComponent(new Vector2(PLAYER_START_X,PLAYER_START_Y)));
        entity.add(new CameraComponent(new OrthographicCamera()));
        pooledEngine.addEntity(entity);
        return new Player(entity);
    }
}
