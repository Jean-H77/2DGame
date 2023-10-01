package com.mygdx.game.GameOne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class MSound {

    HashMap<String, Sound> sounds = new HashMap();

    public MSound(){
        sounds.put("drop_ok",Gdx.audio.newSound(Gdx.files.internal("sounds/ok.wav")));
        sounds.put("drop_ng",Gdx.audio.newSound(Gdx.files.internal("sounds/ng.wav")));

        sounds.put("sword1",Gdx.audio.newSound(Gdx.files.internal("sounds/sword1.mp3")));
        sounds.put("sword2",Gdx.audio.newSound(Gdx.files.internal("sounds/sword2.mp3")));
        sounds.put("sword3",Gdx.audio.newSound(Gdx.files.internal("sounds/sword3.mp3")));
        sounds.put("enemy_damage",Gdx.audio.newSound(Gdx.files.internal("sounds/enemy_damage.mp3")));

        sounds.put("music",Gdx.audio.newSound(Gdx.files.internal(  "sounds/music2.mp3")));
        sounds.put("gameover",Gdx.audio.newSound(Gdx.files.internal(  "sounds/gameover.wav")));

        sounds.get("music").stop();
        sounds.get("music").loop();
    }

    public void play(String sound,float volume){
        sounds.get(sound).play(volume);
    }

    public void stop(String sound){
        sounds.get(sound).stop();
    }
}
