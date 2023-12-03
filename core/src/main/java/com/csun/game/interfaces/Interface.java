package com.csun.game.interfaces;

import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Interface {

    protected final Stage stage = new Stage();

    protected abstract void buildView();

    public void dispose() {
        stage.dispose();
    }

    public void render(float delta) {
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }
}
