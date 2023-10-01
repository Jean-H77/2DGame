package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameOne.*;
import com.mygdx.game.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameOne implements Screen {
    private Stage stage;
    private Game game;

    SpriteBatch batch;
    Texture img;
    private OrthographicCamera cam;

    List<GameObject> gameObjects;

    Label timerText;
    Label scoreText;
    int score;
    float timer;


    GameObject bg;
    Player player;
    Spawner spawner;

    public GameOne(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        timerText = new Label("1", Main.gameSkin,"big-white");
        timerText.setAlignment(Align.center);
        timerText.setPosition(315,0);
        timerText.setFontScale(0.5f);
        stage.addActor(timerText);

        scoreText = new Label("1", Main.gameSkin,"big-white");
        scoreText.setAlignment(Align.center);
        scoreText.setPosition(80,0);
        scoreText.setFontScale(0.5f);
        stage.addActor(scoreText);

        batch = new SpriteBatch();

        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        gameObjects = new ArrayList<>();
        bg = new GameObject(new Vector2(0,0),new Texture("gameOne/bg.png"));

        player = new Player(new Vector2(100,100));

        spawner = new Spawner(player);


        gameObjects.add(bg);
        gameObjects.add(player);

        startGame();
    }

    void startGame(){


        Gdx.input.setInputProcessor(new InputAdapter () {
            @Override
            public boolean keyDown(int keycode) {
                player.onKeyDown(keycode);
                return super.keyDown(keycode);
            }
            @Override
            public boolean keyUp(int keycode) {
                player.onKeyUp(keycode);
                return super.keyUp(keycode);
            }
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                player.attack();
                return super.touchDown(screenX, screenY, pointer, button);
            }
        });
    }


    @Override
    public void show() {
        Gdx.app.log("MainScreen","show");
//        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();

        if(player.destroyFlag){
            game.setScreen(new Highscore(game,score));
        }


        Utils.get().cameraPos = new Vector2( player.getPos().x - Gdx.graphics.getWidth()/2 + 100, player.getPos().y - Gdx.graphics.getHeight()/2 + 100 );
        if(Utils.get().cameraPos.x < 0){
            Utils.get().cameraPos.x = 0;
        }
        if(Utils.get().cameraPos.y < 0){
            Utils.get().cameraPos.y = 0;
        }
        if(Utils.get().cameraPos.x > bg.getRect().w/2){
            Utils.get().cameraPos.x = bg.getRect().w/2;
        }
        if(Utils.get().cameraPos.y > bg.getRect().h/2){
            Utils.get().cameraPos.y = bg.getRect().h/2;
        }

        spawner.spawnEnemy(gameObjects);

        SpriteSheet playerSheet = player.getAttackSheet();
        List<GameObject> addedObjects = new ArrayList<>();
        for(GameObject obj : gameObjects){
            obj.update();
            if( obj instanceof Mummy ){
                ((Mummy)obj).attack(addedObjects);
            }
            if( obj instanceof Enemy ){
                if(playerSheet != null && (playerSheet.getCurrent() == 1 || playerSheet.getCurrent() == 6 || playerSheet.getCurrent() == 11) && Utils.get().collide(obj,player)){
                    addedObjects.add(new Blood(obj.getPos()));
                    ((Attackable)obj).hit(1);
                    ((Attackable)obj).knockBack(player.getPos());
                }
            }

        }
        gameObjects.addAll(addedObjects);

        for(int i = 0 ; i < gameObjects.size();i++){
            if(gameObjects.get(i).destroyFlag){
                if(gameObjects.get(i) instanceof Enemy){
                    score += 1;
                    Utils.get().mSound.play("enemy_damage",1);

                }
                gameObjects.remove(i);
            }
        }



        for(GameObject obj : gameObjects){
            obj.draw(batch);
        }

//        timer -= delta;
//        if(timer <= 0){
//            mSound.stop("music");
//            mSound.play("gameover",1);
//            game.setScreen(new Highscore(game,score));
//        }

        timerText.setText(String.format("%.1f",timer) + "s");
        scoreText.setText(String.format("Score: %d",score) );

        batch.end();

//        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}
