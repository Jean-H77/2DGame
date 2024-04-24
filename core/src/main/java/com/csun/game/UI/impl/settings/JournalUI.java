package com.csun.game.UI.impl.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csun.game.annotations.UIComponent;
import com.csun.game.UI.BaseUI;
import com.csun.game.UI.UIManager;
import com.csun.game.misc.AssetFont;
import com.csun.game.misc.Assets;
import com.csun.game.player.Player;
import com.google.inject.Inject;

@UIComponent(key = "journal")
public class JournalUI extends BaseUI {

    private UIManager uiManager;
    private Journal journal;
    private AssetManager assetManager = Assets.INSTANCE.getManager();
    private TextureAtlas uiTextureAtlas;

    private Table mainTable;
    private ScrollPane questsScrollPane;
    private Table questsListTable;
    private Table questDetailTable;

    @Inject
    public JournalUI(UIManager uiManager, Journal journal) {
        this.uiManager = uiManager;
        this.journal = journal;
    }

    @Override
    public void setup(Player player) {
        uiTextureAtlas = assetManager.get("atlas/ui/ui.atlas");

        mainTable = new Table();
        mainTable.setFillParent(true);

        setupQuestsList();
        setupQuestDetails();

        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);
    }

    private void setupQuestsList() {
        questsListTable = new Table();
        for (QuestInfo quest : journal.getQuests()) {
            Label questLabel = new Label(quest.getTitle(), new Label.LabelStyle(AssetFont.getInstance().getNightCore32(), null));
            questLabel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showQuestDetails(quest);
                }
            });
            questsListTable.add(questLabel).expandX().fillX().padTop(10);
            questsListTable.row();
        }
        questsScrollPane = new ScrollPane(questsListTable);
        mainTable.add(questsScrollPane).expand().fillY().width(300);
    }

    private void setupQuestDetails() {
        questDetailTable = new Table();
        questDetailTable.add(new Label("Select a quest to view details.", new Label.LabelStyle(AssetFont.getInstance().getNightCore32(), null))).expand().fill();
        mainTable.add(questDetailTable).expand().fill();
    }

    private void showQuestDetails(QuestInfo quest) {
        questDetailTable.clear();
        questDetailTable.add(new Label(quest.getTitle(), new Label.LabelStyle(AssetFont.getInstance().getNightCore32(), null))).pad(10);
        questDetailTable.row();
        questDetailTable.add(new Label(quest.getDescription(), new Label.LabelStyle(AssetFont.getInstance().getNightCore32(), null))).expand().fill().pad(10);
    }

    @Override
    public void loop(Player player, float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public void exit() {
        super.exit();
        uiManager.removeUI(player, "journal");
        stage.clear();
    }
}
