package com.csun.game.screens.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csun.game.screens.GameScreen;
import com.google.inject.Inject;

public class PauseTable extends Table {

    private final GameScreen gameScreen;

    private Table windowTable;
    private Table buttonTable;

    private Table settingsTable;
    private Table iconTable;
    private ImageTextButton resumeButton;
    private ImageTextButton settingsButton;
    private ImageTextButton exitButton;
    private Image ripperIcon;

    @Inject
    public PauseTable(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        createInsideElements();
    }

    public void createInsideElements() {

        TextureAtlas uiTextureAtlas = new TextureAtlas(Gdx.files.internal("atlas/ui/ui.atlas"));
        windowTable = new Table();
        buttonTable = new Table();
        iconTable = new Table();
        settingsTable = new SettingsTable(this);

        ImageTextButton.ImageTextButtonStyle buttonStyle = new ImageTextButton.ImageTextButtonStyle(
            new TextureRegionDrawable(uiTextureAtlas.findRegion("button")),null,null,
                createFont(32));

        resumeButton = new ImageTextButton("Resume",buttonStyle);
        settingsButton = new ImageTextButton("Settings",buttonStyle);
        exitButton = new ImageTextButton("Exit",buttonStyle);

        ripperIcon = new Image(uiTextureAtlas.findRegion("icon-ripper"));

        setFillParent(true);
        add(windowTable).width(300f).height(300f);

        windowTable.background(new TextureRegionDrawable(uiTextureAtlas.findRegion("window")));
        windowTable.stack(
            iconTable,
            settingsTable,
            buttonTable
        ).width(300f).height(300f);

        iconTable.top();
        iconTable.add(ripperIcon).width(150f).height(150f).padTop(-75f);

        buttonTable.center();
        buttonTable.add(resumeButton).width(150f).height(60f).pad(10f).row();
        buttonTable.add(settingsButton).width(150f).height(60f).pad(10f).row();
        buttonTable.add(exitButton).width(150f).height(60f).pad(10f).row();


        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                gameScreen.resume();
            }
        });
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                activeSettingsMenu(true);
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                gameScreen.goToMainMenu();
            }
        });

        activeSettingsMenu(false);
    }
    public void activeSettingsMenu(boolean active) {
        if (active) {
            settingsTable.setVisible(true);
            buttonTable.setVisible(false);
        } else {
            settingsTable.setVisible(false);
            buttonTable.setVisible(true);
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    private BitmapFont createFont(int fontSize){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Nightcore Demo.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = fontSize;
        parameter.color = Color.RED;


        return generator.generateFont(parameter);
    }
}
