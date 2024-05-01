package com.csun.game.dialogue;

public record Option(String title, Command command, DialoguePart next) {
    public static Option create(String title, Command command, DialoguePart next) {
        return new Option(title, command, next);
    }

    public static Option create(String title) {
        return new Option(title, null, null);
    }

    // so here you can execute a Command
    public static Option create(String title, Command command) {
        return new Option(title, command, null);
    }

    // here you can make it do a next dialogue
    public static Option create(String title, DialoguePart next) {
        return new Option(title, null, next);
    }

}
