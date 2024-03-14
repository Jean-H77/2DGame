package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.csun.game.MainGame;
import com.csun.game.player.Player;
import com.google.inject.Inject;

public class MenuScreen implements Screen {

    private final MainGame mainGame;
    private final Stage stage;
    private BitmapFont gameTitleFont;
    private final Player player;

    @Inject
    public MenuScreen(MainGame mainGame, SpriteBatch spriteBatch, Player player) {
        this.mainGame = mainGame;
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), spriteBatch);
        this.player = player;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Nightcore Demo.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color = Color.WHITE;
        gameTitleFont = generator.generateFont(parameter);
        generator.dispose();

        Table table = new Table();
        table.top().center();

        table.setFillParent(true);

        stage.addActor(table);

        Label.LabelStyle titleStyle = new Label.LabelStyle(gameTitleFont, Color.RED);

        Label titleL1 = new Label("Hollows Curse", titleStyle);
        titleL1.setAlignment(Align.center);
        table.add(titleL1);
        table.row();

        Label titleL2 = new Label("Pursuit Of The Headless Horseman", titleStyle);
        table.add(titleL2);
        table.row();

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = gameTitleFont;
        buttonStyle.fontColor = Color.RED;
        buttonStyle.overFontColor = Color.WHITE;

        TextButton playButton = new TextButton("Play now", buttonStyle);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.postLoad();
                mainGame.switchScreen(MainGame.ScreenType.GAME_SCREEN);
            }
        });
        table.add(playButton).padTop(10f);
        table.row();

        TextButton exitButton = new TextButton("Exit", buttonStyle);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(exitButton).padTop(10f);
        table.row();



    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
