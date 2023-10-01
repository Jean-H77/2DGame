package com.csun.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;
import com.csun.game.ashley.systems.AnimationSystem;
import com.csun.game.ashley.systems.MovementSystem;
import com.csun.game.ashley.systems.PlayerInputSystem;
import com.csun.game.ashley.systems.RenderSystem;
import com.csun.game.screens.TitleScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {

    private final PooledEngine engine = new PooledEngine();

    private SpriteBatch spriteBatch;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        addSystems();
        createPlayer();
        setScreen(new TitleScreen(this));
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    /**
     * Here is where the systems are created in a pipeline,
     * so they execute in the order they're put in.
     */
    private void addSystems() {
        engine.addSystem(new PlayerInputSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new AnimationSystem());
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
       // TextureComponent textureComponent = new TextureComponent();
        //test
       // Texture texture = new Texture(Gdx.files.internal("gameOne/mummy.png"));
        //textureComponent.textureRegion = new TextureRegion(texture, 0, 0, 25, 25);
       // entity.add(textureComponent);

        entity.add(new MovementComponent());
        entity.add(new PlayerComponent());

        // add entity to engine
        engine.addEntity(entity);
    }

    public PooledEngine getEngine() {
        return engine;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}
