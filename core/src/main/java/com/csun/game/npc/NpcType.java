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

public enum NpcType {
    MOM("Mom",
        48,
        48,
        MOM_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", MOM_WALK_NORTH, MOM_IDLE_NORTH),
        setAnim("south", MOM_WALK_SOUTH, MOM_IDLE_SOUTH),
        setAnim("east", MOM_WALK_EAST, MOM_IDLE_EAST),
        setAnim("west", MOM_WALK_WEST, MOM_IDLE_WEST)),

    ZOMBIE("Austin",
        48,
        48,
        ZOMBIE_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", ZOMBIE_WALK_NORTH, ZOMBIE_IDLE_NORTH),
        setAnim("south", ZOMBIE_WALK_SOUTH, ZOMBIE_IDLE_SOUTH),
        setAnim("east", ZOMBIE_WALK_EAST, ZOMBIE_IDLE_EAST),
        setAnim("west", ZOMBIE_WALK_WEST, ZOMBIE_IDLE_WEST)),

    CAILIN("Cailin",
        48,
        48,
        WITCH_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", WITCH_WALK_NORTH, WITCH_IDLE_NORTH),
        setAnim("south", WITCH_WALK_SOUTH, WITCH_IDLE_SOUTH),
        setAnim("east", WITCH_WALK_EAST, WITCH_IDLE_EAST),
        setAnim("west", WITCH_WALK_WEST, WITCH_IDLE_WEST)),

    WITCH("Witch",
        48,
        48,
        WITCH_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", WITCH_WALK_NORTH, WITCH_IDLE_NORTH),
        setAnim("south", WITCH_WALK_SOUTH, WITCH_IDLE_SOUTH),
        setAnim("east", WITCH_WALK_EAST, WITCH_IDLE_EAST),
        setAnim("west", WITCH_WALK_WEST, WITCH_IDLE_WEST)),

    DOCTOR("Doctor.John",
        48,
        48,
        DOCTOR_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", DOCTOR_WALK_NORTH, DOCTOR_IDLE_NORTH),
        setAnim("south", DOCTOR_WALK_SOUTH, DOCTOR_IDLE_SOUTH),
        setAnim("east", DOCTOR_WALK_EAST, DOCTOR_IDLE_EAST),
        setAnim("west", DOCTOR_WALK_WEST, DOCTOR_IDLE_WEST)),

    WOODLOCK("Woodlock",
        48,
        48,
        SKELETON_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    CALI("CALI",
        48,
        48,
        CALI_IDLE_WEST,
        defaultComponents(),
        setAnim("north", CALI_WALK_NORTH, CALI_IDLE_NORTH),
        setAnim("south", CALI_WALK_SOUTH, CALI_IDLE_SOUTH),
        setAnim("east", CALI_WALK_EAST, CALI_IDLE_EAST),
        setAnim("west", CALI_WALK_WEST, CALI_IDLE_WEST)),

    DEVON("Devon",
        48,
        48,
        DEVON_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north",DEVON_WALK_NORTH, DEVON_IDLE_NORTH),
        setAnim("south", DEVON_WALK_SOUTH, DEVON_IDLE_SOUTH),
        setAnim("east", DEVON_WALK_EAST, DEVON_IDLE_EAST),
        setAnim("west", DEVON_WALK_WEST, DEVON_IDLE_WEST)),

    GHOSTMAN("Fred",
        48,
        48,
        GHOSTMAN_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", GHOSTMAN_WALK_NORTH, GHOSTMAN_IDLE_NORTH),
        setAnim("south", GHOSTMAN_WALK_SOUTH, GHOSTMAN_IDLE_SOUTH),
        setAnim("east", GHOSTMAN_WALK_EAST, GHOSTMAN_IDLE_EAST),
        setAnim("west", GHOSTMAN_WALK_WEST, GHOSTMAN_IDLE_WEST)),

    GHOSTWOMAN("Amy",
        48,
        48,
        GHOSTGIRL_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", GHOSTGIRL_WALK_NORTH, GHOSTGIRL_IDLE_NORTH),
        setAnim("south", GHOSTGIRL_WALK_SOUTH, GHOSTGIRL_IDLE_SOUTH),
        setAnim("east", GHOSTGIRL_WALK_EAST, GHOSTGIRL_IDLE_EAST),
        setAnim("west", GHOSTGIRL_WALK_WEST, GHOSTGIRL_IDLE_WEST)),

    SKELETON1("Aaron",
        48,
        48,
        SKELETON_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    SKELETON2("Annette",
                 48,
                 48,
             SKELETON_IDLE_SOUTH,
              defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    SKELETON3("Arteen",
        48,
        48,
        SKELETON_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    RINGMASTER("RingMaster",
        48,
        48,
        RINGMASTER_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", RINGMASTER_WALK_NORTH, RINGMASTER_IDLE_NORTH),
        setAnim("south", RINGMASTER_WALK_SOUTH, RINGMASTER_IDLE_SOUTH),
        setAnim("east", RINGMASTER_WALK_EAST, RINGMASTER_IDLE_EAST),
        setAnim("west", RINGMASTER_WALK_WEST, RINGMASTER_IDLE_WEST)),

    HEADLESSGHOST("Stephanie",
        48,
        48,
        HEADLESSGHOST_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", HEADLESSGHOST_WALK_NORTH, HEADLESSGHOST_IDLE_NORTH),
        setAnim("south", HEADLESSGHOST_WALK_SOUTH, HEADLESSGHOST_IDLE_SOUTH),
        setAnim("east", HEADLESSGHOST_WALK_EAST, HEADLESSGHOST_IDLE_EAST),
        setAnim("west", HEADLESSGHOST_WALK_WEST, HEADLESSGHOST_IDLE_WEST)),

    HORSEMAN("Horseman",
        96,
        96,
        HORSEMAN_IDLE_NORTH,
        defaultComponents(),
        setAnim("north", HORSEMAN_WALK_NORTH, HORSEMAN_IDLE_NORTH),
        setAnim("south", HORSEMAN_WALK_SOUTH, HORSEMAN_IDLE_SOUTH),
        setAnim("east", HORSEMAN_WALK_EAST, HORSEMAN_IDLE_EAST),
        setAnim("west", HORSEMAN_WALK_WEST, HORSEMAN_IDLE_WEST)),

    MUSICIAN("Willow",
        48,
        48,
        SKELETON_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    FORESTSPIRIT("Premdergast",
        48,
        48,
        SKELETON_IDLE_SOUTH,
        defaultComponents(),
        setAnim("north", SKELETON_WALK_NORTH, SKELETON_IDLE_NORTH),
        setAnim("south", SKELETON_WALK_SOUTH, SKELETON_IDLE_SOUTH),
        setAnim("east", SKELETON_WALK_EAST, SKELETON_IDLE_EAST),
        setAnim("west", SKELETON_WALK_WEST, SKELETON_IDLE_WEST)),

    REDCHEST("chest",
                 48,
                 48,
                 REDCHEST_IDLE_NORTH,
                 defaultComponents(),
    setAnim("north", REDCHEST_WALK_NORTH, REDCHEST_IDLE_NORTH),
    setAnim("south", REDCHEST_WALK_SOUTH, REDCHEST_IDLE_SOUTH),
    setAnim("east", REDCHEST_WALK_EAST, REDCHEST_IDLE_EAST),
    setAnim("west", REDCHEST_WALK_WEST, REDCHEST_IDLE_WEST));





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
