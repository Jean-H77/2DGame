package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.MainGame;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class TitleScreen implements Screen {

    private final MainGame mainGame;
    private final Screen gameScreen;
    private final SpriteBatch spriteBatch;
    private Texture background;
    private Texture bloodyCursor;
    private int currentOption = 0;
    private final String[] menuOptions = {"Start", "Load", "Quit"};
    private final Vector2 arrowPosition = new Vector2(50, 200);
    private BitmapFont bloodyFont;
    private final Rectangle[] optionRects = new Rectangle[menuOptions.length];
    private GlyphLayout layout = new GlyphLayout();

    @Inject
    public TitleScreen(MainGame mainGame, @Named("GameScreen")Screen gameScreen, SpriteBatch spriteBatch) {
        this.mainGame = mainGame;
        this.gameScreen = gameScreen;
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("background.jpg"));
        bloodyCursor = new Texture(Gdx.files.internal("bloody_cursor.png"));

        bloodyFont = new BitmapFont(Gdx.files.internal("font.fnt"));
        bloodyFont.setColor(Color.RED); // Setting the font color to red.
        bloodyFont.getData().setScale(1.5F);

        for (int i = 0; i < menuOptions.length; i++) {
            layout.setText(bloodyFont, menuOptions[i]);
            float width = layout.width;
            float height = layout.height;
            optionRects[i] = new Rectangle(100, 200 - i * 40 - height, width, height);
        }

        Pixmap pixmap = new Pixmap(Gdx.files.internal("bloody_cursor.png"));
        Cursor cursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose();
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.UP -> {
                        if (currentOption > 0) {
                            currentOption--;
                            arrowPosition.y += 40;
                        }
                    }
                    case Input.Keys.DOWN -> {
                        if (currentOption < 2) {
                            currentOption++;
                            arrowPosition.y -= 40;
                        }
                    }
                    case Input.Keys.ENTER -> executeOption();
                }
                return super.keyDown(keycode);
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                // Convert the y-coordinate from top-left origin to bottom-left origin (LibGDX's default)
                int flippedScreenY = Gdx.graphics.getHeight() - screenY;

                // Check if the mouse is hovering over any of the menu options
                for (int i = 0; i < menuOptions.length; i++) {
                    layout.setText(bloodyFont, menuOptions[i]);
                    float optionX = (Gdx.graphics.getWidth() - layout.width) / 2;
                    float optionY = 100 + (menuOptions.length - i - 1) * 40;
                    float optionWidth = layout.width;
                    float optionHeight = layout.height;

                    if (screenX >= optionX && screenX <= optionX + optionWidth && flippedScreenY >= optionY - optionHeight && flippedScreenY <= optionY) {
                        currentOption = i;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    executeOption();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //bloodyFont.draw(spriteBatch, "Game Title", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 50);
        // Draw Game Title
        layout.setText(bloodyFont, "Game Title");
        float titleX = (Gdx.graphics.getWidth() - layout.width) / 2;  // Centered horizontally
        float titleY = Gdx.graphics.getHeight() - layout.height - 20;  // 20 pixels padding from the top
        bloodyFont.draw(spriteBatch, "Game Title", titleX, titleY);


        // Draw Menu Options
        for (int i = 0; i < menuOptions.length; i++) {
            layout.setText(bloodyFont, menuOptions[i]);
            float optionX = (Gdx.graphics.getWidth() - layout.width) / 2;  // Centered horizontally
            float optionY = 100 + (menuOptions.length - i - 1) * 40;  // Adjusted for screen's bottom
            bloodyFont.draw(spriteBatch, menuOptions[i], optionX, optionY);

            // Adjust arrow position to align with the text's center
            if(i == currentOption) {
                arrowPosition.x = optionX - 40;  // 40 pixels to the left of the text start
                arrowPosition.y = optionY;
            }
        }

        bloodyFont.draw(spriteBatch, "->", arrowPosition.x, arrowPosition.y);

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
        bloodyFont.dispose();
    }

    private void executeOption() {
        switch (currentOption) {
            case 0: // Start
                mainGame.setScreen(gameScreen);
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
