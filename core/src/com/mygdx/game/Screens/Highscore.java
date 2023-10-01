package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameOne.Utils;
import com.mygdx.game.Main;

import java.util.Comparator;
import java.util.List;

public class Highscore implements Screen {
    private Stage stage;
    private Game game;

    public Highscore(Game aGame,int score) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        Label title = new Label("High Scores", Main.gameSkin,"big-black");
        title.setAlignment(Align.center);
        title.setY(650);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton backButton = new TextButton("Back",Main.gameSkin);
        backButton.setWidth(Gdx.graphics.getWidth()/2);
        backButton.setPosition(Gdx.graphics.getWidth()/2-backButton.getWidth()/2,Gdx.graphics.getHeight()/5-backButton.getHeight()/2);
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Titlescreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(backButton);


        if(score != -1){
            title.setText("Game Over!");

            Utils.get().saveScore(score);

            Label scoreText = new Label("Score: " + score, Main.gameSkin,"big-black");
            scoreText.setAlignment(Align.center);
            scoreText.setY(350);
            scoreText.setWidth(Gdx.graphics.getWidth());
            stage.addActor(scoreText);

            backButton.setText("Retry!");
        }
        else{
            //display highscores
            List<Integer> scores = Utils.get().readScores();
            scores.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1 ;
                }
            });
            int y = 550;
            System.out.println("s:" + scores.size());
            for(int i = 0 ; i < 5 ;i++){
                if(i >= scores.size()){
                    break;
                }
                Label scoreLabel = new Label(String.valueOf(i) + ".            " + String.valueOf(scores.get(i)), Main.gameSkin,"big-black");
                scoreLabel.setAlignment(Align.center);
                scoreLabel.setY(y);
                scoreLabel.setWidth(Gdx.graphics.getWidth());
                stage.addActor(scoreLabel);
                y -= 80;
            }
        }
    }

    @Override
    public void show() {
        Gdx.app.log("MainScreen","show");
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
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
    }
}
