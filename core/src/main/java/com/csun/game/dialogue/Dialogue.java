package com.csun.game.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.csun.game.action.ActionListener;
import com.csun.game.dialogue.impl.DialogueOptions;
import com.csun.game.dialogue.impl.DialogueStatement;
import com.csun.game.attributes.Attributes;
import com.csun.game.misc.Assets;
import com.csun.game.player.Player;
import com.csun.game.quest.impl.TestQuest;
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

    private static final AssetManager assetManager = Assets.INSTANCE.getManager();
    private static BitmapFont bitmapFont;
    private static Font font;
    private static Texture texture;
    private static final int ROOT_WIDTH = 420;
    private static final int ROOT_HEIGHT = 150;

    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("dialogue/di.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        bitmapFont = generator.generateFont(parameter);
        font = new Font(bitmapFont);
        generator.dispose();

        texture = assetManager.get("dialogue/dialogue_board.png");
    }

    private final Deque<DialoguePart> dialogueParts = new ArrayDeque<>();
    private final Stage stage = new Stage(new ScreenViewport());

    private DialoguePart currentPart;

    private Dialogue(Builder builder) {
        this.dialogueParts.addAll(builder.dialogueParts);
    }

    public void setup(Player player) {
        if(currentPart == null) {
            currentPart = next();
        }

        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        Image image = new Image(texture);
        root.setBackground(image.getDrawable());
        root.setSize(ROOT_WIDTH, ROOT_HEIGHT);
        float xPosition = (stage.getWidth() / 2) - (ROOT_WIDTH / 2);
        float yPosition = 10;
        root.setPosition(xPosition, yPosition);

        stage.addActor(root);

        Table table = new Table();
        root.add(table);

        if(currentPart instanceof DialogueOptions dialogueOptions) {
            Option[] options = dialogueOptions.getOptions();

            TypingLabel header = new TypingLabel("[_][FIREBRICK] Select an option [%]", font);
            header.setSize(400, 16);
            table.row().padBottom(10);
            table.add(header);

            for (Option option : options) {
                TypingLabel label = new TypingLabel("[BLACK]" + option.title() + "[%]", font);
                label.setSize(400, 16);

                label.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(label.hasEnded()) {
                            Command command = option.command();
                            if(command != null) {
                                command.execute(player);
                            }
                            currentPart = option.next() != null ? option.next() : dialogueOptions.getNext();
                            if (currentPart == null) {
                                removeDialogue(player);
                                return;
                            }
                            dialogueParts.addFirst(currentPart);
                            currentPart = next();
                            setup(player);
                        }
                    }

                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        if(label.hasEnded()) {
                            label.setText(label.storedText.replace("[BLACK]", "[WHITE]"), true);
                        }
                        super.enter(event, x, y, pointer, fromActor);
                    }

                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        if(label.hasEnded()) {
                            label.setText(label.storedText.replace("[WHITE]", "[BLACK]"), true);
                        }
                        super.exit(event, x, y, pointer, toActor);
                    }
                });
                table.row();
                table.add(label);
            }

        } else if(currentPart instanceof DialogueStatement statement) {
            TypingLabel typingLabel = new TypingLabel("[BLACK]" + statement.getText(player) + "[%]", font);
            typingLabel.setAlignment(Align.center);
            typingLabel.setWrap(true);
            typingLabel.setSize(400, 16);

            TypingLabel continueLabel = new TypingLabel("[BLUE]Click here to continue[%]", font);
            continueLabel.setAlignment(Align.center);
            continueLabel.setSize(400, 16);
            continueLabel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(continueLabel.hasEnded()) {
                        if(statement.getNext() != null) {
                            dialogueParts.addFirst(statement.getNext());
                        }
                        currentPart = next();
                        if (currentPart == null) {
                            removeDialogue(player);
                            return;
                        }
                        setup(player);
                    }
                }
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if(continueLabel.hasEnded()) {
                        continueLabel.setText(continueLabel.storedText.replace("[BLUE]", "[WHITE]"), true);
                    }
                    super.enter(event, x, y, pointer, fromActor);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if(continueLabel.hasEnded()) {
                        continueLabel.setText(continueLabel.storedText.replace("[WHITE]", "[BLUE]"), true);
                    }
                    super.exit(event, x, y, pointer, toActor);
                }
            });

            table.add(typingLabel);
            table.row().padTop(10);
            table.add(continueLabel);
        }
    }

    public void render(float deltaTime) {
        stage.getViewport().apply();
        stage.act(deltaTime);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        float xPosition = ((float) width / 2) - ((float) ROOT_WIDTH / 2);
        stage.getActors().get(0).setPosition(xPosition, 10);
    }

    public void removeDialogue(Player player) {
        player.getAttributes().remove(Attributes.CURRENT_DIALOGUE);
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }

    public DialoguePart next() {
        return dialogueParts.isEmpty() ? null : dialogueParts.pop();
    }

    public static class Builder {
        List<DialoguePart> dialogueParts = new ArrayList<>();

        public Builder addOptions(DialogueOptions dialogueOption) {
            dialogueParts.add(dialogueOption);
            return this;
        }

        public Builder addStatement(DialogueStatement dialogueStatement) {
            dialogueParts.add(dialogueStatement);
            return this;
        }

        public Builder addPart(DialoguePart dialoguePart) {
            dialogueParts.add(dialoguePart);
            return this;
        }

        public Dialogue build() {
            return new Dialogue(this);
        }
    }
}
