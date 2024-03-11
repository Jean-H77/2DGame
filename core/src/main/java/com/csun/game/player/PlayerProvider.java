package com.csun.game.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ashley.components.*;
import com.csun.game.map.GameMap;
import com.csun.game.models.Animations;
import com.csun.game.quest.Quest;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.Map;

import static com.csun.game.GameConstants.PLAYER_START_X;
import static com.csun.game.GameConstants.PLAYER_START_Y;

public class PlayerProvider implements Provider<Player> {

    private final PooledEngine pooledEngine;
    private final GameMap gameMap;
    private final Map<String, Quest> questMap; // I really don't like how I'm doing this

    @Inject
    public PlayerProvider(PooledEngine pooledEngine, GameMap gameMap, Map<String, Quest> questMap) {
        this.pooledEngine = pooledEngine;
        this.gameMap = gameMap;
        this.questMap = questMap;
    }

    @Override
    public Player get() {
        Entity entity = pooledEngine.createEntity();
        entity.add(new PlayerComponent());
        entity.add(new SizeComponent(32,32));
        entity.add(new AnimationComponent(Animations.WITCH_WALK_NORTH, 48, 48));
        entity.add(new TransformComponent(new Vector2(PLAYER_START_X,PLAYER_START_Y)));
        entity.add(new CameraComponent(new OrthographicCamera()));
        pooledEngine.addEntity(entity);
        return new Player(entity, gameMap, questMap);
    }
}
