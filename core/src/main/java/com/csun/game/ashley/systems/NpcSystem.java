package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.AnimationComponent;
import com.csun.game.ashley.components.TransformComponent;
import com.csun.game.ashley.components.NpcComponent;
import com.google.inject.Inject;

public class NpcSystem extends IteratingSystem {

    private final BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();
    private final SpriteBatch spriteBatch;

    @Inject
    public NpcSystem(SpriteBatch spriteBatch) {
        super(Family.all(NpcComponent.class, TransformComponent.class).get());
        this.spriteBatch = spriteBatch;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("npc_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        font = generator.generateFont(parameter);
        font.setColor(Color.valueOf("ffff00"));
        generator.dispose();
    }

    @Override
    public void update(float deltaTime) {
        spriteBatch.begin();
        super.update(deltaTime);
        spriteBatch.end();
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        NpcComponent npcComponent = Mappers.npcs.get(entity);
        TransformComponent transformComponent = Mappers.movements.get(entity);
        AnimationComponent animationComponent = Mappers.animations.get(entity);

        if(npcComponent == null || transformComponent == null || animationComponent == null) {
            return;
        }
        if(npcComponent.say != null && !npcComponent.say.isEmpty()) {
            layout.setText(font, npcComponent.say);
            float textWidth = layout.width;
            float textX = transformComponent.pos.x + (animationComponent.width / 2) - (textWidth / 2);
            float textY = transformComponent.pos.y + animationComponent.height + 10;
            font.draw(spriteBatch, npcComponent.say, textX, textY);
            npcComponent.sayTimer -= v;
            if(npcComponent.sayTimer < 0) {
                npcComponent.say = null;
            }
        }
    }
}
