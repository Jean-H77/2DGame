package com.csun.game.items;

public abstract class ItemContainer {

    protected final int capacity;

    protected final Item[] items;

    protected ItemContainer(int capacity) {
        this.capacity = capacity;
        this.items = new Item[capacity];
    }

    public int getCapacity() {
        return capacity;
    }

    public Item[] getItems() {
        return items;
    }

    // just add additional methods like deleting/adding an item, etc.
}
