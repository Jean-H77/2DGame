package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.csun.game.MainGame;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class TitleScreen implements Screen {
    private static final String DIRECTORY_PATH = "titlescreen/";

    private final MainGame mainGame;
    private final Screen gameScreen;
    private final Stage stage;
    private final ExtendViewport extendViewport;
    private final Image[] menuImageActors = new Image[3];

    @Inject
    public TitleScreen(MainGame mainGame,
                       @Named("TitleScreenStage") Stage stage,
                       ExtendViewport screenViewPort,
                       @Named("GameScreen") Screen gameScreen) {
        this.mainGame = mainGame;
        this.stage = stage;
        this.extendViewport = screenViewPort;
        this.gameScreen = gameScreen;
    }

    @Override
    public void show() {
        stage.setViewport(extendViewport);

        Texture background = new Texture(Gdx.files.internal(getFileLocation("background.jpg")));
        Image image = new Image(background);
        image.setTouchable(Touchable.disabled);
        stage.addActor(image);

        stage.addActor(createGrave(1206, MenuOptionType.START, getFileLocation("grave_1.png")));
        stage.addActor(createGrave(1434, MenuOptionType.LOAD, getFileLocation("grave_2.png")));
        stage.addActor(createGrave(1603, MenuOptionType.EXIT, getFileLocation("grave_3.png")));

        stage.addActor(createMenuImage(MenuOptionType.START));
        stage.addActor(createMenuImage(MenuOptionType.LOAD));
        stage.addActor(createMenuImage(MenuOptionType.EXIT));

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    private static String getFileLocation(String file) {
        return DIRECTORY_PATH + file;
    }

    private ImageButton createGrave(int xPos, MenuOptionType optionType, String imagePath) {
        ImageButton grave = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(imagePath))));
        grave.setPosition(xPos, 0);
        grave.addListener(getClickEventListener(this, optionType));
        return grave;
    }

    private Image createMenuImage(MenuOptionType menuOptionType) {
         Image image = new Image(menuOptionType.unHover);
         image.setPosition(menuOptionType.pos.x, menuOptionType.pos.y);
         image.setTouchable(Touchable.disabled);
         return menuImageActors[menuOptionType.key] = image;
    }

    private ClickListener getClickEventListener(TitleScreen titleScreen, MenuOptionType menuOption) {
            return new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    menuImageActors[menuOption.key].setDrawable(menuOption.hover);
                    menuImageActors[menuOption.key].pack();
                }
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    menuImageActors[menuOption.key].setDrawable(menuOption.unHover);
                    menuImageActors[menuOption.key].pack();
                }
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    menuOption.event.execute(titleScreen);
                }
            };
    }

    private void startGame() {
        mainGame.setScreen(gameScreen);
    }

    private void loadGame() {

    }

    @FunctionalInterface
    private interface MenuOptionEvent { void execute(TitleScreen titleScreen); }

    private enum MenuOptionType {
        START(
            new TextureRegionDrawable(new TextureRegion(new Texture(getFileLocation("startgame_hover.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(getFileLocation("startgame.png")))),
            TitleScreen::startGame, new Vector2(1289, 30), 0
        ),
        LOAD(
            new TextureRegionDrawable(new TextureRegion(new Texture(getFileLocation("load_hover.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(getFileLocation("load.png")))),
            TitleScreen::loadGame, new Vector2(1512, 155), 1
        ),
        EXIT(
            new TextureRegionDrawable(new TextureRegion(new Texture(getFileLocation("quit_hover.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(getFileLocation("quit.png")))),
            (t) -> Gdx.app.exit(), new Vector2(1658, 35), 2
        )
        ;

        private final TextureRegionDrawable hover;
        private final TextureRegionDrawable unHover;
        private final MenuOptionEvent event;
        private final Vector2 pos;
        private final int key;

        MenuOptionType(TextureRegionDrawable hover, TextureRegionDrawable unHover, MenuOptionEvent menuOptionEvent, Vector2 pos, int key) {
            this.hover = hover;
            this.unHover = unHover;
            this.event = menuOptionEvent;
            this.pos = pos;
            this.key = key;
        }
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height, true);
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
