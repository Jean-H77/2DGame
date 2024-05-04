package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.*;
import com.csun.game.map.GameMap;
import com.csun.game.models.Animations;
import com.csun.game.models.Direction;
import com.csun.game.models.MovementState;
import com.csun.game.models.WalkDirection;
import com.csun.game.npc.NpcManager;
import com.google.inject.Inject;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.csun.game.GameConstants.TILE_SIZE;


public class TransformSystem extends IteratingSystem {

    private static final int NPC_WALK = 0;
    private static final int NPC_IDLE = 1;

    private final GameMap gameMap;
    private final NpcManager npcManager;

    @Inject
    public TransformSystem(GameMap gameMap, NpcManager npcManager) {
        super(Family.all(TransformComponent.class, AnimationComponent.class).get());
        this.gameMap = gameMap;
        this.npcManager = npcManager;
    }

    public static Direction getDirection(Vector2 newPos, Vector2 oldPos) {
        double deadzone = 8;
        double dx = newPos.x - oldPos.x;
        double dy = newPos.y - oldPos.y;
        double magnitude = Math.sqrt(dx * dx + dy * dy);

        if (magnitude < deadzone) {
            return null;
        }

        if (dx > 0 && dy > 0) {
            return Direction.NE;
        } else if (dx < 0 && dy > 0) {
            return Direction.NW;
        } else if (dx < 0 && dy < 0) {
            return Direction.SW;
        } else if (dx > 0 && dy < 0) {
            return Direction.SE;
        }

        if (Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? Direction.E : Direction.W;
        } else if (Math.abs(dy) > Math.abs(dx)) {
            return dy > 0 ? Direction.N : Direction.S;
        }

        return null;
    }

    public static Vector2 calculateMovementStep(Direction dir, float velocity) {
        Vector2 vect = new Vector2();

        switch (dir) {
            case N -> vect.y += velocity;
            case S -> vect.y -= velocity;
            case E -> vect.x += velocity;
            case W -> vect.x -= velocity;
            case NE -> {
                vect.x += velocity;
                vect.y += velocity;
            }
            case NW -> {
                vect.x -= velocity;
                vect.y += velocity;
            }
            case SE -> {
                vect.x += velocity;
                vect.y -= velocity;
            }
            case SW -> {
                vect.x -= velocity;
                vect.y -= velocity;
            }
        }
        return vect;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent movement = Mappers.transforms.get(entity);
        AnimationComponent animation = Mappers.animations.get(entity);
        if (movement.state.equals(MovementState.IDLE)) return;
        Direction direction = null;
        Vector2 step = null;

        if (!movement.walkDirections.isEmpty() && movement.currentWalkDirection == null) {
            movement.currentWalkDirection = movement.walkDirections.pop();
            direction = movement.currentWalkDirection .direction();
            step = movement.currentWalkDirection .point();
        }

        if(direction == null || step == null) {
            return;
        }

        NpcComponent npcComponent = Mappers.npcs.get(entity);
        PlayerComponent playerComponent = Mappers.players.get(entity);

        if(npcComponent != null && animation != null && movement.dir != direction) {
            switch (direction) {
                case N, NE, NW -> animation.nextAnim(npcComponent.npcType.getAnimations().get("north")[NPC_WALK]);
                case S, SE, SW -> animation.nextAnim(npcComponent.npcType.getAnimations().get("south")[NPC_WALK]);
                case E -> animation.nextAnim(npcComponent.npcType.getAnimations().get("east")[NPC_WALK]);
                case W -> animation.nextAnim(npcComponent.npcType.getAnimations().get("west")[NPC_WALK]);
            }
        } else {
            if (playerComponent != null && animation != null && movement.dir != direction) {
                switch (direction) {
                    case N, NE, NW -> animation.nextAnim(Animations.TODD_WALK_NORTH);
                    case S, SE, SW -> animation.nextAnim(Animations.TODD_WALK_SOUTH);
                    case E -> animation.nextAnim(Animations.TODD_WALK_EAST);
                    case W -> animation.nextAnim(Animations.TODD_WALK_WEST);
                }
            }
        }

        movement.dir = direction;

        Vector2 dest = new Vector2(step.x, step.y).nor();
        dest.x += movement.pos.x;
        dest.y += movement.pos.y;

        SizeComponent sizeComponent = Mappers.sizes.get(entity);

        if(sizeComponent != null) {
           if (checkCollision((int) sizeComponent.width, (int) sizeComponent.height, dest.x, dest.y, gameMap)) return;
        }

        movement.pos = dest;

        if(movement.currentWalkDirection != null) {
            if(movement.currentWalkDirection.point().equals(movement.pos)) {
                movement.currentWalkDirection = null;
                movement.state = MovementState.IDLE;
            }
        }

        ClickableComponent clickableComponent = Mappers.clickables.get(entity);
        if (clickableComponent != null) {
            clickableComponent.bounds.x = movement.pos.x;
            clickableComponent.bounds.y = movement.pos.y;
        }

        /*if(movement.path.isEmpty() && animation != null) {
            if(npcComponent != null) {
                switch (movement.dir) {
                    case N, NE, NW -> animation.nextAnim(npcComponent.npc.getAnimations().get("north")[NPC_IDLE]);
                    case S, SE, SW -> animation.nextAnim(npcComponent.npc.getAnimations().get("south")[NPC_IDLE]);
                    case E -> animation.nextAnim(npcComponent.npc.getAnimations().get("east")[NPC_IDLE]);
                    case W -> animation.nextAnim(npcComponent.npc.getAnimations().get("west")[NPC_IDLE]);
                }
            }
        }*/

    }

    public static boolean checkCollision(int width, int height, float destX, float destY, GameMap gameMap) {
        AtomicBoolean blocked = new AtomicBoolean(false);
        int[] offsetX = {0, width - 1, 0, height - 1};
        int[] offsetY = {0, 0, width - 1, height - 1};

        for (int i = 0; i < offsetX.length; i++) {
            int x = (int) ((destX + offsetX[i]) / TILE_SIZE);
            int y = (int) ((destY + offsetY[i]) / TILE_SIZE);

            TiledMapTileLayer layer = gameMap.getCollisionLayer();

            TiledMapTileLayer.Cell cell = layer.getCell(x, y);
            if (cell != null && cell.getTile().getProperties().containsKey("blocked")) {
                Gdx.app.log("Collision", "Blocked [" + x + ", " + y + "]");
                blocked.set(true);
            }
        }

        return blocked.get();
    }
}
