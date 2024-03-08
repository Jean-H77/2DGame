package com.csun.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.csun.game.MainGame;

import java.util.Objects;

public class FadeScreen implements Screen {

    private static BitmapFont font;
    private static final SpriteBatch spriteBatch = new SpriteBatch();
    private static final GlyphLayout layout = new GlyphLayout();

    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("npc_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        font = generator.generateFont(parameter);
        font.setColor(Color.valueOf("ffff00"));
        generator.dispose();
    }

    private final Screen current;
    private final Screen next;
    private final MainGame mainGame;

    private float alpha = 0;
    private boolean fadeDirection = true;
    private ShapeRenderer shapeRenderer;
    private float midPause = 0f;

    private String fadeText;

    public FadeScreen(Screen current, Screen next, MainGame game, String fadeText) {
        this.current = current;
        this.next = next;
        game.setScreen(next);
        game.setScreen(current);
        this.mainGame = game;
        this.fadeText = fadeText;
        layout.setText(font, fadeText);
    }

    public static void fade(Screen current, Screen next, MainGame mainGame, String fadeText) {
        mainGame.setScreen(new FadeScreen(current, next, mainGame, fadeText));
    }

    public static void fade(Screen current, Screen next, MainGame mainGame) {
        mainGame.setScreen(new FadeScreen(current, next, mainGame, ""));
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        if (fadeDirection) {
            current.render(Gdx.graphics.getDeltaTime());
        } else {
            next.render(Gdx.graphics.getDeltaTime());
        }

        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setColor(0, 0 ,0, alpha);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

        if (alpha >= 1) {
            fadeDirection = false;
            midPause += Gdx.graphics.getDeltaTime();
            if(midPause <= 1.5f) {
                if(!Objects.equals(fadeText, "")) {
                    spriteBatch.begin();
                    font.draw(spriteBatch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, (Gdx.graphics.getHeight() + layout.height) / 2);
                    spriteBatch.end();
                }
                return;
            }
        }
        else if (alpha <= 0 && !fadeDirection) {
            mainGame.setScreen(next);
        }
        alpha += (float) (fadeDirection ? 0.005 : -0.005);
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
