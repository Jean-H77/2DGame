package com.csun.game.npc;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ai.paths.PathFinder;
import com.csun.game.ai.paths.PathResult;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.AnimationComponent;
import com.csun.game.ashley.components.ClickableComponent;
import com.csun.game.ashley.components.TransformComponent;
import com.csun.game.ashley.components.NpcComponent;
import com.csun.game.map.GameMap;
import com.google.inject.Inject;

import java.util.*;

import static com.csun.game.GameConstants.TILE_SIZE;

public class NpcManager {

    private final Map<String, Entity> NPCS = new HashMap<>();
    private final PooledEngine pooledEngine;
    private final GameMap gameMap;

    @Inject
    public NpcManager(PooledEngine pooledEngine, GameMap gameMap) {
        this.pooledEngine = pooledEngine;
        this.gameMap = gameMap;
    }

    public void add(String id, Vector2 position, Npc npc) {
        if(get(id) != null) {
            return;
        }

        Entity entity = pooledEngine.createEntity();

        for(Component c : npc.getComponents()) {

            if(c instanceof ClickableComponent) {
                ClickableComponent component = new ClickableComponent();
                component.id = id;
                component.bounds =  new Rectangle(position.x, position.y, npc.getWidth(), npc.getHeight());
                entity.add(component);
                continue;
            } else if(c instanceof NpcComponent) {
                NpcComponent component = new NpcComponent();
                component.name = npc.getName();
                component.id = id;
                component.npc = npc;
                entity.add(component);
                continue;
            } else if(c instanceof AnimationComponent) {
                AnimationComponent component = new AnimationComponent();
                component.set(npc.getStartAnimation(), npc.getWidth(), npc.getHeight());
                component.width = npc.getWidth();
                component.height = npc.getHeight();
                entity.add(component);
                continue;
            } else if(c instanceof TransformComponent) {
                TransformComponent component = new TransformComponent();
                component.pos = position;
                entity.add(component);
                continue;
            }

            entity.add(c);
        }

        pooledEngine.addEntity(entity);
        NPCS.put(id, entity);
    }

    public Entity get(String id) {
        return NPCS.get(id);
    }

    public void remove(String id) {
        Entity npc = get(id);
        if(npc != null) {
            pooledEngine.removeEntity(npc);
        }
    }

    public void forceMove(String id, Vector2 pos) {
        Entity npc = get(id);
        if(npc == null) {
            return;
        }

        TransformComponent transformComponent = Mappers.movements.get(npc);
        transformComponent.pos = pos;

        ClickableComponent clickableComponent = Mappers.clickables.get(npc);

        if(clickableComponent != null) {
            clickableComponent.bounds.x = pos.x;
            clickableComponent.bounds.y = pos.y;
        }
    }

    public void walkTo(String id, float x, float y) {
        Entity npc = get(id);
        if(npc == null) {
            return;
        }

        TransformComponent transformComponent = Mappers.movements.get(npc);

        PathResult pathResult = PathFinder.findPath(
            new Vector2(
                transformComponent.pos.x / TILE_SIZE,
                transformComponent.pos.y / TILE_SIZE
            ),
            new Vector2((int)(x/ TILE_SIZE), (int)(y/TILE_SIZE)),
            gameMap);

        transformComponent.setPath(new ArrayDeque<>(pathResult.waypoints()));
    }

    public void speak(String statement, String id, int seconds) {
        Entity npc = get(id);
        if(npc == null) {
            return;
        }

        NpcComponent npcComponent = Mappers.npcs.get(npc);
        npcComponent.say = statement;
        npcComponent.sayTimer = seconds;
    }

    public Vector2 getPosition(String id) {
        Entity npc = get(id);
        return npc == null ? null : Mappers.movements.get(npc).pos;
    }
}
