package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.csun.game.npc.NpcType;

public class NpcComponent implements Component {
    public String say;
    public float sayTimer;
    public String id;
    public String name;
    public NpcType npcType;
    public Entity following;
}
