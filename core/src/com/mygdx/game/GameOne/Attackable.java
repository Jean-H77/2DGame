package com.mygdx.game.GameOne;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Attackable extends GameObject {

    int health = 5;
    protected int knockBackDist = 50;

    public Attackable(Vector2 pos, Texture img) {
        super(pos, img);
    }

    public void hit(int damage){
        health -= damage;
        if(health <= 0){
            destroyFlag = true;
        }
    }
    public void knockBack(Vector2 target){
        if(target.x > pos.x){
            pos.x -= knockBackDist;
        }
        else{
            pos.x += knockBackDist;
        }
    }
}
