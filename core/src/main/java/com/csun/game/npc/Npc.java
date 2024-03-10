package com.csun.game.npc;


import com.badlogic.ashley.core.Component;
import com.csun.game.ashley.components.AnimationComponent;
import com.csun.game.ashley.components.ClickableComponent;
import com.csun.game.ashley.components.NpcComponent;
import com.csun.game.ashley.components.TransformComponent;
import com.csun.game.models.Animations;

import java.util.HashMap;
import java.util.Map;

import static com.csun.game.models.Animations.*;

public enum Npc {
    MOM("Mom",
        48,
        48,
        MOM_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", MOM_WALK_NORTH, MOM_IDLE_NORTH),
        setAnim("south", MOM_WALK_SOUTH, MOM_IDLE_SOUTH),
        setAnim("east", MOM_WALK_EAST, MOM_IDLE_EAST),
        setAnim("west", MOM_WALK_WEST, MOM_IDLE_WEST)),

    WITCH("Witch",
        48,
        48,
        WITCH_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", WITCH_WALK_NORTH, WITCH_IDLE_NORTH),
        setAnim("south", WITCH_WALK_SOUTH, WITCH_IDLE_SOUTH),
        setAnim("east", WITCH_WALK_EAST, WITCH_IDLE_EAST),
        setAnim("west", WITCH_WALK_WEST, WITCH_IDLE_WEST)),

    DOCTOR("Doctor",
        48,
        48,
        DOCTOR_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", DOCTOR_WALK_NORTH, DOCTOR_IDLE_NORTH),
        setAnim("south", DOCTOR_WALK_SOUTH, DOCTOR_IDLE_SOUTH),
        setAnim("east", DOCTOR_WALK_EAST, DOCTOR_IDLE_EAST),
        setAnim("west", DOCTOR_WALK_WEST, DOCTOR_IDLE_WEST)),

    SKELETON("Skeleton",
        48,
        48,
        SKELETON_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST));


    private final String name;
    private final float width;
    private final float height;
    private Animations.Anim startAnimation;
    private final Component[] components;
    private Map<String, Animations.Anim[]> animations;

    Npc(String name, float width, float height, Component... components) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.components = components;

    }

    Npc(String name, float width, float height, Animations.Anim startAnimation, Component[] components, AnimSetter... animSetters) {
        this(name, width, height, components);
        this.startAnimation = startAnimation;
        this.animations = new HashMap<>();

        for(AnimSetter an : animSetters) {
            animations.put(an.key, an.animations);
        }
    }

    public String getName() {
        return name;
    }

    public Component[] getComponents() {
        return components;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Map<String, Animations.Anim[]> getAnimations() {
        return animations;
    }

    public Anim getStartAnimation() {
        return startAnimation;
    }

    public static Component[] defaultComponents() {
        return new Component[] {
            new TransformComponent(),
            new ClickableComponent(),
            new NpcComponent(),
            new AnimationComponent()
        };
    }

    private static AnimSetter setAnim(String key, Animations.Anim... animations) {
        return new AnimSetter(key, animations);
    }

    private record AnimSetter(String key, Animations.Anim... animations) {

    }
}
