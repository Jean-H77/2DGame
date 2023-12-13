package com.csun.game.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ashley.components.AnimationComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.models.NpcType;
import com.google.inject.Inject;

public class NPCManager {

    private final PooledEngine pooledEngine;

    @Inject
    public NPCManager(PooledEngine pooledEngine) {
        this.pooledEngine = pooledEngine;
    }

    public void addNpc(NpcType npcType, Vector2 position) {
        Entity entity = pooledEngine.createEntity();
        entity.add(npcType.getTextureComponent());
        entity.add(new MovementComponent(position));
        entity.add(new AnimationComponent());
        pooledEngine.addEntity(entity);
    }
}
