package com.mygdx.game.GameOne;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Ghost extends Enemy {

    SpriteSheet sheet;
    SpriteSheet runSheet;
    SpriteSheet attackSheet;
    float speed = 2;
    Vector2 vel = new Vector2(0,0);
    Player player;
    boolean flip =false;
    boolean attack = false;

    public Ghost(Vector2 pos, Player player) {
        super(pos);
        health = 40;

        runSheet = new SpriteSheet(new Texture("gameOne/ghost.png"),2,3);
        runSheet.setPlay(0, 2, 0.15f, true);

        attackSheet = new SpriteSheet(new Texture("gameOne/ghost.png"),2,3);
        attackSheet.setPlay(3, 5, 0.1f, false);


        sheet = runSheet;
        this.player = player;
    }

    void flipAllSheet(boolean flipX){
        runSheet.flip(flipX);
        attackSheet.flip(flipX);
    }

    public Rect getRect(){
        TextureRegion t = sheet.getCurrentFrame();
//        if((sheet == attackSheet) && flip){
//            return new Rect(pos.x - 50,pos.y,t.getRegionWidth(),t.getRegionHeight());
//        }
        return new Rect(pos.x,pos.y,t.getRegionWidth(),t.getRegionHeight());
    }
    @Override
    public void update() {
        super.update();
        sheet.play();

        if(sheet == attackSheet){

            if(sheet.current >= 4){
                sheet = runSheet;
            }
            else if(!attack && sheet.current >= 2 && Vector2.dst(pos.x,pos.y,player.getPos().x,player.getPos().y) < 100){
                player.hit(1);
                attack = true;
                player.knockBack(pos);

            }

            return;
        }


        if(Vector2.dst(pos.x,pos.y,player.getPos().x,player.getPos().y) < 100){
            sheet = attackSheet;
            attackSheet.setPlay(0, 4, 0.1f, true);
            attack = false;
        }
        else {
            Vector2 dir = new Vector2(new Vector2( player.getPos().x - pos.x, player.getPos().y - pos.y));
            dir.nor();
            vel = new Vector2(dir.x * speed,dir.y * speed);

            if(!flip && vel.x < 0){
                flipAllSheet(true);
                flip = true;
            }
            if(flip && vel.x > 0){
                flipAllSheet(true);
                flip = false;
            }
            pos.add(vel);
        }

    }

    @Override
    public void draw(Batch batch) {
        TextureRegion t = sheet.getCurrentFrame();
//        if((sheet == attackSheet) && flip){
//            super.drawImg(new Vector2(pos.x - 50,pos.y),batch,t);
//            return;
//        }
        super.drawImg(new Vector2(pos.x,pos.y),batch,t);

    }
}
