package com.csun.game.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.AnimationComponent;
import com.csun.game.ashley.components.ClickableComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.NpcComponent;
import com.csun.game.map.GameMap;
import com.csun.game.models.Animations;
import com.csun.game.models.Direction;
import com.csun.game.models.MovementState;
import com.google.inject.Inject;

import static com.csun.game.GameConstants.TILE_SIZE;


public class MovementSystem extends IteratingSystem {

    private final GameMap gameMap;
    private boolean isBlocked;

    @Inject
    public MovementSystem(GameMap gameMap) {
        super(Family.all(MovementComponent.class, AnimationComponent.class).get());
        this.gameMap = gameMap;
    }

    public static Direction getDirection(Vector2 newPos, Vector2 oldPos) {
        double dx = newPos.x - oldPos.x;
        double dy = newPos.y - oldPos.y;
        double angle = Math.atan2(dy, dx);
        if (angle >= -Math.PI / 8 && angle < Math.PI / 8) {
            return Direction.E;
        } else if (angle >= Math.PI / 8 && angle < 3 * Math.PI / 8) {
            return Direction.NE;
        } else if (angle >= 3 * Math.PI / 8 && angle < 5 * Math.PI / 8) {
            return Direction.N;
        } else if (angle >= 5 * Math.PI / 8 && angle < 7 * Math.PI / 8) {
            return Direction.NW;
        } else if (angle >= 7 * Math.PI / 8 || angle < -7 * Math.PI / 8) {
            return Direction.W;
        } else if (angle >= -7 * Math.PI / 8 && angle < -5 * Math.PI / 8) {
            return Direction.SW;
        } else if (angle >= -5 * Math.PI / 8 && angle < -3 * Math.PI / 8) {
            return Direction.S;
        } else if (angle >= -3 * Math.PI / 8) {
            return Direction.SE;
        }
        return null;
    }


    private static Vector2 calculateMovementStep(Direction dir, float velocity) {
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
        MovementComponent movement = Mappers.movements.get(entity);
        AnimationComponent animation = Mappers.animations.get(entity);
        if (movement.state.equals(MovementState.IDLE)) return;
        isBlocked = false;
        Direction direction = null;
        if (movement.nextWaypoint != null) {
            Vector2 temp = new Vector2((int) movement.pos.x, (int) movement.pos.y);
            Vector2 waypointWorldPos = new Vector2((int) movement.nextWaypoint.x * TILE_SIZE, (int) movement.nextWaypoint.y * TILE_SIZE);
            if (isCloseEnough(temp, waypointWorldPos, movement.threshold)) {
                direction = getDirection(waypointWorldPos, temp);
                if (animation != null && direction != null) {
                    NpcComponent npcComponent = Mappers.npcs.get(entity);
                    /**
                     * Ok so this is a terrible way to do this, but we don't have a lot of time
                     */
                    switch (direction) {
                        case N, NE, NW -> {
                            if (npcComponent != null) {
                                if (npcComponent.id.equals("Doctor")) {
                                    animation.nextAnim(Animations.DOCTOR_WALK_NORTH);
                                } else if(npcComponent.id.equalsIgnoreCase("Mom")) {
                                    animation.nextAnim(Animations.MOM_WALK_NORTH);
                                }
                            } else {
                                animation.nextAnim(Animations.WITCH_WALK_NORTH);
                            }
                        }
                        case S, SE, SW -> {
                            if (npcComponent != null) {
                                if (npcComponent.id.equals("Doctor")) {
                                    animation.nextAnim(Animations.DOCTOR_WALK_SOUTH);
                                } else if(npcComponent.id.equalsIgnoreCase("Mom")) {
                                    animation.nextAnim(Animations.MOM_WALK_SOUTH);
                                }
                            } else {
                                animation.nextAnim(Animations.WITCH_WALK_SOUTH);
                            }
                        }
                        case E -> {
                            if (npcComponent != null) {
                                if (npcComponent.id.equals("Doctor")) {
                                    animation.nextAnim(Animations.DOCTOR_WALK_EAST);
                                } else if(npcComponent.id.equalsIgnoreCase("Mom")) {
                                    animation.nextAnim(Animations.MOM_WALK_EAST);
                                }
                            } else {
                                animation.nextAnim(Animations.WITCH_WALK_EAST);
                            }
                        }
                        case W -> {
                            if (npcComponent != null) {
                                if (npcComponent.id.equals("Doctor")) {
                                    animation.nextAnim(Animations.DOCTOR_WALK_WEST);
                                } else if(npcComponent.id.equalsIgnoreCase("Mom")) {
                                    animation.nextAnim(Animations.MOM_WALK_WEST);
                                }
                            } else {
                                animation.nextAnim(Animations.WITCH_WALK_WEST);
                            }
                        }
                    }
                }
            } else if (!movement.waypoints.isEmpty()) {
                movement.nextWaypoint = movement.waypoints.pop();
            } else {
                movement.nextWaypoint = null;
                movement.velocity = 0.0f;
                movement.state = MovementState.IDLE;
                if (animation != null && animation.animations != null) {
                    if (Animations.IDLE_TRANSITION_ANIMATIONS.containsKey(animation.animations)) {
                        animation.nextAnim(Animations.IDLE_TRANSITION_ANIMATIONS.get(animation.animations));
                    }
                }
            }
        } else {
            if (!movement.movementQueue.isEmpty()) {
                direction = movement.movementQueue.pop();
            }
        }

        if (direction == null) {
            return;
        }

        movement.dir = direction;
        Vector2 step = calculateMovementStep(direction, movement.velocity);
        Vector2 dest = new Vector2(step.x, step.y).nor();
        dest.x += movement.pos.x;
        dest.y += movement.pos.y;

        gameMap.getLayer(1).ifPresent(tiledMapTileLayer -> {
            TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell((int) (dest.x / TILE_SIZE), (int) (dest.y / TILE_SIZE));
            if (cell != null && cell.getTile().getProperties().containsKey("blocked")) {
                Gdx.app.log("Collision", "Blocked [" + (int) (dest.x / TILE_SIZE) + ", " + (int) (dest.y / TILE_SIZE) + "]");
                isBlocked = true;
            }
        });

        if (isBlocked) return;

        movement.pos = dest;

        ClickableComponent clickableComponent = Mappers.clickables.get(entity);
        if (clickableComponent != null) {
            clickableComponent.bounds.x = movement.pos.x;
            clickableComponent.bounds.y = movement.pos.y;
        }
    }

    private boolean isCloseEnough(Vector2 pos1, Vector2 pos2, float threshold) {
        return pos1.dst(pos2) > threshold;
    }
}
