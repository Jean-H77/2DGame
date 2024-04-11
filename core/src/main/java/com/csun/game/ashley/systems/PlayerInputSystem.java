package com.csun.game.ashley.systems;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.csun.game.UI.UIManager;
import com.csun.game.ai.paths.PathFinder;
import com.csun.game.ai.paths.PathResult;
import com.csun.game.ashley.Mappers;
import com.csun.game.ashley.components.*;
import com.csun.game.attributes.Attributes;
import com.csun.game.dialogue.TestDialogue;
import com.csun.game.models.Animations;
import com.csun.game.models.Direction;
import com.csun.game.models.LightType;
import com.csun.game.models.WalkDirection;
import com.csun.game.player.Player;
import com.github.tommyettinger.gand.Path;
import com.github.tommyettinger.gand.points.PointI2;
import com.google.inject.Inject;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

import static com.csun.game.GameConstants.TILE_SIZE;

public class PlayerInputSystem extends IteratingSystem {

    private final Player player;
    private final UIManager uiManager;

    @Inject
    public PlayerInputSystem(Player player, UIManager uiManager) {
        super(Family.all(PlayerComponent.class).get());
        this.player = player;
        this.uiManager = uiManager;
    }

    @Override
    public ImmutableArray<Entity> getEntities() {
        return getEngine().getEntitiesFor(Family.all(ClickableComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //if(!player.getAttributes().get(Attributes.PLAYER_IS_PAUSED))
        TransformComponent mc = Mappers.transforms.get(entity);
        AnimationComponent ac = Mappers.animations.get(entity);
        player.getAttributes().set(Attributes.WORLD_MOUSE_POSITION, getWorldPositionFromScreen(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
        player.getAttributes().set(Attributes.PLAYER_POSITION, mc.pos);

        if (player.getAttributes().get(Attributes.CURRENT_DIALOGUE) != null) {
            return;
        }

        for (int i = 0; i < getEntities().size(); i++) {
            if (handleEntityClick(getEntities().get(i), mc)) {
                return;
            }
        }

        if (handleMovementInput(mc, ac)) {
            if (player.getAttributes().hasKey(Attributes.PLAYER_LIGHT)) {
                Light light = player.getAttributes().get(Attributes.PLAYER_LIGHT);
                if (light instanceof PointLight pointLight) {
                    pointLight.setPosition(mc.pos.x, mc.pos.y);
                } else if (light instanceof ConeLight coneLight) {
                    coneLight.setPosition(mc.pos.x, mc.pos.y);
                    coneLight.setDirection(mc.dir.getDegree());
                }
            }
        } else {
            if(Animations.IDLE_TRANSITION_ANIMATIONS.containsKey(ac.currentAnimation)) {
                ac.nextAnim(Animations.IDLE_TRANSITION_ANIMATIONS.get(ac.currentAnimation));
            }
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) return;

        if (handleHotKeyInput()) {
        }
    }

    private boolean handleHotKeyInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            player.save()
                .thenRun(() -> Gdx.app.log("Saved", "Player saved"));
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            player.enableLight(LightType.CONE_LIGHT, 200);
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player.startDialogue(TestDialogue.get());
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            uiManager.openUI(player, "settings");
            return true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            uiManager.removeUI(player, "settings");
            return true;
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if(player.getAttributes().get(Attributes.PLAYER_IS_PAUSED)){
                uiManager.removeUI(player,"pause");
                player.getAttributes().set(Attributes.PLAYER_IS_PAUSED,false);
            }else{
                uiManager.openUI(player,"pause");
                player.getAttributes().set(Attributes.PLAYER_IS_PAUSED,true);
            }

        }
        return false;
    }

    public Vector2 getWorldPositionFromScreen(Vector3 v) {
        Vector3 vc = player.getCamera().unproject(new Vector3(v.x, v.y, v.z));
        return new Vector2(vc.x, vc.y);
    }

    private boolean handleEntityClick(Entity entity, TransformComponent mc) {
        ClickableComponent component = Mappers.clickables.get(entity);
        if (component != null && player.getAttributes().hasKey(Attributes.WORLD_MOUSE_POSITION)) {
            Vector2 mouseAtWorld = player.getAttributes().get(Attributes.WORLD_MOUSE_POSITION);
            if (mouseAtWorld.x >= component.bounds.x && mouseAtWorld.x <= component.bounds.x + component.bounds.width &&
                mouseAtWorld.y >= component.bounds.y && mouseAtWorld.y <= component.bounds.y + component.bounds.height) {

                String identification = component.id;
                NpcComponent npcComponent = Mappers.npcs.get(entity);

                if(npcComponent != null) {

                player.getAttributes().get(Attributes.ACTION_LISTENERS)
                    .forEach(
                        actionListener -> actionListener
                        .hoverOverNpc(player, npcComponent.npcType.getName(), mouseAtWorld.x, mouseAtWorld.y));

                player.getAttributes().set(Attributes.NPC_HOVER, npcComponent.npcType.getName());

                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    player.getAttributes().set(Attributes.LAST_NPC_CLICKED, identification);

                    TransformComponent transformComponent = Mappers.transforms.get(entity);
                    if (transformComponent != null && mc.pos.dst(transformComponent.pos) > TILE_SIZE * 2) {

                        // i'll clean this up later
                        /*Vector2 mouse = player.getAttributes().get(Attributes.WORLD_MOUSE_POSITION);
                        PathResult pathResult = PathFinder.findPath(
                            new Vector2(player.getTilePosX(), player.getTilePosY()),
                            new Vector2((int) (mouse.x / TILE_SIZE), (int) (mouse.y / TILE_SIZE)),
                            player.getGameMap());

                        mc.setPath(new ArrayDeque<>(pathResult.waypoints()));
                        mc.threshold = (float) (TILE_SIZE*2);*/

                    } else {
                        player.getAttributes().get(Attributes.ACTION_LISTENERS)
                            .forEach(actionListener -> actionListener
                                .clickNpc(player, npcComponent.npcType.getName(), identification, mouseAtWorld.x, mouseAtWorld.y));
                    }
                    return true;
                }
                }
            }
        }

        return false;
    }

    public static List<Vector2> extractPath(List<Vector2> waypoints) {
        List<Vector2> path = new ArrayList<>();
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Vector2 start = waypoints.get(i);
            Vector2 end = waypoints.get(i + 1);
            Vector2 current = new Vector2(start);

            while (!current.epsilonEquals(end, 1.0f)) {
                float dx = Math.signum(end.x - current.x);
                float dy = Math.signum(end.y - current.y);
                float nextX = current.x + (dx);
                float nextY = current.y + (dy != 0 && dx == 0 ? dy : 0);

                current.set(nextX, nextY);
                path.add(new Vector2(current));
            }
        }
        return path;
    }


    private boolean handleMovementInput(TransformComponent mc, AnimationComponent ac) {
        if (mc.disabled) return false;

        if (player.getAttributes().get(Attributes.ALL_OPENED_UI)
            .stream()
            .anyMatch(baseUi -> !baseUi.isWalkable())) {
            return false;
        }

        /*if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 mouse = player.getAttributes().get(Attributes.WORLD_MOUSE_POSITION);

            Path<PointI2> pathResult = PathFinder.findPathAStar(
                new Vector2(player.getTilePosX(), player.getTilePosY()), new Vector2(mouse.x, mouse.y), player.getGameMap());


            if(pathResult != null) {
                PointI2 prev = pathResult.pop();
                Direction dir = TransformSystem.getDirection(
                        new Vector2(prev.x, prev.y), player.getMovementComponent().pos);

                List<WalkDirection> walkDirections = new ArrayList<>();

                if(dir != null) {
                    walkDirections.add(new WalkDirection(dir, new Vector2(prev.x, prev.y)));
                }

                Gdx.app.log
                    ("Direction", dir + " ("+prev.x+","+prev.y+") -> " +
                        "("+player.getMovementComponent().pos.x+","+player.getMovementComponent().pos.y+")");

                while(!pathResult.isEmpty()) {
                    PointI2 p = pathResult.pop();
                    Direction direction = TransformSystem.getDirection(
                        new Vector2(p.x, p.y), new Vector2(prev.x, prev.y));

                    Gdx.app.log
                        ("Direction", direction + " ("+prev.x+","+prev.y+") -> " +
                            "("+p.x+","+p.y+")");

                    if(direction != null) {
                        walkDirections.add(new WalkDirection(direction, new Vector2(p.x, p.y)));
                    }

                    prev = p;
                }

                mc.move(walkDirections);
            }
            return true;
        }*/

        CollisionComponent collisionComponent = player.getCollisionComponent();
        collisionComponent.bounds.x = mc.pos.x;
        collisionComponent.bounds.y = mc.pos.y;

        Arrays.stream(player.getGameMap().getDoors())
            .filter(door -> door.rectangle().overlaps(collisionComponent.bounds))
            .findFirst()
            .ifPresent(door -> player.moveToMap(door.properties().get("nm_door").toString()));

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) && !(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))) {
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                mc.move(Direction.NE);
            } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                mc.move(Direction.NW);
            } else {
                mc.move(Direction.N);
            }
            ac.nextAnim(Animations.SKELETON_WALK_NORTH);
            return true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN) && !(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))) {
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                mc.move(Direction.SE);
            } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                mc.move(Direction.SW);
            } else {
                mc.move(Direction.S);
            }
            ac.nextAnim(Animations.SKELETON_WALK_SOUTH);
            return true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT) && !(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
            mc.move(Direction.W);
            ac.nextAnim(Animations.SKELETON_WALK_WEST);
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
            mc.move(Direction.E);
            ac.nextAnim(Animations.SKELETON_WALK_EAST);
            return true;
        }
        return false;
    }
}
