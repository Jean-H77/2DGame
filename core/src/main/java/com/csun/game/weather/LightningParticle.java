package com.csun.game.weather;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameOne.SpriteSheet;
import com.mygdx.game.GameOne.Utils;

public class LightningParticle extends Particle {
    SpriteSheet sheet;



    public LightningParticle(Vector2 pos) {
        super(pos, null);
        sheet = new SpriteSheet(new Texture("gameOne/lightning.png"),1,5);
        sheet.setPlay(0, 4, 0.01f, false);
    }

    @Override
    public void update() {

        sheet.play();
        if(sheet.getCurrent() >= 4){
            destroyFlag = true;
        }
    }

    @Override
    public void draw(Batch batch) {
        Utils.get().batch.begin();
        TextureRegion t = sheet.getCurrentFrame();
        super.drawImg(new Vector2(pos.x,pos.y),Utils.get().batch,t);
        Utils.get().batch.end();
    }
}
