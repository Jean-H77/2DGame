package com.csun.game.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.csun.game.attributes.Attributes;
import com.csun.game.dialogue.impl.DialogueOptions;
import com.csun.game.dialogue.impl.DialogueStatement;
import com.csun.game.misc.Assets;
import com.csun.game.player.Player;
import com.github.tommyettinger.textra.Font;
import com.github.tommyettinger.textra.Layout;
import com.github.tommyettinger.textra.TypingLabel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Dialogue {

    private static final Sprite DEFAULT_TEXT_BG, DEFAULT_NPC_BG;
    private static final int ROOT_WIDTH = Gdx.graphics.getWidth();
    private static final int ROOT_HEIGHT = 275;
    private static final Vector2 NPC_BG_SIZE = new Vector2(350, 350);
    private static BitmapFont bitmapFont;
    private static Font font;

    static {
        // Load fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("dialogue/di.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        bitmapFont = generator.generateFont(parameter);
        font = new Font(bitmapFont);
        font.scale(3, 3);
        generator.dispose();

        // Load textures using AssetManager
        AssetManager assetManager = Assets.INSTANCE.getManager();
        assetManager.load("dialogue/dialogue_text_bg.png", Texture.class);
        assetManager.load("dialogue/dialogue_npc_bg.png", Texture.class);
        assetManager.load("dialogue/dialogue_quest_bg.png", Texture.class);
        assetManager.finishLoading(); // Block until all assets are loaded

        // Create sprites using loaded textures
        Texture textBgTexture = assetManager.get("dialogue/dialogue_text_bg.png", Texture.class);
        DEFAULT_TEXT_BG = new Sprite(textBgTexture);

        Texture npcBgTexture = assetManager.get("dialogue/dialogue_text_bg.png", Texture.class);
        DEFAULT_NPC_BG = new Sprite(npcBgTexture);
    }

    /**
     * Is shown behind the text
     */
    private final Sprite textBackground;
    /**
     * Is shown behind the npc sprite
     */
    private final Sprite npcBackground;
    /**
     * Sprite of the npc
     */
    private final Sprite npcSprite;
    private final SpriteBatch spriteBatch;
    private final AssetManager assetManager = Assets.INSTANCE.getManager();
    private final Deque<DialoguePart> dialogueParts = new ArrayDeque<>();
    private final Stage stage = new Stage(new ScreenViewport());
    private Configuration config;
    private DialoguePart currentPart;

    private Dialogue(Builder builder) {
        this.dialogueParts.addAll(builder.dialogueParts);
        this.textBackground = builder.textBackground;
        this.npcBackground = builder.npcBackground;
        this.npcSprite = builder.npcSprite;
        this.spriteBatch = new SpriteBatch();
        this.config = builder.config;
    }

    /**
     * Sets up the dialogue with the provided player.
     *
     * <p>This method initializes and configures the dialogue user interface, including the layout of text, options,
     * and NPC sprite. It handles different types of dialogue parts such as options and statements, updating the
     * dialogue accordingly based on player interaction.</p>
     *
     * @param player The player interacting with the dialogue.
     */
    public void setup(Player player) {
        // Ensure there is a current dialogue part to display
        if (currentPart == null) {
            currentPart = next();
        }

        // Clear the stage and set input processor
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        // Create a parent table to contain all dialogue elements
        final Table parentToAll = new Table();
        parentToAll.setBounds(Gdx.graphics.getSafeInsetLeft(), Gdx.graphics.getSafeInsetBottom(),
            Gdx.graphics.getWidth(), config.textBackgroundHeight + config.npcBackgroundSize.y);
        stage.addActor(parentToAll);

        // Create root table for text background
        final Table root = new Table();
        root.setBounds(Gdx.graphics.getSafeInsetLeft(), Gdx.graphics.getSafeInsetBottom(),
            Gdx.graphics.getWidth(), config.textBackgroundHeight);
        root.setBackground(new Image(textBackground).getDrawable());

        // Create table for NPC sprite and background
        final Table npcRootTable = new Table();
        npcRootTable.setBackground(new Image(npcBackground).getDrawable());
        Image npcSpriteImg = new Image(npcSprite);
        npcRootTable.add(npcSpriteImg).center();
        npcRootTable.setBounds(Gdx.graphics.getSafeInsetLeft(), Gdx.graphics.getSafeInsetBottom() + config.textBackgroundHeight,
            config.npcBackgroundSize.x, config.npcBackgroundSize.y);
        parentToAll.addActor(npcRootTable);

        // Position the root table
        root.setPosition(0, 0);

        // Add root table to the stage
        stage.addActor(root);

        // Create container for text and wrap it in a ScrollPane
        final Table table = new Table();
        table.setBounds(root.getX(), root.getY(), root.getWidth(), root.getHeight());

        // Create ScrollPane to enable scrolling if text overflows
        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setBounds(root.getX(), root.getY(), root.getWidth(), root.getHeight());
        scrollPane.setScrollingDisabled(true, false); // Disable horizontal scrolling
        scrollPane.setFadeScrollBars(false); // Keep scroll bars always visible

        // Add the ScrollPane to the root table
        root.add(scrollPane).expand().fill();

        // Handle current dialogue part
        if (currentPart instanceof DialogueOptions dialogueOptions) {
            // If the current part is options, display them
            Option[] options = dialogueOptions.getOptions();

            TypingLabel header = new TypingLabel("[_][FIREBRICK] Select an option [%]", font);
            table.add(header).padBottom(10).padTop(25);

            // Display each option as a clickable label
            for (Option option : options) {
                TypingLabel label = new TypingLabel("[BLACK]" + option.title() + "[%]", font);

                // Add click listener to execute the selected option
                label.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (label.hasEnded()) {
                            Command command = option.command();
                            if (command != null) {
                                command.execute(player);
                            }
                            // Update current part based on the selected option
                            currentPart = option.next() != null ? option.next() : dialogueOptions.getNext();
                            if (currentPart == null) {
                                removeDialogue(player);
                                return;
                            }
                            // Update dialogue parts and refresh setup
                            dialogueParts.addFirst(currentPart);
                            currentPart = next();
                            setup(player);
                        }
                    }

                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        if (label.hasEnded()) {
                            label.setText(label.storedText.replace("[BLACK]", "[WHITE]"), true);
                        }
                        super.enter(event, x, y, pointer, fromActor);
                    }

                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        if (label.hasEnded()) {
                            label.setText(label.storedText.replace("[WHITE]", "[BLACK]"), true);
                        }
                        super.exit(event, x, y, pointer, toActor);
                    }
                });
                table.row();
                table.add(label);
            }
        } else if (currentPart instanceof DialogueStatement statement) {
            // If the current part is a statement, display the text
            String talker = statement.getTalkerName();
            Gdx.app.log("Talker", "I am talking: " + talker);

            TypingLabel typingLabel = new TypingLabel("[BLACK]" + statement.getText(player) + "[%]", font);

            TypingLabel continueLabel = new TypingLabel("[BLUE]Click here to continue[%]", font);

            // Add click listener to proceed to the next part
            continueLabel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (continueLabel.hasEnded()) {
                        if (statement.getNext() != null) {
                            dialogueParts.addFirst(statement.getNext());
                        }
                        // Move to the next part of the dialogue
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
                    if (continueLabel.hasEnded()) {
                        continueLabel.setText(continueLabel.storedText.replace("[BLUE]", "[WHITE]"), true);
                    }
                    super.enter(event, x, y, pointer, fromActor);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (continueLabel.hasEnded()) {
                        continueLabel.setText(continueLabel.storedText.replace("[WHITE]", "[BLUE]"), true);
                    }
                    super.exit(event, x, y, pointer, toActor);
                }
            });

            // Set up text label for statement
            typingLabel.setWrap(true); // Enable text wrapping if it exceeds the width
            typingLabel.setAlignment(Align.center); // Center align the text
            table.center(); // Center the table within its parent
            table.add(typingLabel).width(root.getWidth()).center().expandX().fillX().row();
            table.row().padTop(10); // Add padding between text and continue label
            table.add(continueLabel); // Add continue label
        }
    }

    private float calculateFontScale(TypingLabel label, float maxWidth, float maxHeight) {
        Layout layout = label.layout;

        // Calculate font scale based on available width and height
        float scaleX = maxWidth / layout.getWidth();
        float scaleY = maxHeight / layout.getHeight();

        // Choose the smaller scale to ensure that text fits within the bounds
        return Math.min(scaleX, scaleY);
    }

    /**
     * Renders the dialogue on the screen.
     * @param deltaTime The time elapsed since the last frame.
     */
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
        //uh it's here tho, that little pepper thing means it's lombok making the getter
        player.getAttributes().remove(Attributes.CURRENT_DIALOGUE);
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }

    public DialoguePart next() {
        return dialogueParts.isEmpty() ? null : dialogueParts.pop();
    }

    /**
     * Builder class for constructing Dialogue instances.
     *
     * <p>The Builder class provides a convenient way to construct instances of the Dialogue class with various settings.
     * It allows customization of NPC background, text background, NPC sprite, and configuration settings.</p>
     */
    public static class Builder {
        List<DialoguePart> dialogueParts = new ArrayList<>();
        private Sprite npcBackground, textBackground, npcSprite;
        private Configuration config;

        /**
         * Constructs a new Builder with default settings.
         *
         * <p>The default settings include using default NPC background and text background sprites, and a default
         * configuration. Additionally, a default NPC sprite is loaded from the assets.</p>
         */
        public Builder() {
            this.npcBackground = Dialogue.DEFAULT_NPC_BG;
            this.textBackground = Dialogue.DEFAULT_TEXT_BG;
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.Alpha);
            pixmap.setColor(Color.CLEAR);
            this.npcSprite = new Sprite(new Texture(pixmap));
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                npcSprite.getTexture().dispose();
            }));
            this.config = new Configuration();
        }

        /**
         * Sets the configuration for the dialogue.
         *
         * @param config The configuration for the dialogue.
         * @return This Builder object for method chaining.
         */
        public Builder setConfig(Configuration config) {
            this.config = config;
            return this;
        }

        /**
         * Adds options to the dialogue.
         *
         * @param dialogueOption The options to be added to the dialogue.
         * @return This Builder object for method chaining.
         */
        public Builder addOptions(DialogueOptions dialogueOption) {
            dialogueParts.add(dialogueOption);
            return this;
        }

        /**
         * Sets the NPC background sprite.
         *
         * @param npcBackground The NPC background sprite.
         * @return This Builder object for method chaining.
         */
        public Builder setNpcBackground(Sprite npcBackground) {
            this.npcBackground = npcBackground;
            return this;
        }

        /**
         * Sets the text background sprite.
         *
         * @param textBackground The text background sprite.
         * @return This Builder object for method chaining.
         */
        public Builder setTextBackground(Sprite textBackground) {
            this.textBackground = textBackground;
            return this;
        }

        /**
         * Sets the NPC sprite.
         *
         * @param npcSprite The NPC sprite.
         * @return This Builder object for method chaining.
         */
        public Builder setNpcSprite(Sprite npcSprite) {
            this.npcSprite = npcSprite;
            return this;
        }

        /**
         * Adds a statement to the dialogue.
         *
         * @param dialogueStatement The statement to be added to the dialogue.
         * @return This Builder object for method chaining.
         */
        public Builder addStatement(DialogueStatement dialogueStatement) {
            dialogueParts.add(dialogueStatement);
            return this;
        }

        /**
         * Adds a part to the dialogue.
         *
         * @param dialoguePart The part to be added to the dialogue.
         * @return This Builder object for method chaining.
         */
        public Builder addPart(DialoguePart dialoguePart) {
            dialogueParts.add(dialoguePart);
            return this;
        }

        /**
         * Builds and returns a new Dialogue instance based on the current Builder state.
         *
         * @return A new Dialogue instance.
         */
        public Dialogue build() {
            return new Dialogue(this);
        }
    }

    /**
     * Configuration for how the dialogue will appear.
     *
     * <p>This record defines the settings for the appearance of the dialogue, including the size of the NPC background,
     * the size of the NPC sprite, the height of the text background, and the offset of the text from the NPC background.</p>
     *
     * @param npcBackgroundSize   The size of the texture that will be shown on the left-hand side of the screen, beneath
     *                            the texture of the talking NPC.
     * @param npcSpriteSize       The size of the NPC texture.
     * @param textBackgroundHeight The height of the part of the dialogue where the text appears.
     * @param textOffset          The offset of the text from the NPC background. If textOffset is 0, it will be right
     *                            next to the NPC background on the left-hand side.
     */
    public record Configuration(Vector2 npcBackgroundSize, Vector2 npcSpriteSize,
                                float textBackgroundHeight, float textOffset) {

        /**
         * Constructs a default Configuration.
         *
         * <p>The default configuration uses pre-defined constants for NPC background size, NPC sprite size, text background height,
         * and text offset.</p>
         */
        public Configuration() {
            this(NPC_BG_SIZE, new Vector2(NPC_BG_SIZE.x - 20, NPC_BG_SIZE.y - 20), ROOT_HEIGHT, 10);
        }

        /**
         * Calculates the offset of the NPC sprite from the NPC background.
         *
         * @return The offset of the NPC sprite from the NPC background.
         */
        public Vector2 npcSpriteToBackgroundOffset() {
            Vector2 result = npcBackgroundSize.sub(npcSpriteSize);
            result.x /= 2;
            result.y /= 2;
            return result;
        }
    }
}
