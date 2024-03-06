package com.csun.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.ClickableComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.NpcComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.csun.game.models.Systems;
import com.csun.game.player.Player;
import com.csun.game.screens.FadeScreen;
import com.csun.game.screens.GameScreen;
import com.csun.game.screens.MenuScreen;
import com.csun.game.screens.TitleScreen;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.concurrent.CompletableFuture;

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

    public PooledEngine getPooledEngine() {
        return pooledEngine;
    }

    // ok this is a pretty ugly way to handle this, but we don't have a lot of time
    public void addNpc(Vector2 pos, float width, float height, String identification) {
        if(getNpcEntity(identification) != null) return;
        Entity entity = pooledEngine.createEntity();
        entity.add(new MovementComponent(pos));
        entity.add(new ClickableComponent(new Rectangle(pos.x, pos.y, width, height), identification));
        entity.add(new TextureComponent());
        entity.add(new NpcComponent());
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
        Gdx.app.log("Size", String.valueOf(pooledEngine.getEntities().size()));
        ImmutableArray<Entity> entities = pooledEngine.getEntitiesFor(
            Family.all(NpcComponent.class, MovementComponent.class, ClickableComponent.class, TextureComponent.class).
                get());
        Gdx.app.log("Size", String.valueOf(entities.size()));

        for(Entity e : entities) {
            ClickableComponent clickableComponent = Mappers.clickables.get(e);
            Gdx.app.log("Searching", clickableComponent.identification);
            if(clickableComponent.identification.equalsIgnoreCase(identification)) {
                return e;
            }
        }
        return null;
    }
}
