package com.csun.game.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.HashMap;

public abstract class Interface {

    private final HashMap<Integer, Interface> cache = new HashMap<>();

    protected final Stage stage = new Stage();

    private final int id;

    private int interfaceCounter;

    protected Interface() {
        this.id = ++interfaceCounter;
    }

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

    public Interface getAndCreateInterface() {
        Interface temp;
        if((temp = cache.get(id)) != null) return temp;

        populateStage();
        cache.put(id, this);
        return this;
    }

    public void dispose() {
        stage.dispose();
    }

    public void process() {
        stage.draw();
    }
}
