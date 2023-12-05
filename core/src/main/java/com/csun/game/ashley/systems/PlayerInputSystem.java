package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.csun.game.player.Player;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;
import com.csun.game.models.Direction;
import com.csun.game.models.MovementState;
import com.google.inject.Inject;

public class PlayerInputSystem extends IteratingSystem {

    private final ComponentMapper<MovementComponent> mp = ComponentMapper.getFor(MovementComponent.class);

    private final Player player;

    @Inject
    public PlayerInputSystem(Player player) {
        super(Family.all(PlayerComponent.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent mc = mp.get(entity);
        mc.velocity = 0.0f;
        mc.state = MovementState.IDLE;

        if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) return;

        if(handleMovementInput(mc)) return;

        if(handleHotKeyInput()) return;
    }

    private boolean handleHotKeyInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.Y)) {
            player.save();
        }
        return false;
    }

    private boolean handleMovementInput(MovementComponent mc) {
        if(mc.disabled) return false;

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) && !(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))) {
            if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.move(Direction.NE);
            } else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.move(Direction.NW);
            } else {
                player.move(Direction.N);
            }
            return true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN) && !(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))) {
            if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.move(Direction.SE);
            } else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.move(Direction.SW);
            } else {
                player.move(Direction.S);
            }
            return true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT) && !(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
            player.move(Direction.W);
            return true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
            player.move(Direction.E);
            return true;
        }
        return false;
    }
}
