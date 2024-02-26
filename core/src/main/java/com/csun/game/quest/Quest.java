package com.csun.game.quest;

import com.csun.game.action.ActionAdapter;

public class Quest extends ActionAdapter {

    protected final String title;

    public Quest(String title) {
        this.title = title;
    }
}
