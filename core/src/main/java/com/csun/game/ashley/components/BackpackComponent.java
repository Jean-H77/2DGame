package com.csun.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;

public class BackpackComponent implements Component {

    public HashMap<ItemComponent.ITEM_TYPES,Integer> itemMap = new HashMap<>();
    public Boolean isOpen;

    public BackpackComponent() {
        itemMap = new HashMap<>();
        itemMap.put(ItemComponent.ITEM_TYPES.AXE,1);
        itemMap.put(ItemComponent.ITEM_TYPES.HEALTH_POTION,2);
        itemMap.put(ItemComponent.ITEM_TYPES.MANA_POTION,4);
        itemMap.put(ItemComponent.ITEM_TYPES.KNIFE,2);
        isOpen = false;
    }


}
