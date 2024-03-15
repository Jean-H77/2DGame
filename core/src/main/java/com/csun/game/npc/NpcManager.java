package com.csun.game.npc;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.*;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Map;

public class NpcManager {

    private final Map<String, Npc> NPCs = new HashMap<>();
    private final PooledEngine pooledEngine;

    @Inject
    public NpcManager(PooledEngine pooledEngine) {
        this.pooledEngine = pooledEngine;
    }

    public void put(NpcJson.NpcJsonList NpcJsonList) {
        for(NpcJson npcJson : NpcJsonList.npcJsonList()) {
            NpcType type = NpcType.valueOf(npcJson.getType());
            create(npcJson.getId(), npcJson.getMap(), npcJson.getCoords(), type);
        }
    }


    public void create(String id, String map, Vector2 position, NpcType npcType) {
        if(get(id) != null) {
            return;
        }

        Entity entity = new Entity();
        SizeComponent sizeComponent = new SizeComponent(32, 32);
        entity.add(sizeComponent);

        for(Component c : npcType.getComponents()) {

            if(c instanceof ClickableComponent) {
                ClickableComponent component = new ClickableComponent();
                component.id = id;
                component.bounds =  new Rectangle(position.x, position.y, npcType.getWidth(), npcType.getHeight());
                entity.add(component);
                continue;
            } else if(c instanceof NpcComponent) {
                NpcComponent component = new NpcComponent();
                component.name = npcType.getName();
                component.id = id;
                component.npcType = npcType;
                entity.add(component);
                continue;
            } else if(c instanceof AnimationComponent) {
                AnimationComponent component = new AnimationComponent();
                component.set(npcType.getStartAnimation(), npcType.getWidth(), npcType.getHeight());
                component.width = npcType.getWidth();
                component.height = npcType.getHeight();
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

        NPCs.put(id, new Npc(entity, map, npcType));
    }

    public Npc get(String id) {
        return NPCs.get(id);
    }

    public void spawn(String id) {
        Entity npc = get(id).getEntity();
        if(npc != null) {
            pooledEngine.addEntity(npc);
        }
    }

    public void despawn(String id) {
        Entity npc = get(id).getEntity();
        if(npc != null) {
            pooledEngine.removeEntity(npc);
        }
    }

    public void forceMove(String id, Vector2 pos) {
        Entity npc = get(id).getEntity();
        if(npc == null) {
            return;
        }

        TransformComponent transformComponent = Mappers.transforms.get(npc);
        transformComponent.pos = pos;

        ClickableComponent clickableComponent = Mappers.clickables.get(npc);

        if(clickableComponent != null) {
            clickableComponent.bounds.x = pos.x;
            clickableComponent.bounds.y = pos.y;
        }
    }

    public void walkTo(String id, float x, float y) {
        Entity npc = get(id).getEntity();
        if(npc == null) {
            return;
        }

        TransformComponent transformComponent = Mappers.transforms.get(npc);
        AnimationComponent animationComponent = Mappers.animations.get(npc);

        /*PathResult pathResult = PathFinder.findPath(
                (int) animationComponent.width, (int) animationComponent.height,
            new Vector2(
                transformComponent.pos.x / TILE_SIZE,
                transformComponent.pos.y / TILE_SIZE
            ),
            new Vector2((int)(x/ TILE_SIZE), (int)(y/TILE_SIZE)),
            gameMap);*/
      //  transformComponent.setPath(new ArrayDeque<>(pathResult.waypoints()));
    }

    public void speak(String statement, String id, int seconds) {
        Npc npc = get(id);
        if(npc == null) {
            return;
        }

        NpcComponent npcComponent = Mappers.npcs.get(npc.getEntity());
        npcComponent.say = statement;
        npcComponent.sayTimer = seconds;
    }

    public Vector2 getPosition(String id) {
        Entity npc = get(id).getEntity();
        return npc == null ? null : Mappers.transforms.get(npc).pos;
    }

    public void follow(String id, Entity followEntity) {
        Entity npc = get(id).getEntity();
        if(npc == null || followEntity == null) {
            return;
        }

        NpcComponent npcComponent = Mappers.npcs.get(npc);
        npcComponent.following = followEntity;

        TransformComponent followingTransformComponent = Mappers.transforms.get(followEntity);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(npcComponent.following == null) {
                    cancel();
                    return;
                }

                walkTo(id, followingTransformComponent.pos.x, followingTransformComponent.pos.y);
            }
        }, 0, 0.3f);
    }

    public Map<String, Npc> getNPCs() {
        return NPCs;
    }

    public void clearVisible() {
        ImmutableArray<Entity> entities = pooledEngine.getEntitiesFor(Family.all(NpcComponent.class).get());
        entities.forEach(pooledEngine::removeEntity);
    }

    public void spawnAll(Entity[] entities) {
        for (Entity entity : entities)
            if(!pooledEngine.getEntities().contains(entity, true))
                pooledEngine.addEntity(entity);
    }
}
