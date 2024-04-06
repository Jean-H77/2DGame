package com.csun.game.map;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MapUtils {

    public static float getMapWidth(TiledMap tiledMap) {
        return tiledMap.getProperties().get("width", Integer.class) * tiledMap.getProperties().get("tilewidth", Integer.class);
    }

    public static float getMapHeight(TiledMap tiledMap) {
        return tiledMap.getProperties().get("height", Integer.class) * tiledMap.getProperties().get("tileheight", Integer.class);
    }

    public static TiledMapTileLayer getTileLayer(TiledMap tiledMap, int type) {
        if(tiledMap == null || tiledMap.getLayers().size() <= type) {
            return null;
        }
        return (TiledMapTileLayer) tiledMap.getLayers().get(type);
    }

    public static MapObjects getTileObjectLayer(TiledMap tiledMap, String name) {
        return tiledMap == null ? null : tiledMap.getLayers().get(name).getObjects();
    }
}
