package com.mygdx.game.GameOne;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends Attackable {
    public  Enemy(Vector2 pos){
        super(pos,null);
        knockBackDist = 20;
    }

}
