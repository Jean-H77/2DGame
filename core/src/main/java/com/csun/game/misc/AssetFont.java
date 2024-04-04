package com.csun.game.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class AssetFont {

    private BitmapFont nightCore32;
    private static AssetFont instance;

    public static AssetFont getInstance(){
        if(instance == null){
            instance = new AssetFont();
            instance.initialize();
        }
        return instance;
    }

    private void initialize(){
        nightCore32 = createFont(32);
    }

    private BitmapFont createFont(int fontSize){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Nightcore Demo.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = fontSize;
        parameter.color = Color.RED;


        return generator.generateFont(parameter);
    }


    public BitmapFont getNightCore32() {
        return nightCore32;
    }
}
