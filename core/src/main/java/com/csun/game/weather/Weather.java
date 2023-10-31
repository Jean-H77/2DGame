package com.mygdx.game.GameOne.weather;

import com.mygdx.game.GameOne.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Weather {

    List<Particle> particles = new ArrayList<>();

    public void update(){

    }

    public void draw(){
        for(Particle particle : particles){
            particle.draw(null);
        }
        for(int i = 0; i < particles.size();i++){
            if(particles.get(i).destroyFlag){
                particles.remove(i);
            }
        }
    }

}
