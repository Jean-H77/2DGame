package com.csun.game.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csun.game.dialogue.impl.DialogueOptions;
import com.csun.game.player.Player;
import com.github.tommyettinger.textra.Font;
import com.github.tommyettinger.textra.KnownFonts;
import com.github.tommyettinger.textra.TypingLabel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.csun.game.GameConstants.VIEWPORT_HEIGHT;
import static com.csun.game.GameConstants.VIEWPORT_WIDTH;

public class Dialogue {

    private static final Skin skin = new Skin();
    static {
        skin.addRegions(new TextureAtlas(Gdx.files.internal("gdx-skins-master/comic/skin/comic-ui.atlas")));
        skin.load(Gdx.files.internal("gdx-skins-master/comic/skin/comic-ui.json"));
    }

    private final Deque<DialoguePart> dialogueParts = new ArrayDeque<>();

    private Stage stage;
    private DialoguePart currentPart;
    private int step;

    private Dialogue(Builder builder) {
        this.dialogueParts.addAll(builder.dialogueParts);
        next();
    }

    public void buildWidget(Player player) {
        Viewport viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        stage = new Stage();
        Font.FontFamily fontFamily = new Font.FontFamily(skin);

        if(currentPart instanceof DialogueOptions dialogueOptions) {
            Option[] options = dialogueOptions.options();
            Table table = new Table(skin);
            table.setPosition((float) Gdx.graphics.getWidth() /2 , 65);
            table.align(Align.center);

            for (Option option : options) {

                TypingLabel label = new TypingLabel(option.title(), KnownFonts.getStandardFamily());
                label.setSize(200, 20);

                label.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        option.command().execute(player);
                    }
                });

                table.row();
                table.add(label);
            }

            stage.addActor(table);
        }

        Gdx.input.setInputProcessor(stage);
    }

    public void render(float deltaTime) {
        stage.getViewport().apply();
        stage.act(deltaTime);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    public void next() {
        currentPart = dialogueParts.pop();
    }

    public static class Builder {
        List<DialoguePart> dialogueParts = new ArrayList<>();

        public Builder addOption(DialogueOptions dialogueOption) {
            dialogueParts.add(dialogueOption);
            return this;
        }

        public Dialogue build() {
            return new Dialogue(this);
        }
    }

}
