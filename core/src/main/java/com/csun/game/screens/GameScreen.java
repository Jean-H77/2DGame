package com.csun.game.screens;

import box2dLight.RayHandler;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csun.game.MainGame;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.ClickableComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.NpcComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.csun.game.ashley.systems.MovementSystem;
import com.csun.game.attributes.Attributes;
import com.csun.game.dialogue.Dialogue;
import com.csun.game.map.GameMap;
import com.csun.game.models.AnimationType;
import com.csun.game.models.Direction;
import com.csun.game.player.Player;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import static com.csun.game.GameConstants.TILE_SIZE;

/** First screen of the application. Displayed after the application is created. */
@Singleton
public class GameScreen implements Screen {

    private final RayHandler rayHandler;
    private final PooledEngine pooledEngine;
    private final OrthographicCamera playerCamera;
    private final OrthogonalTiledMapRenderer renderer;
    private final Player player;
    private final Viewport viewport;
    private final SpriteBatch spriteBatch;
    private final MainGame mainGame;

    BitmapFont font;
    ShapeRenderer shapeRenderer;
    SpriteBatch debugSpriteBatch;
    BitmapFont debugFont;
    public static String debugNpcHoverText = "";
    Dialogue dialogue;

    @Inject
    public GameScreen(PooledEngine pooledEngine, @Named("ExtendedViewport") Viewport viewport, Player player, SpriteBatch spriteBatch, MainGame mainGame) {
        this.pooledEngine = pooledEngine;
        this.spriteBatch = spriteBatch;
        this.renderer = player.getGameMap().getRenderer();
        this.rayHandler = player.getGameMap().getRayHandler();
        this.player = player;
        this.viewport = viewport;
        playerCamera = (OrthographicCamera) player.getCamera();
        this.mainGame = mainGame;
    }

    @Override
    public void show() {
        viewport.setCamera(playerCamera);
        debugFont = new BitmapFont();
        debugFont.setColor(Color.GOLD);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(playerCamera.combined);
        debugSpriteBatch = new SpriteBatch();

        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        //pooledEngine.getEntitiesFor(Family.all(TextureComponent.class).get()).forEach(entity -> Mappers.textures.get(entity).shape.setProjectionMatrix(playerCamera.combined));
        spriteBatch.setProjectionMatrix(playerCamera.combined);

        renderer.setView(playerCamera);
        renderer.render();

        pooledEngine.update(Gdx.graphics.getDeltaTime());

        debugSpriteBatch.begin();
        Vector2 mouse = player.getAttributes().get(Attributes.WORLD_MOUSE_POSITION);
        debugFont.draw(debugSpriteBatch, "Position "+player.getMovementComponent().pos.toString(), 15, 15);
        debugFont.draw(debugSpriteBatch, "Mouse "+mouse, 15, 30);
        debugSpriteBatch.end();

        rayHandler.setCombinedMatrix(playerCamera.combined);
        rayHandler.updateAndRender();

        if(player.getAttributes().hasKey(Attributes.NPC_HOVER)) {
            String hover = player.getAttributes().get(Attributes.NPC_HOVER);

            spriteBatch.begin();
            spriteBatch.setProjectionMatrix(playerCamera.combined);
            font.draw(spriteBatch, hover, mouse.x, mouse.y+12);
            spriteBatch.end();

            player.getAttributes().remove(Attributes.NPC_HOVER);
        }

        if(player.getAttributes().hasKey(Attributes.CURRENT_DIALOGUE)) {
            player.getAttributes().get(Attributes.CURRENT_DIALOGUE).render(delta);
        }

        player.getAttributes().get(Attributes.ALL_OPENED_UI).forEach(baseUI -> baseUI.render(player, delta));

        player.getGameMap().drawLine(playerCamera);
        /*if(player.getGameMap().waypoints != null) {
            if (waypoint == null && !player.getGameMap().waypoints.isEmpty()) {
                waypoint = player.getGameMap().waypoints.pop();
            }
            if (waypoint != null) {
                Vector2 myvect = new Vector2((int) player.getMovementComponent().pos.x, (int) player.getMovementComponent().pos.y);
                Vector2 waypointVect = new Vector2((int)waypoint.x * 32, (int)waypoint.y * 32);
                Direction direction = MovementSystem.getDirection(waypointVect, myvect);
                if (!myvect.equals(waypointVect)) {
                    player.getMovementComponent().move(direction);
                } else if (!player.getGameMap().waypoints.isEmpty()) {
                    waypoint = player.getGameMap().waypoints.pop();
                } else {
                    waypoint = null;
                }
            }
        }*/
    }

    Vector2 waypoint;

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        if(player.getAttributes().hasKey(Attributes.CURRENT_DIALOGUE)) {
            player.getAttributes().get(Attributes.CURRENT_DIALOGUE).resize(width, height);
        }

        player.getAttributes().get(Attributes.ALL_OPENED_UI).forEach(baseUI -> baseUI.resize(width,height));
    }
    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        player.getGameMap().dispose();
    }
}
