package com.csun.game.dialogue;

public record Option(String line, DialogueOptionClickEvent event) {
    public static Option create(String line, DialogueOptionClickEvent event) {
        return new Option(line, event);
    }
}
