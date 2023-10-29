package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;

public class PlayerInputSystem extends IteratingSystem {

    private final ComponentMapper<MovementComponent> mp = ComponentMapper.getFor(MovementComponent.class);

    public PlayerInputSystem() {
        super(Family.all(PlayerComponent.class, MovementComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent mc = mp.get(entity);

        mc.velocity = 0.0f;
        mc.state = MovementComponent.MovementState.IDLE;

        if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            return;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) && !(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))) {
            if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                addMovement(mc, MovementComponent.Direction.NE);
            } else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                addMovement(mc, MovementComponent.Direction.NW);
            } else {
                addMovement(mc, MovementComponent.Direction.N);
            }
            return;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN) && !(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))) {
            if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                addMovement(mc, MovementComponent.Direction.SE);
            } else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                addMovement(mc, MovementComponent.Direction.SW);
            } else {
                addMovement(mc, MovementComponent.Direction.S);
            }
            return;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT) && !(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
            addMovement(mc,  MovementComponent.Direction.W);
            return;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
            addMovement(mc,  MovementComponent.Direction.E);
        }
    }

    private void addMovement(MovementComponent m,  MovementComponent.Direction dir) {
        m.velocity = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? 2 : 1;
        m.state = MovementComponent.MovementState.MOVING;
        m.dir = dir;
    }
}
