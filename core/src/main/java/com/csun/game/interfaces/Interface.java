package com.csun.game.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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

    protected abstract void buildView();

    public Interface addImageButton(String imagePath, int xPos, int yPos, ButtonClick buttonClick) {
        ImageButton imageButton = new ImageButton(createTextureRegionDrawable(imagePath));
        imageButton.setPosition(xPos, yPos);
        imageButton.addListener(createOnButtonClickListener(buttonClick));
        stage.addActor(imageButton);
        return this;
    }

    public Interface addImageButtonWithHover(String imagePath, String hoverImagePath, int xPos, int yPos, ButtonClick buttonClick) {
        TextureRegionDrawable nonHover = createTextureRegionDrawable(imagePath);
        TextureRegionDrawable hover = createTextureRegionDrawable(hoverImagePath);
        ImageButton imageButton = new ImageButton(nonHover, hover);
        imageButton.setPosition(xPos, yPos);
        imageButton.addListener(createOnButtonClickListener(buttonClick));
        stage.addActor(imageButton);
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
                buttonClick.onButtonClick();
            }
        };
    }

    public static Interface get(InterfaceType interfaceType) {
        Interface interface_;
        boolean isCacheable = interfaceType.isCacheable();
        if(isCacheable && (interface_ = cache.get(interfaceType)) != null) return interface_;
        try {
            interface_ = interfaceType.getClassz().getDeclaredConstructor().newInstance();
            interface_.buildView();
            if(isCacheable) cache.put(interfaceType, interface_);
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
