package com.csun.game.npc;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class NpcJson {

    private String map;
    private String type;
    private Vector2 coords;
    private String id;

    public NpcJson(String map, String type, Vector2 coords, String id) {
        this.map = map;
        this.type = type;
        this.coords = coords;
        this.id = id;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Vector2 getCoords() {
        return coords;
    }

    public void setCoords(Vector2 coords) {
        this.coords = coords;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public record NpcJsonList(List<NpcJson> npcJsonList) {}
}
