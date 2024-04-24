package com.csun.game.UI.impl.settings;


import java.util.ArrayList;
import java.util.List;

public class QuestInfo {
    private String title;
    private String description;
    private List<String> objectives;
    private List<String> requiredItems;
    private QuestStatus status;

    public QuestInfo(String title, String description) {
        this.title = title;
        this.description = description;
        this.objectives = new ArrayList<>();
        this.requiredItems = new ArrayList<>();
        this.status = QuestStatus.ACTIVE; // Default status
    }

    public void addObjective(String objective) {
        objectives.add(objective);
    }

    public void addRequiredItem(String item) {
        requiredItems.add(item);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getObjectives() {
        return objectives;
    }

    public List<String> getRequiredItems() {
        return requiredItems;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(QuestStatus status) {
        this.status = status;
    }

    public enum QuestStatus {
        ACTIVE, COMPLETED, LOCKED
    }
}
