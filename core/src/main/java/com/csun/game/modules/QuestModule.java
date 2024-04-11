package com.csun.game.modules;

import com.csun.game.quest.Quest;
import com.csun.game.quest.impl.TestQuest;
import com.csun.game.quest.impl.TestQuestTwo;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

public class QuestModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<String, Quest> questMapBinder = MapBinder.newMapBinder(binder(), String.class, Quest.class);
        questMapBinder.addBinding("TestQuest").to(TestQuest.class);
        questMapBinder.addBinding("TestQuestTwo").to(TestQuestTwo.class);
    }
}
