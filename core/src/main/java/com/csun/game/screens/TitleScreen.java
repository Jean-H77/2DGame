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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.csun.game.MainGame;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class TitleScreen implements Screen {

    private final MainGame mainGame;
    private final Screen gameScreen;
    private final Stage stage;
    private final ExtendViewport screenViewport;
    private final Image[] menuImageActors = new Image[3];

    @Inject
    public TitleScreen(MainGame mainGame,
                       @Named("TitleScreenStage") Stage stage,
                       ExtendViewport screenViewPort,
                       @Named("GameScreen") Screen gameScreen) {
        this.mainGame = mainGame;
        this.stage = stage;
        this.screenViewport = screenViewPort;
        this.gameScreen = gameScreen;
    }

    @Override
    public void show() {
        stage.setViewport(screenViewport);

        Texture background = new Texture(Gdx.files.internal("titlescreen/background.jpg"));
        Image image = new Image(background);
        image.setTouchable(Touchable.disabled);
        stage.addActor(image);

        ImageButton firstGrave = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("titlescreen/grave_1.png"))));
        firstGrave.setPosition(1206, 0);
        firstGrave.addListener(addClickEventListener(this, MenuOptionType.START));
        stage.addActor(firstGrave);

        ImageButton secondGrave = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("titlescreen/grave_2.png"))));
        secondGrave.setPosition(1434, 0);
        secondGrave.addListener(addClickEventListener(this, MenuOptionType.LOAD));
        stage.addActor(secondGrave);

        ImageButton thirdGrave = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("titlescreen/grave_3.png"))));
        thirdGrave.setPosition(1603, 0);
        thirdGrave.addListener(addClickEventListener(this, MenuOptionType.EXIT));

        stage.addActor(thirdGrave);

        menuImageActors[0] = new Image(MenuOptionType.START.unHover);
        menuImageActors[0].setPosition(1289, 30);
        stage.addActor(menuImageActors[0]);
        menuImageActors[0].setTouchable(Touchable.disabled);

        menuImageActors[1] = new Image(MenuOptionType.LOAD.unHover);
        menuImageActors[1].setPosition(1512, 155);
        stage.addActor(menuImageActors[1]);
        menuImageActors[1].setTouchable(Touchable.disabled);

        menuImageActors[2] = new Image(MenuOptionType.EXIT.unHover);
        menuImageActors[2].setPosition(1658, 35);
        stage.addActor(menuImageActors[2]);
        menuImageActors[2].setTouchable(Touchable.disabled);

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

    private ClickListener addClickEventListener(TitleScreen titleScreen, MenuOptionType menuOption) {
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
            new TextureRegionDrawable(new TextureRegion(new Texture("titlescreen/startgame_hover.png"))),
            new TextureRegionDrawable(new TextureRegion(new Texture("titlescreen/startgame.png"))),
            TitleScreen::startGame, 0
        ),
        LOAD(
            new TextureRegionDrawable(new TextureRegion(new Texture("titlescreen/load_hover.png"))),
            new TextureRegionDrawable(new TextureRegion(new Texture("titlescreen/load.png"))),
            TitleScreen::loadGame, 1
        ),
        EXIT(
            new TextureRegionDrawable(new TextureRegion(new Texture("titlescreen/quit_hover.png"))),
            new TextureRegionDrawable(new TextureRegion(new Texture("titlescreen/quit.png"))),
            (t) -> Gdx.app.exit(), 2
        )
        ;

        private final TextureRegionDrawable hover;
        private final TextureRegionDrawable unHover;
        private final MenuOptionEvent event;
        private final int key;

        MenuOptionType(TextureRegionDrawable hover, TextureRegionDrawable unHover, MenuOptionEvent menuOptionEvent, int key) {
            this.hover = hover;
            this.unHover = unHover;
            this.event = menuOptionEvent;
            this.key = key;
        }
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
