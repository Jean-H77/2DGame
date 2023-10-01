package com.mygdx.game.GameOne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Mummy extends Enemy {

    SpriteSheet sheet;
    SpriteSheet runSheet;
    SpriteSheet attackSheet;
    float speed = 2;
    Vector2 vel = new Vector2(0,0);
    Player player;
    boolean flip =false;
    boolean attack = false;

    float countAttackTime = 0;

    public Mummy(Vector2 pos, Player player) {
        super(pos);
        health = 10;

        runSheet = new SpriteSheet(new Texture("gameOne/mummy.png"),1,1);
        runSheet.setPlay(0, 0, 0.15f, true);


        sheet = runSheet;
        this.player = player;
    }

    void flipAllSheet(boolean flipX){
        runSheet.flip(flipX);
//        attackSheet.flip(flipX);
    }

    public Rect getRect(){
        TextureRegion t = sheet.getCurrentFrame();
        if((sheet == attackSheet) && flip){
            return new Rect(pos.x - 50,pos.y,t.getRegionWidth(),t.getRegionHeight());
        }
        return new Rect(pos.x,pos.y,t.getRegionWidth(),t.getRegionHeight());
    }
    @Override
    public void update() {
        super.update();
        sheet.play();

        float x = 0;
        if(pos.x < player.pos.x){
            x = 1;
        }
        else {
            x = -1;
        }

        if(!flip && x < 0){
            flipAllSheet(true);
            flip = true;
        }
        if(flip && x > 0){
            flipAllSheet(true);
            flip = false;
        }
    }

    public void attack(List<GameObject> addedObjects){
        countAttackTime += Gdx.graphics.getDeltaTime();
        if(countAttackTime >= 2){
            countAttackTime = 0;
            addedObjects.add(new MummyProjectile(new Vector2(pos.x,pos.y),player));
        }
    }

    @Override
    public void draw(Batch batch) {
        TextureRegion t = sheet.getCurrentFrame();
        if((sheet == attackSheet) && flip){
            super.drawImg(new Vector2(pos.x - 50,pos.y),batch,t);
            return;
        }
        super.drawImg(new Vector2(pos.x,pos.y),batch,t);

    }
}
