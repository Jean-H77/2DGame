package com.csun.game.map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.csun.game.npc.Npc;
import com.csun.game.npc.NpcManager;
import com.google.inject.Inject;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.csun.game.GameConstants.TILE_SIZE;

public class MapCache {

    private final NpcManager npcManager;
    private final HashMap<TiledMap , MapEntry> tiledMaps = new HashMap<>();

    @Inject
    public MapCache(NpcManager npcManager) {
        this.npcManager = npcManager;
    }

    private boolean[][] loadMapGrid(TiledMap tiledMap) {
        TiledMapTileLayer layer = MapUtils.getTileLayer(tiledMap, 1);
        boolean[][] grid = new boolean[(int) MapUtils.getMapWidth(tiledMap)][(int) MapUtils.getMapHeight(tiledMap)];

        if(layer != null) {
            for (boolean[] booleans : grid) {
                Arrays.fill(booleans, true);
            }

            for (int i = 0; i < grid.length; i++) {
                for (int k = 0; k < grid[i].length; k++) {
                    if (layer.getCell(i / TILE_SIZE, k / TILE_SIZE) == null) continue;
                    grid[i][k] = !layer.getCell(i / TILE_SIZE, k / TILE_SIZE).getTile().getProperties().containsKey("blocked");
                }
            }
        }

        return grid;
    }

    private Door[] loadDoors(TiledMap tiledMap) {
        MapObjects mapObjects = MapUtils.getTileObjectLayer(tiledMap, "door");
        Stream<MapObject> stream = StreamSupport.stream(mapObjects.spliterator(), true);

        return stream
            .filter(it -> it instanceof RectangleMapObject)
            .map(obj -> new Door(((RectangleMapObject) obj).getRectangle(), obj.getProperties()))
            .toArray(Door[]::new);
    }

    public Entity[] loadNPCs(String name) {
        String mapName = name.split(".tmx")[0];
        List<Entity> entities = new ArrayList<>();
        for(Map.Entry<String, Npc> entry : npcManager.getNPCs().entrySet()) {
            Npc val = entry.getValue();
            if(val.getMap().equalsIgnoreCase(mapName)) {
                entities.add(val.getEntity());
            }
        }
        return entities.toArray(Entity[]::new);
    }

    private MapEntry putEntry(String name, TiledMap tiledMap) {
        Door[] doors = loadDoors(tiledMap);
        boolean[][] grid = loadMapGrid(tiledMap);
        Entity[] npcs = loadNPCs(name);
        return new MapEntry(grid, npcs, doors);
    }

    public MapEntry getMapEntry(String name, TiledMap tiledMap) {
        return tiledMaps.computeIfAbsent(tiledMap, x -> putEntry(name, tiledMap));
    }
}
