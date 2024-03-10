package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.csun.game.models.Animations;

import java.util.HashMap;
import java.util.Map;


public class AnimationComponent implements Component, Cloneable {

    public float width;
    public float height;
    public float stateTime;
    public Animation<TextureRegion> textureRegionAnimation;
    public Animations.Anim currentAnimation;

    public AnimationComponent(Animations.Anim animations, float width, float height) {
        set(animations, width, height);
    }

    public AnimationComponent() {
        set(null, 0, 0);
    }

    public void nextAnim(Animations.Anim animations) {
        if(animations == null) {
            return;
        }
        this.currentAnimation = animations;
        textureRegionAnimation = new Animation<>(1f/ animations.length(), animations.atlasRegions());
    }

    public void set(Animations.Anim animations, float width, float height) {
        if(animations == null) {
            return;
        }
        textureRegionAnimation = new Animation<>(1f/ animations.length(), animations.atlasRegions());
        this.width = width;
        this.height = height;
        this.currentAnimation = animations;
    }
}
