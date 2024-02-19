package com.csun.game.ashley;

import com.badlogic.ashley.core.ComponentMapper;
import com.csun.game.ashley.components.AnimationComponent;
import com.csun.game.ashley.components.CameraComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.TextureComponent;

public final class Mappers {

    private Mappers() {}

    public static final ComponentMapper<AnimationComponent> animations = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<TextureComponent> textures = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<MovementComponent> movements = ComponentMapper.getFor(MovementComponent.class);
    public static final  ComponentMapper<CameraComponent> cameras = ComponentMapper.getFor(CameraComponent.class);

}
