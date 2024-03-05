package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.csun.game.MainGame;
import com.csun.game.models.AnimationType;
import com.google.inject.Inject;

public class TitleScreen implements Screen {

    private final AssetManager assetManager;
    private final MainGame mainGame;

    @Inject
    public TitleScreen(AssetManager assetManager, MainGame mainGame) {
        this.assetManager = assetManager;
        this.mainGame = mainGame;
    }

    @Override
    public void show() {
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        assetManager.load("act1.tmx", TiledMap.class);
        assetManager.load("act2.tmx", TiledMap.class);

        assetManager.load("witch.atlas", TextureAtlas.class);
        assetManager.load("gdx-skins-master/cloud-form/skin/cloud-form-ui.atlas", TextureAtlas.class);
    }

    @Override
    public void render(float v) {
        Gdx.app.log("Rendering", "Ye");

        if(assetManager.update()) {
            if(assetManager.get("witch.atlas") == null) {
                Gdx.app.log("Is false, ", "ISNULL");

            }
         //   AnimationType.AnimationData.init(assetManager);
            mainGame.switchScreen(MainGame.ScreenType.MENU_SCREEN);
        } else {
            Gdx.app.log("Is false, ", String.valueOf(assetManager.getProgress()));
        }
    }

    @Override
    public void resize(int i, int i1) {

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

    }
}
