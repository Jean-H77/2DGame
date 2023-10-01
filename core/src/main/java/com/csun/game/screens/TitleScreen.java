package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.MainGame;

public class TitleScreen implements Screen {

    private final MainGame mainGame;
    private final SpriteBatch spriteBatch;
    private Texture background;
    private Texture bloodyCursor;
    private BitmapFont font;
    private int currentOption = 0;
    private final String[] menuOptions = {"Start", "Load", "Quit"};
    private final Vector2 arrowPosition = new Vector2(50, 200);

    public TitleScreen(MainGame mainGame) {
        this.mainGame = mainGame;
        this.spriteBatch = mainGame.getSpriteBatch();
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("background.jpg"));
        bloodyCursor = new Texture(Gdx.files.internal("bloody_cursor.png"));

        font = new BitmapFont(); // You can use a custom font with LibGDX's BitmapFont class.
        font.getData().setScale(2);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.UP -> {
                        if (currentOption > 0) {
                            currentOption--;
                            arrowPosition.y += 40;  // You might want to adjust this.
                        }
                    }
                    case Input.Keys.DOWN -> {
                        if (currentOption < 2) {
                            currentOption++;
                            arrowPosition.y -= 40;  // You might want to adjust this.
                        }
                    }
                    case Input.Keys.ENTER -> executeOption();
                }
                return super.keyDown(keycode);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.draw(spriteBatch, "Game Title", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 50);
        for (int i = 0; i < menuOptions.length; i++) {
            font.draw(spriteBatch, menuOptions[i], 100, 200 - i * 40);
        }

        font.draw(spriteBatch, "->", arrowPosition.x, arrowPosition.y);

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        spriteBatch.dispose();
        background.dispose();
        bloodyCursor.dispose();
        font.dispose();
    }

    private void executeOption() {
        switch (currentOption) {
            case 0: // Start
                mainGame.setScreen(new GameScreen(mainGame));
                // Handle start game logic here
                break;
            case 1: // Load
                // Handle load game logic here
                break;
            case 2: // Quit
                Gdx.app.exit();
                break;
        }
    }
}
