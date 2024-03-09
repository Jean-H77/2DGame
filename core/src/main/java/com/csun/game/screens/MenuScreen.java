package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.csun.game.MainGame;
import com.csun.game.misc.Assets;
import com.github.tommyettinger.textra.KnownFonts;
import com.github.tommyettinger.textra.TextraButton;
import com.github.tommyettinger.textra.TextraLabel;
import com.google.inject.Inject;

import static com.csun.game.GameConstants.GAME_TITLE;

public class MenuScreen implements Screen {

    private final MainGame mainGame;
    private final AssetManager assetManager = Assets.INSTANCE.getManager();
    private boolean clickedPlay = false;
    private final Stage stage;

    @Inject
    public MenuScreen(MainGame mainGame, SpriteBatch spriteBatch) {
        this.mainGame = mainGame;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), spriteBatch);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        // ShaderProgram shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/example.vert"), Gdx.files.internal("shaders/example.frag"));
        // spriteBatch.setShader(shaderProgram);

        Skin skin = new Skin();
        skin.addRegions(assetManager.get("gdx-skins-master/cloud-form/skin/cloud-form-ui.atlas"));
        skin.load(Gdx.files.internal("gdx-skins-master/cloud-form/skin/cloud-form-ui.json"));

        TextraButton startGameTextButton = new TextraButton("Start Game", skin, "default");
        startGameTextButton.setSize(100, 100);
        startGameTextButton.setPosition((float) Gdx.graphics.getWidth() /2 - startGameTextButton.getWidth()/2, (float) Gdx.graphics.getHeight() /2 - startGameTextButton.getHeight()/6);
        startGameTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!clickedPlay) {
                    Gdx.input.setInputProcessor(null);
                    clickedPlay = true;
                    Gdx.app.log("C", "C");
                    mainGame.fadeScreen("Random welcome text here", mainGame.getInjector().getInstance(GameScreen.class), mainGame.getInjector().getInstance(MenuScreen.class));
                }
            }
        });
        stage.addActor(startGameTextButton);

        TextButton exitGameTextButton = new TextButton("Exit", skin, "default");
        exitGameTextButton.setSize(100, 100);
        exitGameTextButton.setPosition((float) Gdx.graphics.getWidth() /2 - exitGameTextButton.getWidth()/2, (float) ((float) Gdx.graphics.getHeight() /2 - exitGameTextButton.getHeight()*1.3));
        exitGameTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitGameTextButton);

        TextraLabel titleLabel = new TextraLabel(GAME_TITLE, KnownFonts.getStandardFamily());
        titleLabel.setWrap(true);
        titleLabel.setAlignment(Align.center);
        titleLabel.setSize(200, 100);
        titleLabel.setPosition((float) Gdx.graphics.getWidth() /2 - titleLabel.getWidth()/2, (float) Gdx.graphics.getHeight() - titleLabel.getHeight());
        stage.addActor(titleLabel);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.SKY);
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
