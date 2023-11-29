package com.csun.game.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class Interface {

    // just gonna explicit create a new stage here
    protected final Stage stage = new Stage();

    protected abstract void populateStage();

    protected void addImageButton(String imagePath, ClickListener clickListener) {
        ImageButton imageButton = new ImageButton(createTextureRegionDrawable(imagePath));
        imageButton.addListener(clickListener);
        stage.addActor(imageButton);
    }

    protected void addImageButtonWithHover(String imagePath, String hoverImagePath, ClickListener clickListener) {
        TextureRegionDrawable nonHover = createTextureRegionDrawable(imagePath);
        TextureRegionDrawable hover = createTextureRegionDrawable(hoverImagePath);
        ImageButton imageButton = new ImageButton(nonHover, hover);
        imageButton.addListener(clickListener);
        stage.addActor(imageButton);
    }

    private static TextureRegionDrawable createTextureRegionDrawable(String path) {
        Texture texture = new Texture(Gdx.files.internal(path));
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public void createInterface() {
        populateStage();
    }

    public void dispose() {
        stage.dispose();
    }

    public void process() {
        stage.draw();
    }
}
