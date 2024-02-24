package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.csun.game.ashley.components.NpcComponent;

public class NpcSystem extends IteratingSystem {

    public NpcSystem() {
        super(Family.all(NpcComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {

    }
}
