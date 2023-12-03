package com.csun.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Optional;

public class GameMap {

    private final HashMap<String, TiledMap> tiledMaps = new HashMap<>();

    private final OrthographicCamera mapCamera; //map camera might be needed
    private OrthogonalTiledMapRenderer renderer;

    private TiledMap currentMap;

    @Inject
    public GameMap(OrthographicCamera orthographicCamera) {
        this.mapCamera = orthographicCamera;
        //remove later
        loadMap("tempmap.tmx");
    }

    public HashMap<String, TiledMap> getTiledMaps() {
        return tiledMaps;
    }

    public OrthographicCamera getMapCamera() {
        return mapCamera;
    }

    public void loadMap(String path) {
        if(tiledMaps.containsKey(path)) {
            setCurrentMap(tiledMaps.get(path));
            return;
        }
        TiledMap temp = new TmxMapLoader().load(path);
        tiledMaps.put(path, temp);
        setCurrentMap(temp);
    }

    public TiledMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(TiledMap currentMap) {
        this.currentMap = currentMap;
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
    }

    public Optional<TiledMapTileLayer> getLayer(int type) {
        return currentMap == null ? Optional.empty() :
            Optional.ofNullable((TiledMapTileLayer) currentMap.getLayers().get(type));
    }

    public void setRenderer(OrthogonalTiledMapRenderer renderer) {
        this.renderer = renderer;
    }
}
