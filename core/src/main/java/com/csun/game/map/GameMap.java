package com.csun.game.map;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.google.inject.Inject;
import org.javatuples.Quintet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.csun.game.GameConstants.TILE_SIZE;

public class GameMap {

    private static final HashMap<String , TiledMap> tiledMaps = new HashMap<>();
    private final HashMap<String, Color> lightColors = new HashMap<>();
    private final HashMap<TiledMap, List<Quintet<Integer, Color, Integer, Integer, Integer>>> lightPoints = new HashMap<>();
    private final RayHandler rayHandler;
    private final World world;
    private final OrthographicCamera mapCamera; //map camera might be needed

    private OrthogonalTiledMapRenderer renderer;
    private TiledMap currentMap;
    private Rectangle mapBoundaries;

    @Inject
    public GameMap(OrthographicCamera orthographicCamera, RayHandler rayHandler, World world) {
        this.mapCamera = orthographicCamera;
        this.rayHandler = rayHandler;
        this.world = world;
        //remove later
        enterMap("tempmap.tmx", 1f);
    }

    public HashMap<String, TiledMap> getTiledMaps() {
        return tiledMaps;
    }

    public OrthographicCamera getMapCamera() {
        return mapCamera;
    }

    public boolean enterMap(String map, float ambientLight) {
        TiledMap tiledMap = getMap(map);
        setAmbientLight(ambientLight);
        setMapLights(tiledMap);
        setCurrentMap(tiledMap);
        return true;
    }

    private TiledMap getMap(String map) {
        TiledMap tiledMap;
        if(tiledMaps.containsKey(map)) {
            tiledMap = tiledMaps.get(map);
        } else {
            tiledMap = new TmxMapLoader().load(map);
            tiledMaps.put(map, tiledMap);
        }
        return tiledMap;
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
                            if(tiledMapTile.getProperties().containsKey("color")) {
                                String hex = String.valueOf(tiledMapTile.getProperties().containsKey("color"));
                                Color color = lightColors.computeIfAbsent(hex, x -> Color.valueOf(hex));
                                int rays = (int) tiledMapTile.getProperties().get("rays");
                                int radius = (int) tiledMapTile.getProperties().get("radius");
                                int x = (int) (width * TILE_SIZE + ((float) TILE_SIZE /2));
                                int y = height * TILE_SIZE + TILE_SIZE;
                                points.add(new Quintet<>(rays, color, radius, x, y));
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
            });
    }

    private TiledMap getCurrentMap() {
        return currentMap;
    }

    private void setCurrentMap(TiledMap currentMap) {
        this.currentMap = currentMap;
        mapBoundaries = new Rectangle(0, 0, getCurrentMapWidth(), getCurrentMapHeight());
        if(renderer == null) {
            renderer = new OrthogonalTiledMapRenderer(currentMap);
            return;
        }
        renderer.setMap(currentMap);
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public float getCurrentMapWidth() {
        return currentMap.getProperties().get("width", Integer.class) * currentMap.getProperties().get("tilewidth", Integer.class);
    }

    public float getCurrentMapHeight() {
        return currentMap.getProperties().get("height", Integer.class) * currentMap.getProperties().get("tileheight", Integer.class);
    }

    public void dispose() {
        currentMap.dispose();
        renderer.dispose();
        rayHandler.dispose();
    }

    public Optional<TiledMapTileLayer> getLayer(int type) {
        return currentMap == null ? Optional.empty() : Optional.ofNullable((TiledMapTileLayer) currentMap.getLayers().get(type));
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
