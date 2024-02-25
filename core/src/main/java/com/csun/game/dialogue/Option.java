package com.csun.game.dialogue;

public record Option(String title, Command command) {
    public static Option Create(String title, Command command) {
        return new Option(title, command);
    }
}
