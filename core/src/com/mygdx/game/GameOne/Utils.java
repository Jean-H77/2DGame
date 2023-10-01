package com.mygdx.game.GameOne;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    private static Utils instance = null;


    public SpriteBatch batch;
    BitmapFont font = new BitmapFont();

    public boolean gameOver = false;
    public int currentLevel = 0;

    public Vector2 cameraPos;
    public MSound mSound;


    private Utils()
    {
        batch = new SpriteBatch();
        //640:480
        mSound = new MSound();
        mSound.play("music",0.1f);
    }

    public static Utils get() // singleton class
    {
        if (instance == null)
            instance = new Utils();
        return instance;
    }

    public void drawText(float x,float y, String text, Color color ,int scale) {
        font.setColor(color);
        font.getData().setScale(scale);
        font.draw(batch,  text, x,y);
    }

    public boolean collide(GameObject a,GameObject b) {
        Rect rect = a.getRect();
        Rect r = b.getRect();
        if (r.x < rect.x + rect.w &&
                r.x + r.w > rect.x &&
                r.y < rect.y + rect.h &&
                r.h + r.y > rect.y) {
            return true;
        }
        return false;
    }
    public void saveScore(int score){
        try {
            Writer wr = new FileWriter("score.txt", true);
            wr.write(String.valueOf(score) + "\n");
            wr.close();
        }catch (Exception e){

        }
    }

    public List<Integer> readScores(){
        List<Integer> scores = new ArrayList<>();
        File file = new File("score.txt");

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                int i = sc.nextInt();
                scores.add(i);
                System.out.println(i);
            }
            sc.close();
        }
        catch (Exception e) {

        }
        return scores;
    }


}
