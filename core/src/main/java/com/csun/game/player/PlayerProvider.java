package com.csun.game.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ashley.components.*;
import com.csun.game.models.AnimationType;
import com.google.inject.Inject;
import com.google.inject.Provider;

import static com.csun.game.GameConstants.PLAYER_START_X;
import static com.csun.game.GameConstants.PLAYER_START_Y;

public class PlayerProvider implements Provider<Player> {

    private final PooledEngine pooledEngine;

    @Inject
    public PlayerProvider(PooledEngine pooledEngine) {
        this.pooledEngine = pooledEngine;
    }

    @Override
    public Player get() {
        Entity entity = pooledEngine.createEntity();
        entity.add(new PlayerComponent());
        entity.add(new AnimationComponent(AnimationType.PLAYER_WALK_NORTH, 48, 48));
        entity.add(new MovementComponent(new Vector2(PLAYER_START_X,PLAYER_START_Y)));
        entity.add(new CameraComponent(new OrthographicCamera()));
        pooledEngine.addEntity(entity);
        return new Player(entity);
    }
}
