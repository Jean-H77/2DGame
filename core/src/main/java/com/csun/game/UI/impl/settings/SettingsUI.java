package com.csun.game.UI.impl.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.csun.game.UI.BaseUI;
import com.csun.game.annotations.UIComponent;
import com.csun.game.attributes.Attributes;
import com.csun.game.player.Player;
import com.google.inject.Inject;

import static com.csun.game.GameConstants.VIEWPORT_HEIGHT;
import static com.csun.game.GameConstants.VIEWPORT_WIDTH;

@UIComponent(key = "settings")
public class SettingsUI extends BaseUI {

    private final AssetManager assetManager;

    @Inject
    public SettingsUI(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void setup(Player player) {
        Gdx.app.log("Setting up", "Set up settings");
        Settings settings = player.getAttributes().get(Attributes.PLAYER_SETTINGS);

        Skin skin = new Skin();
        skin.addRegions(assetManager.get("gdx-skins-master/comic/skin/comic-ui.atlas"));
        skin.load(Gdx.files.internal("gdx-skins-master/comic/skin/comic-ui.json"));

        Table table = new Table(skin);
        table.center();
        table.setPosition((float) Gdx.graphics.getWidth() /2, (float) Gdx.graphics.getHeight() /2, Align.center);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        TextButton fullScreenButton = new TextButton("Full screen", skin, "default");
        fullScreenButton.setSize(50.0f, 50.0f);
        fullScreenButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settings.setScreenType(ScreenType.FULL_SCREEN);
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        });

        table.add(fullScreenButton);

        table.row();
        TextButton windowedScreenButton = new TextButton("Windowed screen", skin, "default");
        windowedScreenButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settings.setScreenType(ScreenType.WINDOWED);
                Gdx.graphics.setWindowedMode(VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
            }
        });
        fullScreenButton.setSize(50.0f, 50.0f);
        table.add(windowedScreenButton);
    }

    @Override
    public void loop(Player player, float deltaTime) {

    }

    @Override
    public boolean isWalkable() {
        return false;
    }

}
