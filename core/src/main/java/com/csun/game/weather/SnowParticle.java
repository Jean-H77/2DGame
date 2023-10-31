package com.csun.game.weather;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameOne.Utils;

public class SnowParticle extends Particle {
    float speed;
    float deathTimeCount;

    public SnowParticle(Vector2 pos) {
        super(pos, new Texture("gameOne/snowParticle.png"));

        speed = Utils.get().random.nextFloat(1,3);
        deathTimeCount = Utils.get().random.nextFloat(1,10);

    }

    @Override
    public void update() {

        pos.y += speed;


        deathTimeCount -= Gdx.graphics.getDeltaTime();
        if(deathTimeCount < 0){
            destroyFlag = true;
        }
    }
}
