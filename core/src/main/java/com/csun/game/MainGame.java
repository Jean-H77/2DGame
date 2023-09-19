package com.csun.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.csun.game.ashley.components.TextureComponent;
import com.csun.game.ashley.components.TransformComponent;
import com.csun.game.ashley.systems.MovementSystem;
import com.csun.game.ashley.systems.RenderSystem;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {

    private final PooledEngine engine = new PooledEngine();

    @Override
    public void create() {
        addSystems();
        createPlayer();
        setScreen(new FirstScreen(this));
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    /**
     * Here is where the systems are created in a pipeline,
     * so they execute in the order they're put in.
     */
    private void addSystems() {
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderSystem());
        Gdx.app.log("Systems", "added " + engine.getSystems().size() + " systems");
    }

    /**
     * Add player method,
     * here you'll add components to the player entity.
     */
    private void createPlayer() {
        // create the entity
        Entity entity = engine.createEntity();

        // add components
        entity.add(new TransformComponent());
        TextureComponent textureComponent = new TextureComponent();

        //test
        //Texture texture = new Texture(Gdx.files.internal("ghost.png"));
        //textureComponent.textureRegion = new TextureRegion(texture, 0, 0, 200, 200);

        entity.add(textureComponent);

        // add entity to engine
        engine.addEntity(entity);
    }

    public PooledEngine getEngine() {
        return engine;
    }
}
