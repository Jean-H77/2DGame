package com.csun.game.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.csun.game.action.ActionListener;
import com.csun.game.dialogue.impl.DialogueOptions;
import com.csun.game.dialogue.impl.DialogueStatement;
import com.csun.game.attributes.Attributes;
import com.csun.game.player.Player;
import com.csun.game.quest.impl.TestQuest;
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
    private final Stage stage = new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));

    private DialoguePart currentPart;

    private Dialogue(Builder builder) {
        this.dialogueParts.addAll(builder.dialogueParts);
    }

    public void setup(Player player) {
        if(currentPart == null) {
            currentPart = next();
        }

        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        Image image = new Image(new Texture(Gdx.files.internal("dialogue_board.png")));
        root.setBackground(image.getDrawable());
        root.setSize(stage.getWidth(), stage.getHeight());
        root.align(Align.center);
        stage.addActor(root);

        Table table = new Table(skin);
        root.add(table);

        if(currentPart instanceof DialogueOptions dialogueOptions) {
            Option[] options = dialogueOptions.getOptions();

            TypingLabel header = new TypingLabel("[_][%75][SCARLET] Select an option [%]", KnownFonts.getStandardFamily());
            header.setSize(500, 20);
            table.row();
            table.add(header);

            int i = 0;
            for (Option option : options) {
                TypingLabel label = new TypingLabel("[%75][SCARLET]" + option.title() + "[%]", KnownFonts.getStandardFamily());
                label.setSize(500, 20);

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
                                Gdx.app.log("Dialogue", "Removing");
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
                            label.setText(label.storedText.replace("[SCARLET]", "[WHITE]"), true);
                        }
                        super.enter(event, x, y, pointer, fromActor);
                    }

                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        if(label.hasEnded()) {
                            label.setText(label.storedText.replace("[WHITE]", "[SCARLET]"), true);
                        }
                        super.exit(event, x, y, pointer, toActor);
                    }
                });

                table.row();
                table.add(label);
            }

        } else if(currentPart instanceof DialogueStatement statement) {
            TypingLabel typingLabel = new TypingLabel("[%75][SCARLET]" + statement.getText(player) + "[%]", KnownFonts.getStandardFamily());
            typingLabel.setAlignment(Align.center);
            typingLabel.setWrap(true);
            typingLabel.setSize(500, 20);

            TypingLabel continueLabel = new TypingLabel("[_][%75][SCARLET]Click here to continue[%]", KnownFonts.getStandardFamily());
            continueLabel.setAlignment(Align.center);
            continueLabel.setSize(500, 20);
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
                        continueLabel.setText(continueLabel.storedText.replace("[SCARLET]", "[WHITE]"), true);
                    }
                    super.enter(event, x, y, pointer, fromActor);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if(continueLabel.hasEnded()) {
                        continueLabel.setText(continueLabel.storedText.replace("[WHITE]", "[SCARLET]"), true);
                    }
                    super.exit(event, x, y, pointer, toActor);
                }
            });

            table.add(typingLabel);
            table.row();
            table.add(continueLabel);
        }
    }

    public void render(float deltaTime) {
        stage.getViewport().apply();
        stage.act(deltaTime);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
