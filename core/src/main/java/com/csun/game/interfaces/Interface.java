package com.csun.game.interfaces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class Interface {

    // just gonna explicit create a new stage here
    protected final Stage stage = new Stage();

    protected abstract void populateStage();

    public void addImageButton(String imagePath, ClickListener clickListener) {
        ImageButton imageButton = new ImageButton(new Image(new TextureRegionDrawable(new TextureRegion(new Texture(imagePath)))).getDrawable());
        imageButton.addListener(clickListener);
        stage.addActor(imageButton);
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
