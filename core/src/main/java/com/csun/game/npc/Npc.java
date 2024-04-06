package com.csun.game.npc;

import com.badlogic.ashley.core.Entity;

public class Npc {
    private final Entity entity;
    private final NpcType npcType;
    private String map;

    public Npc(Entity entity, String map, NpcType npcType) {
        this.entity = entity;
        this.map = map;
        this.npcType = npcType;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public NpcType getNpcType() {
        return npcType;
    }
}
