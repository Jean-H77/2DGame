package com.mygdx.game.GameOne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteSheet {

	Texture texture;
	
	int rows,cols;
	
	// array of texture
	TextureRegion[] frames;
	
	float countTime = 0;
	int from;
	int to;
	int current;
	float time;
	boolean loop;
	
	int width,height;
	public  boolean isFlip = false;
	
	public SpriteSheet(Texture texture,int rows,int cols){
		
		this.texture = texture;
		this.rows = rows;
		this.cols = cols;
		
		frames = new TextureRegion[rows * cols];
		// split the texture to multiple texture
		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / cols, texture.getHeight() / rows);  // split the sprite sheet 
		
		width = texture.getWidth() / cols;
		height =  texture.getHeight() / rows;
		
		int ide = 0;
		for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	frames[ide] = tmp[i][j];
				frames[ide].flip(false,true);
                ide++;
            }
        }
	}
	public TextureRegion getTexture(int frame) {
		return frames[frame];
	}
	public TextureRegion getCurrentFrame() {
		return frames[current];
	}

	public int getCurrent(){
		return current;
	}

	void flip(boolean flipX) { // flip
		int ide = 0;
		for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	frames[ide].flip(flipX, false);
                ide++;
            }
        }
	}
	void setPlay(int from,int to,float time ,boolean loop) {
		this.from = from;
		this.to = to;
		this.time = time;
		this.loop = loop;
		current = from;
	}
	// play fromFrame to toFrame each delay by 'time'
	void play() {
		countTime += Gdx.graphics.getDeltaTime();
		if(countTime >= time) {
			countTime = 0;
			current++;
			if(current > to && loop) {
				current = from;
			}
			else if(current > to && !loop){
				current = to;
			}
		}
		
	}
	
	float getWidth() {
		return width;
	}
	float getHeight() {
		return height;
	}
	
	void dispose()
	{
		texture.dispose();
	}
	
}
