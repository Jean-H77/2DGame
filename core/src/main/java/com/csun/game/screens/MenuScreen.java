package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import com.csun.game.UI.ShaderToyShader;
import com.google.inject.Inject;

public class MenuScreen implements Screen {

    private final MainGame mainGame;
    private final Stage stage;
    private BitmapFont gameTitleFont;
    private final ShaderToyShader menuBackground;

    @Inject
    public MenuScreen(MainGame mainGame, SpriteBatch spriteBatch) {
        this.mainGame = mainGame;
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), spriteBatch);
        Gdx.input.setInputProcessor(stage);

        var iChannel0 = new Texture(Gdx.files.internal("shaders/menushader/shadertoy0.png"));
        iChannel0.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        iChannel0.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        var iChannel1 = new Texture(Gdx.files.internal("shaders/menushader/shadertoy1.png"));
        iChannel1.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        iChannel1.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        menuBackground = new ShaderToyShader(Gdx.files.internal("shaders/menushader/menuShader.frag"), iChannel0, iChannel1);
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
        menuBackground.render();
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
