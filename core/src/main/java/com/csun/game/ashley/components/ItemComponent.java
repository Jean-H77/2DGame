package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.csun.game.dialogue.DialogueChain;

public class ItemComponent implements Component, Pool.Poolable {

    public enum ITEM_TYPES{
        KNIFE,AXE,HEALTH_POTION,MANA_POTION
    }

    public ITEM_TYPES itemType = ITEM_TYPES.KNIFE;

    @Override
    public void reset() {
        itemType = ITEM_TYPES.KNIFE;

    }
}
