package com.csun.game.UI.impl.settings;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csun.game.MainGame;
import com.csun.game.UI.BaseUI;
import com.csun.game.annotations.UIComponent;
import com.csun.game.attributes.Attributes;
import com.csun.game.misc.Assets;
import com.csun.game.player.Player;
import com.csun.game.screens.GameScreen;
import com.google.inject.Inject;

import java.util.List;

@UIComponent(key = "pause")
public class PauseUI extends BaseUI {

    @Inject
    private final MainGame mainGame;


    private Table mainTable;
    private Table windowTable;
    private Table buttonTable;

    private Table iconTable;
    private ImageTextButton resumeButton;
    private ImageTextButton settingsButton;
    private ImageTextButton exitButton;
    private Image ripperIcon;

    private final AssetManager assetManager = Assets.INSTANCE.getManager();

    @Inject
    public PauseUI(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    @Override
    public void setup(Player player) {
        Gdx.app.log("Pause up", "Set up pause");

        mainGame.getInjector().getInstance(GameScreen.class).pause();

        Gdx.input.setInputProcessor(stage);

        InitializeUIElements();

        mainTable.setFillParent(true);
        mainTable.add(windowTable).width(300f).height(300f);

        windowTable.stack(
            iconTable,
            buttonTable
        ).width(300f).height(300f);

        iconTable.top();
        iconTable.add(ripperIcon).width(150f).height(150f).padTop(-75f);

        buttonTable.center();
        buttonTable.add(resumeButton).width(150f).height(60f).pad(10f).row();
        buttonTable.add(settingsButton).width(150f).height(60f).pad(10f).row();
        buttonTable.add(exitButton).width(150f).height(60f).pad(10f).row();

        stage.addActor(mainTable);
        mainTable.setFillParent(true);

        addActionsToButtons(player);
    }

    private void InitializeUIElements(){
        TextureAtlas uiTextureAtlas = assetManager.get("atlas/ui/ui.atlas");
        mainTable = new Table();

        windowTable = new Table();
        windowTable.background(new TextureRegionDrawable(uiTextureAtlas.findRegion("window")));

        buttonTable = new Table();
        iconTable = new Table();
        ImageTextButton.ImageTextButtonStyle buttonStyle = new ImageTextButton.ImageTextButtonStyle(
            new TextureRegionDrawable(uiTextureAtlas.findRegion("button")),null,null,
            createFont(32));

        resumeButton = new ImageTextButton("Resume",buttonStyle);
        settingsButton = new ImageTextButton("Settings",buttonStyle);
        exitButton = new ImageTextButton("Exit",buttonStyle);

        ripperIcon = new Image(uiTextureAtlas.findRegion("icon-ripper"));
    }
    private void addActionsToButtons(Player player){
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                List<BaseUI> openedUi = player.getAttributes().get(Attributes.ALL_OPENED_UI);
                PauseUI.this.exit();
                openedUi.remove(PauseUI.this);

                player.getAttributes().set(Attributes.PLAYER_IS_PAUSED,false);
            }
        });
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //TODO show settingsUI
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                List<BaseUI> openedUi = player.getAttributes().get(Attributes.ALL_OPENED_UI);
                PauseUI.this.exit();
                openedUi.remove(PauseUI.this);

                mainGame.switchScreen(MainGame.ScreenType.MENU_SCREEN);
            }
        });
    }

    @Override
    public void loop(Player player, float deltaTime) {

    }

    @Override
    public void exit() {
        super.exit();
        stage.clear();
        mainGame.getInjector().getInstance(GameScreen.class).resume();
    }

    @Override
    public boolean isWalkable() {
        return false;
    }


    private BitmapFont createFont(int fontSize){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Nightcore Demo.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = fontSize;
        parameter.color = Color.RED;


        return generator.generateFont(parameter);
    }
}
