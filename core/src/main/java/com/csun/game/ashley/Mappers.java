package com.csun.game.ashley;

import com.badlogic.ashley.core.ComponentMapper;
import com.csun.game.ashley.components.*;

public final class Mappers {

    private Mappers() {}

    public static final ComponentMapper<AnimationComponent> animations = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<TextureComponent> textures = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<TransformComponent> movements = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<CameraComponent> cameras = ComponentMapper.getFor(CameraComponent.class);
    public static final ComponentMapper<ClickableComponent> clickables = ComponentMapper.getFor(ClickableComponent.class);
    public static final ComponentMapper<NpcComponent> npcs = ComponentMapper.getFor(NpcComponent.class);
}
