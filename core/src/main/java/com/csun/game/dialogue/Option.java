package com.csun.game.dialogue;

public record Option(String title, Command command, DialoguePart next) {
    public static Option Create(String title, Command command, DialoguePart next) {
        return new Option(title, command, next);
    }
}
