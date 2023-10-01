package com.csun.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class TitleScreen extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    private Texture bloodyCursor;
    private BitmapFont font;
    private int currentOption = 0;
    private String[] menuOptions = {"Start", "Load", "Quit"};
    private Vector2 arrowPosition = new Vector2(50, 200);

    @Override
    public void create() {
        batch = new SpriteBatch();

        background = new Texture(Gdx.files.internal("background.jpg"));
        bloodyCursor = new Texture(Gdx.files.internal("bloody_cursor.png"));

        font = new BitmapFont(); // You can use a custom font with LibGDX's BitmapFont class.
        font.getData().setScale(2);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        if (currentOption > 0) {
                            currentOption--;
                            arrowPosition.y += 40;  // You might want to adjust this.
                        }
                        break;
                    case Input.Keys.DOWN:
                        if (currentOption < 2) {
                            currentOption++;
                            arrowPosition.y -= 40;  // You might want to adjust this.
                        }
                        break;
                    case Input.Keys.ENTER:
                        executeOption();
                        break;
                }
                return super.keyDown(keycode);
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.draw(batch, "Game Title", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 50);
        for (int i = 0; i < menuOptions.length; i++) {
            font.draw(batch, menuOptions[i], 100, 200 - i * 40);
        }

        font.draw(batch, "->", arrowPosition.x, arrowPosition.y);

        batch.end();
    }

    private void executeOption() {
        switch (currentOption) {
            case 0: // Start
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

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        bloodyCursor.dispose();
        font.dispose();
    }

    public static void main(String[] arg) {

    }
}
