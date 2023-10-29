package com.csun.game.models;

import com.badlogic.ashley.core.EntitySystem;

import java.util.List;

public record Systems(List<Class<? extends EntitySystem>> list) {
}
