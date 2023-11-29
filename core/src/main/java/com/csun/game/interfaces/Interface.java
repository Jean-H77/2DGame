package com.csun.game.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Interface {

    private static final Logger logger = Logger.getLogger(Interface.class.getName());

    private static final HashMap<InterfaceType, Interface> cache = new HashMap<>();

    private final Stage stage = new Stage();

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

    public static Interface get(InterfaceType interfaceType) {
        Interface interface_;
        if((interface_ = cache.get(interfaceType)) != null) return interface_;
        try {
            interface_ = interfaceType.getClassz().getDeclaredConstructor().newInstance();
            interface_.populateStage();
            cache.put(interfaceType, interface_);
            return interface_;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to create interface " + interfaceType.toString(), ex);
        }
        return null;
    }

    public void dispose() {
        stage.dispose();
    }

    public void process() {
        stage.draw();
    }
}
