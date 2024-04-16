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
<<<<<<< Updated upstream
=======
import static com.csun.game.models.Animations.STRANGER_IDLE_SOUTH;
import static com.csun.game.models.Animations.SPIRIT_IDLE_SOUTH;
>>>>>>> Stashed changes

public enum NpcType {
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
<<<<<<< Updated upstream
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST));
=======
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    CALI("CALI",
        48,
        48,
        SKELETON_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    DEVON("Devon",
        48,
        48,
        SKELETON_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    GHOSTMAN("Mohammad",
        48,
        48,
        SKELETON_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    HEADLESSGHOST("Victoria",
        48,
        48,
        SKELETON_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    GHOSTWOMAN("Amber",
        48,
        48,
        SKELETON_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    SKELETON1("Amy",
        48,
        48,
        SKELETON_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    SKELETON2("Calvin",
                 48,
                 48,
             SKELETON_IDLE_NORTH,
              defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    SKELETON3("Stephanie",
        48,
        48,
        SKELETON_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    RINGMASTER("RingMaster",
        48,
        48,
        SKELETON_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    HORSEMAN("Horseman",
        48,
        48,
        SKELETON_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    MUSICIAN("Willow",
        48,
        48,
        SKELETON_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    FORESTSPIRIT("Premdergast",
        48,
        48,
        SKELETON_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    STRANGER("Stranger",
        48,
        48,
        STRANGER_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", STRANGER_WALK_NORTH, STRANGER_IDLE_NORTH),
        setAnim("south", STRANGER_WALK_SOUTH, STRANGER_IDLE_SOUTH),
        setAnim("east", STRANGER_WALK_EAST, STRANGER_IDLE_EAST),
        setAnim("west", STRANGER_WALK_WEST, STRANGER_IDLE_WEST)),

    SPIRIT("Spirit",
        48,
        48,
        SPIRIT_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", SPIRIT_WALK_NORTH, SPIRIT_IDLE_NORTH),
        setAnim("south", SPIRIT_WALK_SOUTH, SPIRIT_IDLE_SOUTH),
        setAnim("east", SPIRIT_WALK_EAST, SPIRIT_IDLE_EAST),
        setAnim("west", SPIRIT_WALK_WEST, SPIRIT_IDLE_WEST));

>>>>>>> Stashed changes


    private final String name;
    private final float width;
    private final float height;
    private Animations.Anim startAnimation;
    private final Component[] components;
    private Map<String, Animations.Anim[]> animations;

    NpcType(String name, float width, float height, Component... components) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.components = components;

    }

    NpcType(String name, float width, float height, Animations.Anim startAnimation, Component[] components, AnimSetter... animSetters) {
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
