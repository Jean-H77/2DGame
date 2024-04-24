package com.csun.game.UI.impl.settings;

import java.util.ArrayList;
import java.util.List;

public class Journal {

    private List<QuestInfo> quests; // List to storequests

    public Journal() {
        quests = new ArrayList<>();
        initializeSampleQuests(); // Load some sample quests
    }

    private void initializeSampleQuests() {
        //sample quests for demonstration
        for (int i = 1; i <= 5; i++) {
            QuestInfo quest = new QuestInfo("Quest " + i, "Complete step " + i);
            quest.addObjective("Objective " + i);
            quests.add(quest);
        }
    }

    public void addQuest(QuestInfo quest) {
        quests.add(quest);
    }

    public boolean removeQuest(QuestInfo quest) {
        return quests.remove(quest);
    }

    public List<QuestInfo> getQuests() {
        return quests;
    }

    public QuestInfo getQuest(int index) {
        if (index >= 0 && index < quests.size()) {
            return quests.get(index);
        }
        return null;
    }
}
