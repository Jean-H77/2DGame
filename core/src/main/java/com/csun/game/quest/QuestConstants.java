package com.csun.game.quest;

import com.csun.game.quest.impl.TestQuest;
import com.google.common.collect.ImmutableMap;

public final class QuestConstants {

    private QuestConstants() {

    }

    public static final ImmutableMap<String, Quest> QUESTS =
        ImmutableMap.<String, Quest>builder()
        .put("test quest", new TestQuest("Test quest"))
        .build();

}
