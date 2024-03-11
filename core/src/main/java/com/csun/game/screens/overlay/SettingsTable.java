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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.google.inject.Inject;

public class SettingsTable extends Table {

    private final PauseTable pauseTable;

    private Table soundTable;
    private Image soundSwitcher;
    private Label soundLabel;
    private ImageTextButton exitButton;

    private boolean soundOn = true;

    @Inject
    public SettingsTable(PauseTable pauseTable) {
        this.pauseTable = pauseTable;
        createInsideElements();
    }

    public void createInsideElements() {

        TextureAtlas uiTextureAtlas = new TextureAtlas(Gdx.files.internal("atlas/ui/ui.atlas"));

        soundTable = new Table();

        ImageTextButton.ImageTextButtonStyle buttonStyle = new ImageTextButton.ImageTextButtonStyle(
            new TextureRegionDrawable(uiTextureAtlas.findRegion("button")),null,null,
                createFont(32));

        soundSwitcher = new Image(uiTextureAtlas.findRegion("profile-soun-on"));
        soundLabel = new Label("Sound",new Label.LabelStyle(createFont(32),null));
        exitButton = new ImageTextButton("Return",buttonStyle);


        center();
        add(soundTable).width(250f).height(150f).pad(10f).row();
        add(exitButton).width(150f).height(60f).pad(10f).row();

        soundTable.add(soundLabel).width(150f);
        soundTable.add(soundSwitcher).width(125f).height(75f);


        if(soundOn){
            soundSwitcher.setDrawable(new TextureRegionDrawable(uiTextureAtlas.findRegion("profile-soun-on")));
        }else{
            soundSwitcher.setDrawable(new TextureRegionDrawable(uiTextureAtlas.findRegion("profile-sound-off")));
        }

        soundSwitcher.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                soundOn = !soundOn;
                if(soundOn){
                    soundSwitcher.setDrawable(new TextureRegionDrawable(uiTextureAtlas.findRegion("profile-soun-on")));
                }else{
                    soundSwitcher.setDrawable(new TextureRegionDrawable(uiTextureAtlas.findRegion("profile-sound-off")));
                }
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pauseTable.activeSettingsMenu(false);
            }
        });

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
