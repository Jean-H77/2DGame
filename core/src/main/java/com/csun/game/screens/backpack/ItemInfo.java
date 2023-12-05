package com.csun.game.screens.backpack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csun.game.ashley.components.ItemComponent;

public class ItemInfo {

    String getItemName(ItemComponent.ITEM_TYPES itemNames){
        switch (itemNames){
            case AXE -> {
                return "AXES";
            }
            case KNIFE -> {
                return "KNIFE";
            }
            case HEALTH_POTION -> {
                return "HEALTH POTION";
            }
            case MANA_POTION -> {
                return "MANA POTION";
            }
        }
        return "";
    }
    String getItemInfo(ItemComponent.ITEM_TYPES itemNames){
        switch (itemNames){
            case AXE -> {
                return "Here is a description of the axe functionality.";
            }
            case KNIFE -> {
                return "Here is a description of the knife functionality.";
            }
            case HEALTH_POTION -> {
                return "Here is a description of the health potion functionality.";
            }
            case MANA_POTION -> {
                return "Here is a description of the mana potion functionality.";
            }
        }
        return "Select item to see info";
    }
}
