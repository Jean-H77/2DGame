package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.csun.game.models.AnimationType;


public class AnimationComponent implements Component {

    public float width;
    public float height;
    public float stateTime;
    public Animation<TextureRegion> textureRegionAnimation;
    public AnimationType animationType;

    public AnimationComponent(AnimationType animationType, float width, float height) {
        textureRegionAnimation = new Animation<>(1f/animationType.getLength(), animationType.getAtlasRegions());
        this.width = width;
        this.height = height;
        this.animationType = animationType;
    }

    public void nextAnim(AnimationType animationType) {
        this.animationType = animationType;
        textureRegionAnimation = new Animation<>(1f/animationType.getLength(), animationType.getAtlasRegions());
    }
}
