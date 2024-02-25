package com.csun.game.dialogue;

import com.csun.game.attributes.Attributes;
import com.csun.game.dialogue.impl.DialogueOptions;
import com.csun.game.dialogue.impl.DialogueStatement;
import com.csun.game.map.GameMap;
import com.csun.game.models.LightType;
import com.csun.game.player.Player;

public class TestDialogue {

    public static Dialogue get() {
        return new Dialogue.Builder()
            .addOptions(new DialogueOptions(
                Option.Create("[SCARLET]Toggle Light", (player) -> {
                    if (!player.getAttributes().hasKey(Attributes.PLAYER_LIGHT)) {
                        player.enableLight(LightType.CONE_LIGHT, 200);
                    }
                }, new DialogueStatement() {
                    @Override
                    public String getText(Player player) {
                        return "I clicked toggle light";
                    }
                }),
                Option.Create("[SCARLET]Dim area", (player) -> GameMap.INSTANCE.setAmbientLight(0.3f),
                    new DialogueStatement() {
                        @Override
                        public String getText(Player player) {
                            return "I clicked dim area";
                        }
                    }),
                Option.Create("[SCARLET]Lighten area", (player) -> GameMap.INSTANCE.setAmbientLight(1.0f),
                    new DialogueStatement() {
                        @Override
                        public String getText(Player player) {
                            return "I clicked lighten area";
                        }
                    })
            ))
            .addStatement(new DialogueStatement() {
                @Override
                public String getText(Player player) {
                    return "This is the next dialogue";
                }
            })
            .addStatement(new DialogueStatement() {
                @Override
                public String getText(Player player) {
                    return "This is the next next dialogue coming after the previous dialogue, The next dialogue is going to be an option dialogue";
                }
            })
            .addOptions(new DialogueOptions(
                Option.Create("This is the option dialogue which will increase player light radius", (player) -> player.enableLight(LightType.CONE_LIGHT, 500),
                    new DialogueOptions(
                        Option.Create("Change light to point light", (player) -> player.enableLight(LightType.POINT_LIGHT, 500), null),
                        Option.Create("Exit dialogue", (player) -> player.getAttributes().get(Attributes.CURRENT_DIALOGUE).removeDialogue(player), null)
                    )),
                Option.Create("This option dialogue will just exit your dialogue", (player) -> player.getAttributes().get(Attributes.CURRENT_DIALOGUE).removeDialogue(player), null)
            ))
            .build();
    }
}
