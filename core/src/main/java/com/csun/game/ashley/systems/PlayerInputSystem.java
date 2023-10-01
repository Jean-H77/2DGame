package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.csun.game.MovementState;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;

public class PlayerInputSystem extends IteratingSystem {

    private final ComponentMapper<MovementComponent> mp = ComponentMapper.getFor(MovementComponent.class);
    private final ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);

    public PlayerInputSystem() {
        super(Family.all(PlayerComponent.class, MovementComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent mc = mp.get(entity);
        PlayerComponent pc = pm.get(entity);

        pc.movementState = MovementState.IDLE;

        if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            return;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            mc.dir = MovementComponent.Direction.N;
            pc.movementState = MovementState.MOVING;
            Gdx.app.debug("Input","Pressed up");
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            mc.dir = MovementComponent.Direction.S;
            pc.movementState = MovementState.MOVING;
            Gdx.app.debug("Input","Pressed down");
        }

    }
}
