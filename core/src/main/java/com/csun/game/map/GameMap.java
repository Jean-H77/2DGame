package com.csun.game.map;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.csun.game.misc.Assets;
import com.csun.game.npc.NpcManager;
import com.google.inject.Inject;
import org.javatuples.Quintet;

import java.util.*;

import static com.csun.game.GameConstants.TILE_SIZE;

public class GameMap {
    private final AssetManager assetManager = Assets.INSTANCE.getManager();
    private final MapCache mapCache;
    private final HashMap<String, Color> lightColors = new HashMap<>();
    private final HashMap<TiledMap, List<Quintet<Integer, Color, Integer, Integer, Integer>>> lightPoints = new HashMap<>();
    private final World world = new World(new Vector2(0, 0), false);
    private final RayHandler rayHandler = new RayHandler(world);
    private final PooledEngine engine;
    private final NpcManager npcManager;

    private OrthogonalTiledMapRenderer renderer;
    private TiledMap currentMap;
    private Rectangle mapBoundaries;
    private Door[] doors;
    boolean[][] grid;
    private Entity[] npcs;

    @Inject
    public GameMap(MapCache mapCache, PooledEngine engine, NpcManager npcManager) {
        this.mapCache = mapCache;
        this.engine = engine;
        this.npcManager = npcManager;
    }

    public void enterMap(String map, float ambientLight) {
        TiledMap tiledMap = getMap(map);
        setCurrentMap(map, tiledMap);
        setAmbientLight(ambientLight);
        setMapLights(tiledMap);
    }

    private TiledMap getMap(String map) {
        return assetManager.get(map);
    }

    private void setMapLights(TiledMap tiledMap) {
        if(lightPoints.containsKey(tiledMap)) {
            List<Quintet<Integer, Color, Integer, Integer, Integer>> points = lightPoints.get(tiledMap);

            for (Quintet<Integer, Color, Integer, Integer, Integer> point : points) {
                new PointLight(rayHandler,
                    point.getValue0(),
                    point.getValue1(),
                    point.getValue2(),
                    point.getValue3(),
                    point.getValue4());
            }
            return;
        }

        List<Quintet<Integer, Color, Integer, Integer, Integer>> points = lightPoints.computeIfAbsent(tiledMap, x -> new ArrayList<>());

        tiledMap.getLayers()
            .forEach(it -> {
                if(it instanceof TiledMapTileLayer tileLayer) {
                    for(int height = 0; height < tileLayer.getHeight(); height++) {
                        for(int width = 0; width < tileLayer.getWidth(); width++) {
                            if(tileLayer.getCell(width, height) == null) continue;
                            TiledMapTile tiledMapTile = tileLayer.getCell(width, height).getTile();
                            if(tiledMapTile.getProperties().containsKey("bike")) {
                                String type = String.valueOf(tiledMapTile.getProperties().get("type"));
                                Gdx.app.log("Type", type);
                                String hex = String.valueOf(tiledMapTile.getProperties().get("bike"));
                                Color color = lightColors.computeIfAbsent(hex, x -> Color.valueOf(hex));
                                int rays = (int) tiledMapTile.getProperties().get("rays");
                                int radius = (int) tiledMapTile.getProperties().get("radius");
                                int x = (int) (width * TILE_SIZE + ((float) TILE_SIZE /2));
                                int y = height * TILE_SIZE + TILE_SIZE;
                                points.add(new Quintet<>(rays, color, radius, x, y));
                                if(Objects.equals(type, "conelight")) {
                                    new ConeLight(rayHandler,
                                        rays,
                                        color,
                                        radius,
                                        x,
                                        y,
                                        90.0f,
                                        90.0f);
                                } else {
                                    new PointLight(rayHandler,
                                        rays,
                                        color,
                                        radius,
                                        x,
                                        y);
                                }
                            }
                        }
                    }
                }
            });
    }

    public TiledMap getCurrentMap() {
        return currentMap;
    }

    private void setCurrentMap(String map, TiledMap currentMap) {
        this.currentMap = currentMap;
        mapBoundaries = new Rectangle(0, 0, MapUtils.getMapWidth(currentMap), MapUtils.getMapHeight(currentMap));
        MapEntry entry = mapCache.getMapEntry(map, currentMap);
        npcManager.clearVisible();
        doors = entry.doors();
        grid = entry.grid();
        npcs = entry.entities();
        npcManager.spawnAll(npcs);
        if(renderer == null) {
            renderer = new OrthogonalTiledMapRenderer(currentMap);
            return;
        }
        renderer.setMap(currentMap);
    }

    public void clearNpcs() {
        Arrays
            .stream(npcs)
            .forEach(engine::removeEntity);
    }

    public void populateNpcs() {

    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public void dispose() {
        currentMap.dispose();
        renderer.dispose();
        rayHandler.dispose();
    }

    public Door[] getDoors() {
        return doors;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public void setGrid(boolean[][] grid) {
        this.grid = grid;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return MapUtils.getTileLayer(currentMap, 1);
    }

    public void setRenderer(OrthogonalTiledMapRenderer renderer) {
        this.renderer = renderer;
    }

    public Rectangle getBoundaries() {
        return mapBoundaries;
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    public void setAmbientLight(float ambientLight) {
        rayHandler.setAmbientLight(ambientLight);
    }

    public HashMap<String, Color> getLightColors() {
        return lightColors;
    }

    public HashMap<TiledMap, List<Quintet<Integer, Color, Integer, Integer, Integer>>> getLightPoints() {
        return lightPoints;
    }

    public World getWorld() {
        return world;
    }

    public Rectangle getMapBoundaries() {
        return mapBoundaries;
    }

    public void setMapBoundaries(Rectangle mapBoundaries) {
        this.mapBoundaries = mapBoundaries;
    }

}
