package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.csun.game.MainGame;
import com.csun.game.npc.NpcJson;
import com.csun.game.npc.NpcLoader;
import com.csun.game.npc.NpcManager;
import com.csun.game.player.PlayerLoader;
import com.csun.game.misc.Assets;
import com.csun.game.models.Animations;
import com.csun.game.player.Player;
import com.google.inject.Inject;

import java.util.List;


public class TitleScreen implements Screen {

    private final MainGame mainGame;
    private float time;
    private BitmapFont titleFont;
    private BitmapFont loadingFont;
    private final Stage stage = new Stage();
    float fadeTimer = 0f;
    float fadeDuration = 5f;
    private Label loadingLabel;
    private final Player player;

    @Inject
    public TitleScreen(MainGame mainGame, Player player) {
        this.mainGame = mainGame;
        this.player = player;
    }

    @Override
    public void show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fiendish.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        parameter.color = Color.WHITE;
        titleFont = generator.generateFont(parameter);
        generator.dispose();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("BloodLust.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.WHITE;
        loadingFont = generator.generateFont(parameter);
        generator.dispose();


        AssetManager assetManager = Assets.INSTANCE.getManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        assetManager.load("act1.tmx", TiledMap.class);
        assetManager.load("witchhouse.tmx", TiledMap.class);
        assetManager.load("mainhouse.tmx", TiledMap.class);
        assetManager.load("circusmap.tmx", TiledMap.class);
        assetManager.load("doctoroffice.tmx", TiledMap.class);
        assetManager.load("dadroom.tmx", TiledMap.class);
        assetManager.load("toddroom.tmx", TiledMap.class);
        assetManager.load("wellstart.tmx", TiledMap.class);
        assetManager.load("wellpuzzle.tmx", TiledMap.class);
        assetManager.load("bonedoor.tmx", TiledMap.class);
        assetManager.load("basement.tmx", TiledMap.class);
        assetManager.load("clocktower.tmx", TiledMap.class);

        assetManager.load("witch.atlas", TextureAtlas.class);
        assetManager.load("doctor.atlas", TextureAtlas.class);
        assetManager.load("mom.atlas", TextureAtlas.class);
        assetManager.load("skeleton.atlas", TextureAtlas.class);
        assetManager.load("zombie.atlas", TextureAtlas.class);
        assetManager.load("cali.atlas", TextureAtlas.class);
        assetManager.load("devon.atlas", TextureAtlas.class);
        assetManager.load("ghostgirl.atlas", TextureAtlas.class);
        assetManager.load("ghostman.atlas", TextureAtlas.class);
        assetManager.load("ringmaster.atlas", TextureAtlas.class);
        assetManager.load("headlessghost.atlas", TextureAtlas.class);
        assetManager.load("horseman.atlas", TextureAtlas.class);
        assetManager.load("redchest.atlas", TextureAtlas.class);









        assetManager.load("atlas/ui/ui.atlas", TextureAtlas.class);

        assetManager.load("gdx-skins-master/cloud-form/skin/cloud-form-ui.atlas", TextureAtlas.class);
        assetManager.load("gdx-skins-master/comic/skin/comic-ui.atlas", TextureAtlas.class);

        assetManager.load("dialogue/dialogue_board.png", Texture.class);

        assetManager.setLoader(NpcJson.NpcJsonList.class, new NpcLoader(new InternalFileHandleResolver()));
        assetManager.load("npc_spawns.json", NpcJson.NpcJsonList.class, new NpcLoader.JsonParameter());

        PlayerLoader.PlayerParameter playerParam = new PlayerLoader.PlayerParameter(player);
        assetManager.setLoader(Player.class, new PlayerLoader(new InternalFileHandleResolver()));
        assetManager.load("player.json", Player.class, playerParam);

        Table table = new Table();
        table.setFillParent(true);

        stage.addActor(table);

        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.ORANGE);
        Label titleLabel = new Label("RoguePixel Games", titleStyle);
        table.add(titleLabel).expand().center();
        table.row();

        Label.LabelStyle loadingStyle = new Label.LabelStyle(loadingFont, Color.WHITE);
        loadingLabel = new Label("Loading...", loadingStyle);
        table.add(loadingLabel).expand().center();
        table.row();
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);

        AssetManager assetManager = Assets.INSTANCE.getManager();
        if(assetManager.update(17)) {
            if(time >= 5) {
                Animations.init(assetManager);
                mainGame.getInjector().getInstance(NpcManager.class).put(assetManager.get("npc_spawns.json"));
                mainGame.fadeScreen("",mainGame.getInjector().getInstance(MenuScreen.class), mainGame.getInjector().getInstance(TitleScreen.class));
            }
        }

        time += v;
        fadeTimer += Gdx.graphics.getDeltaTime();
        if (fadeTimer > fadeDuration) {
            fadeTimer -= fadeDuration;
        }
        float fadeAlpha = Interpolation.sine.apply(Math.abs(MathUtils.sin(fadeTimer / fadeDuration * MathUtils.PI)));
        Color currentColor = loadingLabel.getColor();
        loadingLabel.setColor(currentColor.r, currentColor.g, currentColor.b, fadeAlpha);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
