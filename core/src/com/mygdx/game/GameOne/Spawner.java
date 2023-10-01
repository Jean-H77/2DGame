package com.mygdx.game.GameOne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameOne.GameObject;

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Spawner {

    float countSpawnZombieTime = 0;
    float spawnZombieTime = 3;

    float countSpawnGhostTime = 0;
    float spawnGhostTime = 5;

    float countSpawnMMTime = 0;
    float spawnMMTime = 8;

    Player player;

    public Spawner(Player player){
        this.player = player;
    }

    Vector2 getRandomPos(){
        int width = 16 * 160;
        int height = 16 * 90;

        Random random = new Random();
        return new Vector2(random.nextInt(width - 200),random.nextInt(height - 200));

    }

    public void spawnEnemy(List<GameObject> objects){

        countSpawnZombieTime += Gdx.graphics.getDeltaTime();
        if(countSpawnZombieTime >= spawnZombieTime){
            countSpawnZombieTime = 0;
            objects.add(new Zombie(getRandomPos(),player));
        }

        countSpawnGhostTime += Gdx.graphics.getDeltaTime();
        if(countSpawnGhostTime >= spawnGhostTime){
            countSpawnGhostTime = 0;
            objects.add(new Ghost(getRandomPos(),player));
        }

        countSpawnMMTime += Gdx.graphics.getDeltaTime();
        if(countSpawnMMTime >= spawnMMTime){
            countSpawnMMTime = 0;
            objects.add(new Mummy(getRandomPos(),player));
        }

    }

}
