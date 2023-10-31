package com.csun.game.weather;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameOne.Utils;

public class RainParticle extends Particle {
    float speed;
    float deathTimeCount;

    public RainParticle(Vector2 pos) {
        super(pos, new Texture("gameOne/rainParticle.png"));

        speed = Utils.get().random.nextFloat(5,20);
        deathTimeCount = Utils.get().random.nextFloat(1,5);

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
