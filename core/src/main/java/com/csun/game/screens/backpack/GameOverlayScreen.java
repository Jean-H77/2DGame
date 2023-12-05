package com.csun.game.screens.backpack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.csun.game.MainGame;
import com.csun.game.ashley.components.BackpackComponent;
import com.csun.game.ashley.components.ItemComponent;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/** First screen of the application. Displayed after the application is created. */
@Singleton
public class GameOverlayScreen implements Screen {
    private static final String DIRECTORY_PATH = "backpack/";

    private final MainGame game;

    private Stage stage;
    private PooledEngine engine;

    private Table overlayTable;
    private Table backPackTable;

    private BackpackOverlayScreen backpackOverlayScreen;



    private ItemComponent.ITEM_TYPES currentSelectItem;



    @Inject
    public GameOverlayScreen(MainGame game, PooledEngine engine,BackpackOverlayScreen backpackOverlayScreen) {
        this.game = game;

        this.stage = new Stage();
        this.engine = engine;
        this.backpackOverlayScreen = backpackOverlayScreen;

    }

    @Override
    public void show() {
        overlayTable = new Table();
        backPackTable = new Table();
        backPackTable.bottom().right();
        backPackTable.pad(10f);

        overlayTable.center();
        overlayTable.setFillParent(true);
        stage.addActor(overlayTable);
        overlayTable.stack(
            backPackTable
        ).grow();

        Image bagImage = createImage("bag-icon.png");
        bagImage.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                backpackOverlayScreen.switchBackPackVisible();
            }
        });
        backPackTable.add(bagImage).width(60f).height(60f);



    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.

    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        stage.dispose();
    }
    public InputProcessor getInputProcessor(){
        return stage;
    }


    private Image createImage(String path) {
        return new Image(new TextureRegionDrawable(new TextureRegion(new Texture(DIRECTORY_PATH + path))));
    }
    private TextureRegionDrawable createTRD(String path) {
        return new TextureRegionDrawable(new TextureRegion(new Texture(DIRECTORY_PATH + path)));
    }

}
