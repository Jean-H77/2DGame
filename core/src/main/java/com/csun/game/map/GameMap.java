package com.csun.game.map;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.csun.game.ai.paths.PathResult;
import com.csun.game.misc.Assets;
import org.javatuples.Quintet;

import java.util.*;

import static com.csun.game.GameConstants.TILE_SIZE;

public class GameMap {
    private final AssetManager assetManager = Assets.INSTANCE.getManager();
    private final HashMap<String , TiledMap> tiledMaps = new HashMap<>();
    private final HashMap<TiledMap, Integer[][]> tiledMapGrid = new HashMap<>();
    private final HashMap<String, Color> lightColors = new HashMap<>();
    private final HashMap<TiledMap, List<Quintet<Integer, Color, Integer, Integer, Integer>>> lightPoints = new HashMap<>();
    private final World world = new World(new Vector2(0, 0), false);
    private final RayHandler rayHandler = new RayHandler(world);

    private OrthogonalTiledMapRenderer renderer;
    private TiledMap currentMap;
    private Rectangle mapBoundaries;

    public HashMap<String, TiledMap> getTiledMaps() {
        return tiledMaps;
    }

    public void enterMap(String map, float ambientLight) {
        TiledMap tiledMap = getMap(map);
        setCurrentMap(tiledMap);
        setAmbientLight(ambientLight);
        setMapLights(tiledMap);
        if(tiledMapGrid.containsKey(tiledMap)) {
            return;
        }

        getLayer(1).ifPresent(tileLayer -> {
            Integer[][] grid = new Integer[(int) getCurrentMapHeight()][(int) getCurrentMapWidth()];
            for(int i = 0; i < grid.length; i++) {
                for(int k = 0; k < grid[i].length; k++) {
                    if(tileLayer.getCell(i, k) != null && tileLayer.getCell(i, k).getTile().getProperties().containsKey("blocked")) {
                        grid[i][k] = -1;
                    } else {
                        grid[i][k] = 1;
                    }
                }
            }

            tiledMapGrid.put(tiledMap, grid);
        });
    }

    public PathResult pathResult;
    public Deque<Vector2> waypoints = new ArrayDeque<>();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    public void drawLine(Camera camera) {
        if(pathResult == null) return;
        List<Vector2> path = new ArrayList<>(pathResult.path());
        List<Vector2> waypoints = new ArrayList<>(pathResult.waypoints());

        if (path.isEmpty()) return;

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.RED);
        for (int i = 0; i < path.size() - 1; i++) {
            Vector2 point1 = path.get(i);
            Vector2 point2 = path.get(i + 1);
            shapeRenderer.rectLine(point1.x * TILE_SIZE, point1.y * TILE_SIZE, point2.x * TILE_SIZE, point2.y * TILE_SIZE, 10);
        }

        shapeRenderer.setColor(Color.BLUE);
        for (Vector2 waypoint : waypoints) {
            shapeRenderer.circle(waypoint.x * TILE_SIZE, waypoint.y * TILE_SIZE, 10);
        }

        shapeRenderer.end();
    }

    private TiledMap getMap(String map) {
        Gdx.app.log("Map", map);
        return assetManager.get(map);
        /*TiledMap tiledMap;
        if(tiledMaps.containsKey(map)) {
            tiledMap = tiledMaps.get(map);
        } else {
            tiledMap = new TmxMapLoader().load(map);
            tiledMaps.put(map, tiledMap);
        }
        return tiledMap;*/
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
        if(currentMap == null || currentMap.getLayers().size() <= type) {
            return Optional.empty();
        }
        return Optional.ofNullable((TiledMapTileLayer) currentMap.getLayers().get(type));
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

    public HashMap<TiledMap, Integer[][]> getTiledMapGrid() {
        return tiledMapGrid;
    }
}
