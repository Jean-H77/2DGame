package com.csun.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ai.paths.PathFinder;
import com.csun.game.ai.paths.PathResult;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.*;
import com.csun.game.map.GameMap;
import com.csun.game.models.AnimationType;
import com.csun.game.models.Systems;
import com.csun.game.player.Player;
import com.csun.game.screens.FadeScreen;
import com.csun.game.screens.GameScreen;
import com.csun.game.screens.MenuScreen;
import com.csun.game.screens.TitleScreen;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.ArrayDeque;
import java.util.concurrent.CompletableFuture;

import static com.csun.game.GameConstants.TILE_SIZE;

public class MainGame extends Game {

    private final Injector injector;
    private PooledEngine pooledEngine;

    @Inject
    public MainGame(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void create() {
        PooledEngine engine = injector.getInstance(PooledEngine.class);
        this.pooledEngine = engine;

        injector.getInstance(Systems.class)
            .list()
            .forEach(it -> engine.addSystem(injector.getInstance(it)));

        switchScreen(ScreenType.TITLE_SCREEN);
    }

    public void switchScreen(ScreenType screenType) {
        switch (screenType) {
            case GAME_SCREEN -> setScreen(injector.getInstance(GameScreen.class));
            case MENU_SCREEN -> setScreen(injector.getInstance(MenuScreen.class));
            case TITLE_SCREEN -> setScreen(injector.getInstance(TitleScreen.class));
        }
    }

    public CompletableFuture<Void> loadGameScreen() {
       return CompletableFuture.runAsync(() -> {
           Player player = injector.getInstance(Player.class);
           player.setDefaults()
               .load()
               .thenRun(() -> Gdx.app.postRunnable(() -> {
                   player.postLoad();
                   switchScreen(ScreenType.GAME_SCREEN);
               }));
       });
    }

    public enum ScreenType {
        GAME_SCREEN,
        TITLE_SCREEN,
        MENU_SCREEN
        ;
    }

    public void fadeGameScreen() {
        GameScreen gameScreen = injector.getInstance(GameScreen.class);
        FadeScreen.fade(gameScreen, gameScreen, this);
    }
    public void fadeGameScreen(String text) {
        GameScreen gameScreen = injector.getInstance(GameScreen.class);
        FadeScreen.fade(gameScreen, gameScreen, this, text);
    }

    public PooledEngine getPooledEngine() {
        return pooledEngine;
    }

    // ok this is a pretty ugly way to handle this, but we don't have a lot of time
    public void addNpc(Vector2 pos, float width, float height, String identification) {
        if(getNpcEntity(identification) != null) return;
        Gdx.app.log("NPC ADD", identification);
        Entity entity = pooledEngine.createEntity();
        entity.add(new MovementComponent(pos));
        entity.add(new ClickableComponent(new Rectangle(pos.x, pos.y, width, height), identification));
        entity.add(new AnimationComponent(AnimationType.PLAYER_IDLE_EAST, width, height));
        NpcComponent npcComponent = new NpcComponent();
        npcComponent.width = width;
        npcComponent.height = height;
        entity.add(npcComponent);
        pooledEngine.addEntity(entity);
    }

    // going to rewrite how we add/remove npcs, etc. later, just doing this to get something out
    // so we can start writing quests
    public void removeNpc(String identification) {
        Entity entity = getNpcEntity(identification);
        if(entity != null) {
            pooledEngine.removeEntity(entity);
        }
    }

    public void walkNpc(String identification, float tileX, float tileY) {
        Entity entity = getNpcEntity(identification);
        if(entity != null) {
            MovementComponent movementComponent = Mappers.movements.get(entity);
            PathResult pathResult = PathFinder.findPath(
                new Vector2(
                    movementComponent.pos.x / TILE_SIZE,
                    movementComponent.pos.y / TILE_SIZE
                ),
                new Vector2((int)(tileX/ TILE_SIZE), (int)(tileY/TILE_SIZE)),
                injector.getInstance(GameMap.class));
            movementComponent.setPath(new ArrayDeque<>(pathResult.waypoints()));
        }
    }

    public void moveNpc(String identification, Vector2 newPos) {
        Entity entity = getNpcEntity(identification);
        if(entity == null) {
            return;
        }
        MovementComponent movementComponent = Mappers.movements.get(entity);
        movementComponent.pos = newPos;
        ClickableComponent clickableComponent = Mappers.clickables.get(entity);
        clickableComponent.bounds.x = newPos.x;
        clickableComponent.bounds.y = newPos.y;
    }

    public Entity getNpcEntity(String identification) {
        ImmutableArray<Entity> entities = pooledEngine.getEntitiesFor(
            Family.all(NpcComponent.class, MovementComponent.class, ClickableComponent.class, AnimationComponent.class).
                get());

        for(Entity e : entities) {
            ClickableComponent clickableComponent = Mappers.clickables.get(e);
            if(clickableComponent.identification.equalsIgnoreCase(identification)) {
                return e;
            }
        }
        return null;
    }

    public void npcSay(String statement, String identification, int seconds) {
        Entity entity = getNpcEntity(identification);
        if(entity == null) {
            return;
        }
        NpcComponent npcComponent = Mappers.npcs.get(entity);
        npcComponent.say = statement;
        npcComponent.time = seconds;
    }

    public Vector2 getNpcPosition(String identification) {
        Entity entity = getNpcEntity(identification);
        return entity == null ? null : Mappers.movements.get(entity).pos;
    }
}
