package com.mygdx.game.GameOne;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Blood extends GameObject {

    SpriteSheet sheet;
    public Blood(Vector2 pos){
        super(pos,null);
        sheet = new SpriteSheet(new Texture("gameOne/blood.png"),6,6);
        sheet.setPlay(0, 35, 0.01f, false);

    }

    @Override
    public void update() {
        super.update();
        if(sheet.current >= 34){
            destroyFlag = true;
        }
        sheet.play();
    }

    @Override
    public void draw(Batch batch) {
        TextureRegion t = sheet.getCurrentFrame();
        super.drawImg(new Vector2(pos.x,pos.y),batch,t);

    }
}
