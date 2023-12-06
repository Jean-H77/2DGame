package com.csun.game.items;

public class Item {

    private final int id;

    private final int amount;

    public Item(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public Item(int id) {
        this.id = id;
        this.amount = 1;
    }
}
