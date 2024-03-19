package com.csun.game.UI.impl.settings;

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
import com.csun.game.attributes.Attributes;
import com.csun.game.player.Player;
import com.google.inject.Inject;

public class PauseSettingsTable extends Table {

    private  PauseUI pauseUI;

    private  Player player;

    private Image soundSwitcher;


    public void createInsideElements(PauseUI pauseUI,Player player) {
        this.pauseUI = pauseUI;
        this.player = player;
        TextureAtlas uiTextureAtlas = new TextureAtlas(Gdx.files.internal("atlas/ui/ui.atlas"));

        Table soundTable = new Table();

        ImageTextButton.ImageTextButtonStyle buttonStyle = new ImageTextButton.ImageTextButtonStyle(
            new TextureRegionDrawable(uiTextureAtlas.findRegion("button")),null,null,
            createFont());

        soundSwitcher = new Image(uiTextureAtlas.findRegion("profile-soun-on"));
        Label soundLabel = new Label("Sound", new Label.LabelStyle(createFont(), null));
        ImageTextButton exitButton = new ImageTextButton("Return", buttonStyle);


        center();
        add(soundTable).width(250f).height(150f).pad(10f).row();
        add(exitButton).width(150f).height(60f).pad(10f).row();

        soundTable.add(soundLabel).width(150f);
        soundTable.add(soundSwitcher).width(125f).height(75f);


        if(player.getAttributes().get(Attributes.PLAYER_SETTINGS).getAudioLevel() > 0){
            soundSwitcher.setDrawable(new TextureRegionDrawable(uiTextureAtlas.findRegion("profile-soun-on")));
        }else{
            soundSwitcher.setDrawable(new TextureRegionDrawable(uiTextureAtlas.findRegion("profile-sound-off")));
        }

        soundSwitcher.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(player.getAttributes().get(Attributes.PLAYER_SETTINGS).getAudioLevel() == 0){
                    soundSwitcher.setDrawable(new TextureRegionDrawable(uiTextureAtlas.findRegion("profile-soun-on")));
                    player.getAttributes().get(Attributes.PLAYER_SETTINGS).setAudioLevel(1);
                }else{
                    soundSwitcher.setDrawable(new TextureRegionDrawable(uiTextureAtlas.findRegion("profile-sound-off")));
                    player.getAttributes().get(Attributes.PLAYER_SETTINGS).setAudioLevel(0);
                }
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pauseUI.showMainTable();
            }
        });

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    private BitmapFont createFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Nightcore Demo.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 32;
        parameter.color = Color.RED;


        return generator.generateFont(parameter);
    }
}
