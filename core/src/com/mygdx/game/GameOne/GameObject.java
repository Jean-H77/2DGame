package com.mygdx.game.GameOne;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
    protected Texture img;
    Vector2 pos;
    public boolean destroyFlag = false;


    public GameObject(Vector2 pos,Texture img) {
        this.img = img;
        this.pos = pos;
    }

    public Rect getRect(){
        return new Rect(pos.x,pos.y,img.getWidth(),img.getHeight());
    }

    public void update(){

    }

    public void draw(Batch batch){
        batch.draw(img, pos.x - Utils.get().cameraPos.x, pos.y - Utils.get().cameraPos.y,img.getWidth(),img.getHeight(),0,0,img	.getWidth(),img.getHeight(),false,true);
    }

    public void drawImg(Vector2 pos,Batch batch, TextureRegion img){
        batch.draw(img, pos.x - Utils.get().cameraPos.x, pos.y - Utils.get().cameraPos.y,img.getRegionWidth(),img.getRegionHeight());
    }

    public boolean collide(GameObject b) {
        Rect rect = getRect();
        Rect r = b.getRect();
        if (r.x < rect.x + rect.w &&
                r.x + r.w > rect.x &&
                r.y < rect.y + rect.h &&
                r.h + r.y > rect.y) {
            return true;
        }
        return false;
    }

    public boolean mouseCollide(int x,int y) {
        Rect rect = getRect();
        if(rect.x <= x && x <=  rect.x  + rect.w && rect.y <= y && y <= rect.y + rect.h){
            return true;
        }
        return false;
    }

    public Texture getImg() {
        return img;
    }

    public void setImg(Texture img) {
        this.img = img;
    }

    public Vector2 getPos(){
        return pos;
    }

}
