package com.csun.game.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundManager {

    HashMap<String, Sound> sounds = new HashMap();
    public float masterVolume = 1;
    public float effectVolume = 1;
    public float musicVolume = 1;

    static SoundManager instance;

    private SoundManager(){
        sounds.put("click",Gdx.audio.newSound(Gdx.files.internal("sounds/clickBtn.wav")));

        sounds.put("music",Gdx.audio.newSound(Gdx.files.internal(  "sounds/music.mp3")));

        sounds.get("music").stop();
        //sounds.get("music").loop();
    }

    public void play(String sound,float volume){
        sounds.get(sound).play(volume);
    }

    public void playMusic(String soundId){
        Sound sound = sounds.get(soundId);
        sound.stop();
        float volume = lerp(0,masterVolume,musicVolume);
        sound.loop(volume);
    }
    public void playSound(String soundId){
        Sound sound = sounds.get(soundId);
        float volume = lerp(0,masterVolume,effectVolume);
        sound.play(volume);
    }

    public void stop(String sound){
        sounds.get(sound).stop();
    }
    private float lerp(float a,float  b,float  f)
    {
        return a + f * (b - a);
    }

    public static SoundManager getInstance()
    {
        if (instance == null)
            instance = new SoundManager();
        return instance;
    }
}
