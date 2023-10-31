package com.csun.game.weather;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameOne.Utils;

public class Snow extends Weather {

    float countTime = 0;

    public Snow(){

    }



    public void update(){
        countTime += Gdx.graphics.getDeltaTime();
        float y = Utils.get().cameraPos.y;
        if(countTime >= 0.1){
            particles.add(new SnowParticle(new Vector2(Utils.get().random.nextInt(160 * 16),
                    Utils.get().random.nextFloat(10)

                    ))

            );
            countTime= 0;
        }

        for(Particle p : particles){
            p.update();
        }

    }

}
