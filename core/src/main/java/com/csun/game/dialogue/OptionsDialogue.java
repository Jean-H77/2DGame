package com.csun.game.dialogue;

public record OptionsDialogue(String title, Option... options) implements DialoguePart {
}
