package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csun.game.MainGame;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class TitleScreen implements Screen {
    private static final String DIRECTORY_PATH = "titlescreen/";

    private static final Image[][] MENU_IMAGES = {
        { createImage("startgame_hover.png"), createImage("startgame.png") },
        { createImage("load_hover.png"), createImage("load.png") },
        { createImage("quit_hover.png"), createImage("quit.png") },
    };

    @FunctionalInterface
    private interface GraveClickEvent { void execute(); }

    private final MainGame mainGame;
    private final Screen gameScreen;
    private final Stage stage;
    private final Viewport viewport;
    private final Image[] menuImageActors = new Image[3];

    @Inject
    public TitleScreen(MainGame mainGame, @Named("TitleScreenStage") Stage stage,
                       @Named("Extended") Viewport viewport, @Named("GameScreen") Screen gameScreen) {
        this.mainGame = mainGame;
        this.stage = stage;
        this.viewport = viewport;
        this.gameScreen = gameScreen;
    }

    @Override
    public void show() {
        stage.setViewport(viewport);
        Image image = createImage("background.jpg"); image.setTouchable(Touchable.disabled);
        stage.getActors().addAll(
            image,
            createGrave(1206, 0, "grave_1.png", this::startGame),
            createGrave(1434, 1, "grave_2.png", this::loadGame),
            createGrave(1603, 2, "grave_3.png", () -> Gdx.app.exit()),
            createMenuOption(0, 1289, 30), createMenuOption(1, 1512,155), createMenuOption(2, 1658,35)
        );
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

    private ImageButton createGrave(int xPos, int index, String imagePath, GraveClickEvent event) {
        ImageButton grave = new ImageButton(createImage(imagePath).getDrawable());
        grave.setPosition(xPos, 0);
        grave.addListener(getClickEventListener(index, event));
        return grave;
    }

    private Image createMenuOption(int index, int x, int y) {
         Image image = new Image(MENU_IMAGES[index][1].getDrawable());
         image.setPosition(x, y);
         image.setTouchable(Touchable.disabled);
         return menuImageActors[index] = image;
    }

    private static Image createImage(String path) {
        return new Image(new TextureRegionDrawable(new TextureRegion(new Texture(getFileLocation(path)))));
    }

    private ClickListener getClickEventListener(int index, GraveClickEvent clickEvent) {
            return new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    menuImageActors[index].setDrawable(MENU_IMAGES[index][0].getDrawable());
                    menuImageActors[index].pack();
                }
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    menuImageActors[index].setDrawable(MENU_IMAGES[index][1].getDrawable());
                    menuImageActors[index].pack();
                }
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    clickEvent.execute();
                }
            };
    }

    private void startGame() {
        mainGame.setScreen(gameScreen);
    }

    private void loadGame() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
