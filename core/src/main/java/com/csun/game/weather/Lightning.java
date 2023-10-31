package com.mygdx.game.GameOne.weather;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameOne.Utils;

public class Lightning extends Weather {

    float countTime = 0;

    public Lightning(){

    }

    public static int randInt(int min, int max) {

        int randomNum = Utils.get().random.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public void update(){
        countTime += Gdx.graphics.getDeltaTime();
        float y = Utils.get().cameraPos.y;
        if(countTime >= 0.5){
            particles.add(new LightningParticle(new Vector2(Utils.get().random.nextInt(160 * 16),
                    randInt((int) (y - 500), (int) (y - 100))
                    ))
            );
            countTime= 0;
        }

        for(Particle p : particles){
            p.update();
        }
    }

}
