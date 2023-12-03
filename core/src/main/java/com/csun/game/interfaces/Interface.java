package com.csun.game.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csun.game.interfaces.impl.TestInterface;

import java.util.Optional;

public abstract class Interface {

    private static final int CACHE_SIZE = 10;

    private static final Interface[] cache = new Interface[CACHE_SIZE];

    private final Stage stage = new Stage();

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    protected abstract void buildView();

    public Interface(int id) {
        cache[id] = this;
        buildView();
    }

    public Interface addImageButton(String imagePath, int xPos, int yPos, ButtonClick buttonClick) {
        ImageButton imageButton = new ImageButton(createTextureRegionDrawable(imagePath));
        imageButton.setPosition(xPos, yPos);
        imageButton.addListener(createOnButtonClickListener(buttonClick));
        stage.addActor(imageButton);
        return this;
    }

    public Interface addImageButtonWithClickHover(String imagePath, String hoverImagePath, int xPos, int yPos, ButtonClick buttonClick) {
        TextureRegionDrawable nonHover = createTextureRegionDrawable(imagePath);
        TextureRegionDrawable hover = createTextureRegionDrawable(hoverImagePath);
        ImageButton imageButton = new ImageButton(nonHover, hover);
        imageButton.setPosition(xPos, yPos);
        imageButton.addListener(createOnButtonClickListener(buttonClick));
        stage.addActor(imageButton);
        return this;
    }

    public Interface addRectangle(float xPos, float yPos, float width, float height, Color color, ShapeRenderer.ShapeType shapeType) {
        stage.addActor(new Rectangle(shapeRenderer, xPos, yPos, width, height, color, shapeType));
        return this;
    }

    private static TextureRegionDrawable createTextureRegionDrawable(String path) {
        Texture texture = new Texture(Gdx.files.internal(path));
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    private static ClickListener createOnButtonClickListener(ButtonClick buttonClick) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.onButtonClick(x, y);
            }
        };
    }

    public static Optional<Interface> get(int id) {
        return Optional.of(cache[id]);
    }

    public void dispose() {
        stage.dispose();
    }

    public void process(float delta) {
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
        shapeRenderer.end();
    }

    public static void unpack() {
        cache[TestInterface.ID] = new TestInterface();
    }

    public Stage getStage() {
        return stage;
    }
}
