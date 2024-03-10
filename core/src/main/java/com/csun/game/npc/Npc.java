package com.csun.game.npc;


import com.badlogic.ashley.core.Component;
import com.csun.game.ashley.components.AnimationComponent;
import com.csun.game.ashley.components.ClickableComponent;
import com.csun.game.ashley.components.NpcComponent;
import com.csun.game.ashley.components.TransformComponent;
import com.csun.game.models.Animations;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static com.csun.game.models.Animations.*;

public enum Npc {
    MOM("Mom",
        48,
        48,
        MOM_IDLE_NORTH,

        ImmutableMap.<String, Animations.Anim[]>builder().
            put("north", new Animations.Anim[] {
                MOM_WALK_NORTH,
                MOM_IDLE_NORTH
            }).
            put("south", new Animations.Anim[] {
                MOM_WALK_SOUTH,
                MOM_IDLE_SOUTH
            }).
            put("east", new Animations.Anim[] {
                MOM_WALK_EAST,
                SKELETON_IDLE_EAST
            }).
            put("west", new Animations.Anim[] {
                MOM_WALK_WEST,
                MOM_IDLE_WEST
            })
            .build(),

        defaults()
    ),

    WITCH("Witch",
        48,
        48,
        WITCH_IDLE_NORTH,

        ImmutableMap.<String, Animations.Anim[]>builder().
            put("north", new Animations.Anim[] {
                WITCH_WALK_NORTH,
                WITCH_IDLE_NORTH
            }).
            put("south", new Animations.Anim[] {
                WITCH_WALK_SOUTH,
                WITCH_IDLE_SOUTH
            }).
            put("east", new Animations.Anim[] {
                WITCH_WALK_EAST,
                WITCH_IDLE_EAST
            }).
            put("west", new Animations.Anim[] {
                WITCH_WALK_WEST,
                WITCH_IDLE_WEST
            })
            .build(),

        defaults()
    ),

    DOCTOR("Doctor",
        48,
        48,
        DOCTOR_IDLE_NORTH,

        ImmutableMap.<String, Animations.Anim[]>builder().
            put("north", new Animations.Anim[] {
                DOCTOR_WALK_NORTH,
                DOCTOR_IDLE_NORTH
            }).
            put("south", new Animations.Anim[] {
                DOCTOR_WALK_SOUTH,
                DOCTOR_IDLE_SOUTH
            }).
            put("east", new Animations.Anim[] {
                DOCTOR_WALK_EAST,
                DOCTOR_IDLE_EAST
            }).
            put("west", new Animations.Anim[] {
                DOCTOR_WALK_WEST,
                DOCTOR_IDLE_WEST
            })
            .build(),

        defaults()
    ),

    SKELETON("Skeleton",
        48,
        48,
        SKELETON_IDLE_NORTH,

        ImmutableMap.<String, Animations.Anim[]>builder().
            put("north", new Animations.Anim[] {
                SKELETON_WALK_NORTH,
                SKELETON_IDLE_NORTH
            }).
            put("south", new Animations.Anim[] {
                SKELETON_WALK_SOUTH,
                SKELETON_IDLE_SOUTH
            }).
            put("east", new Animations.Anim[] {
                SKELETON_WALK_EAST,
                SKELETON_IDLE_EAST
            }).
            put("west", new Animations.Anim[] {
                SKELETON_WALK_WEST,
                SKELETON_IDLE_WEST
            })
            .build(),

        defaults()
    );


    private final String name;
    private final float width;
    private final float height;
    private Animations.Anim startAnimation;
    private final Component[] components;
    private Map<String, Animations.Anim[]> walkingIdleAnims;

    Npc(String name, float width, float height, Component... components) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.components = components;

    }

    Npc(String name, float width, float height, Animations.Anim startAnimation, Map<String, Animations.Anim[]> walkingIdleAnims, Component... components) {
        this(name, width, height, components);
        this.startAnimation = startAnimation;
        this.walkingIdleAnims = walkingIdleAnims;
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

    public Map<String, Animations.Anim[]> getWalkingIdleAnims() {
        return walkingIdleAnims;
    }

    public Anim getStartAnimation() {
        return startAnimation;
    }

    public static Component[] defaults() {
        return new Component[] {
            new TransformComponent(),
            new ClickableComponent(),
            new NpcComponent(),
            new AnimationComponent()
        };
    }
}
