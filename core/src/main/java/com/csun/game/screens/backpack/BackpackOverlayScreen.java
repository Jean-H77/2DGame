package com.csun.game.screens.backpack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.csun.game.MainGame;
import com.csun.game.ashley.components.BackpackComponent;
import com.csun.game.ashley.components.ItemComponent;
import com.csun.game.ashley.components.MovementComponent;
import com.csun.game.ashley.components.PlayerComponent;
import com.csun.game.ashley.components.TextureComponent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/** First screen of the application. Displayed after the application is created. */
@Singleton
public class BackpackOverlayScreen implements Screen {
    private static final String DIRECTORY_PATH = "backpack/";

    private final MainGame game;
    private ItemInfo itemInfo;

    private Stage stage;
    private PooledEngine engine;

    private Table overlayTable;
    private Table mainTable;
    private Table itemsTable;
    private Table itemInfoTable;

    private Label titleLabel ;


    private ItemComponent.ITEM_TYPES currentSelectItem;



    @Inject
    public BackpackOverlayScreen(MainGame game,PooledEngine engine) {
        this.game = game;

        this.stage = new Stage();
        this.engine = engine;
        this.itemInfo = new ItemInfo();

    }

    public void switchBackPackVisible(){
        if(mainTable.isVisible()){
            closeBackPack();
        }else{
            openBackPack();
        }
    }
    public void openBackPack(){
        mainTable.setVisible(true);
        resetItemsTable();
        resetItemInfoTable();
        ImmutableArray<Entity> backPackEntityList = engine.getEntitiesFor(Family.all(BackpackComponent.class).get());
        if(backPackEntityList.size() > 0) {
            BackpackComponent backPackComponent = backPackEntityList.get(0).getComponent(BackpackComponent.class);
            backPackComponent.isOpen = true;
        }
    }
    public void closeBackPack(){
        mainTable.setVisible(false);
        ImmutableArray<Entity> backPackEntityList = engine.getEntitiesFor(Family.all(BackpackComponent.class).get());
        if(backPackEntityList.size() > 0) {
            BackpackComponent backPackComponent = backPackEntityList.get(0).getComponent(BackpackComponent.class);
            backPackComponent.isOpen = false;
        }
    }
    private void resetItemsTable(){
        itemsTable.clear();
        itemsTable.top().left();
        itemsTable.background(createTRD("main-window.png"));
        itemsTable.pad(30f);
        ImmutableArray<Entity> backPackEntityList = engine.getEntitiesFor(Family.all(BackpackComponent.class).get());
        if(backPackEntityList.size() > 0){
            BackpackComponent backPackComponent = backPackEntityList.get(0).getComponent(BackpackComponent.class);
            int itemAddCount = 0;
            for (var entry : backPackComponent.itemMap.entrySet()) {
                Table currentCreateItemTable = new Table();
                itemsTable.add(currentCreateItemTable).width(60f).height(60f);


                Table itemWindowBg = new Table();
                itemWindowBg.background(createTRD("item-window.png"));
                Image itemImage = createItemByItemName(entry.getKey());
                itemWindowBg.add(itemImage).width(40f).height(40f);



                Table itemCountOverlay = new Table();
                itemCountOverlay.bottom().right();
                Table itemCountBg = new Table();
                itemCountBg.background(createTRD("item-count-label.png"));
                itemCountOverlay.add(itemCountBg).width(35f).height(35f).padBottom(-8f).padRight(-8f);
                itemCountBg.center();
                itemCountBg.add(new Label(entry.getValue().toString(),generateCountFont(10)));


                currentCreateItemTable.stack(
                    itemWindowBg,
                    itemCountOverlay
                ).width(60f).height(60f).pad(10f);
                currentCreateItemTable.addListener(new ClickListener(){
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        super.touchUp(event, x, y, pointer, button);
                        currentSelectItem = entry.getKey();
                        resetItemInfoTable();
                    }
                });
                itemAddCount++;
                if(itemAddCount % 4 == 0){
                    itemsTable.row();
                }
            }
            for(int i = 0; i < 16;i ++){
                if(i >= backPackComponent.itemMap.size()){
                    Table currentCreateItemTable = new Table();
                    itemsTable.add(currentCreateItemTable).width(60f).height(60f);

                    Table itemWindowBg = new Table();
                    itemWindowBg.background(createTRD("item-window.png"));
                    currentCreateItemTable.stack(
                        itemWindowBg
                    ).width(60f).height(60f).pad(10f);
                    if((i + 1 ) % 4 == 0){
                        itemsTable.row();
                    }
                }
            }
        }

    }
    private void resetItemInfoTable(){
        itemInfoTable.clear();
        itemInfoTable.center();
        itemInfoTable.background(createTRD("main-window.png"));

        if(currentSelectItem != null){
            Label titleInfoLabel = new Label(itemInfo.getItemName(currentSelectItem),generateCountFont(20));
            Label textInfoLabel = new Label(itemInfo.getItemInfo(currentSelectItem),generateCountFont(12));
            ImageTextButton useItemButton = new ImageTextButton("Use Item", new ImageTextButton.ImageTextButtonStyle(
                createTRD("button.png"),null,null,generateCountFont(14).font
            ));
            titleInfoLabel.setAlignment(Align.center);
            titleInfoLabel.setWrap(true);
            textInfoLabel.setAlignment(Align.center);
            textInfoLabel.setWrap(true);
            useItemButton.addListener(new ClickListener(){
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    ImmutableArray<Entity> backPackEntityList = engine.getEntitiesFor(Family.all(BackpackComponent.class).get());
                    if(backPackEntityList.size() > 0) {
                        BackpackComponent backPackComponent = backPackEntityList.get(0).getComponent(BackpackComponent.class);
                        backPackComponent.itemMap.put(currentSelectItem,backPackComponent.itemMap.get(currentSelectItem) - 1);
                        if(backPackComponent.itemMap.get(currentSelectItem) == 0){
                            backPackComponent.itemMap.remove(currentSelectItem);
                            currentSelectItem = null;
                        }
                        resetItemsTable();
                        resetItemInfoTable();
                    }
                }
            });

            itemInfoTable.add(titleInfoLabel).width(180f).padBottom(20f).row();
            itemInfoTable.add(createItemByItemName(currentSelectItem)).width(60f).height(60f).padBottom(10f).row();
            itemInfoTable.add(textInfoLabel).width(180f).padBottom(10f).row();
            itemInfoTable.add(useItemButton).width(150f).height(40f);
        }else{
            Label textInfoLabel = new Label("Select item to see info",generateCountFont(24));
            textInfoLabel.setAlignment(Align.center);
            textInfoLabel.setWrap(true);
            itemInfoTable.add(textInfoLabel).width(180f);
        }

    }

    @Override
    public void show() {
        titleLabel = new Label("Inventory",generateCountFont(48));
        titleLabel.setAlignment(Align.center);
        overlayTable = new Table();
        mainTable = new Table();
        itemsTable = new Table();
        itemInfoTable = new Table();
        overlayTable.center();
        overlayTable.setFillParent(true);

        stage.addActor(overlayTable);
        overlayTable.add(mainTable).width(700f).height(500f);

        mainTable.background(createTRD("main-window.png"));

        mainTable.add(titleLabel).width(300f).height(50f).padBottom(10f).colspan(2).row();
        mainTable.add(itemsTable).width(300f).height(300f).pad(20f);
        mainTable.add(itemInfoTable).width(200f).height(300f).pad(20f);

        resetItemsTable();
        resetItemInfoTable();

        closeBackPack();
    }

    @Override
    public void render(float delta) {
        ImmutableArray<Entity> backPackEntityList = engine.getEntitiesFor(Family.all(BackpackComponent.class).get());
        if(backPackEntityList.size() > 0) {
            BackpackComponent backPackComponent = backPackEntityList.get(0).getComponent(BackpackComponent.class);
            if(backPackComponent.isOpen && !mainTable.isVisible()){
                openBackPack();
            }else if(!backPackComponent.isOpen && mainTable.isVisible()){
                closeBackPack();
            }

        }
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.

    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        stage.dispose();
    }
    public InputProcessor getInputProcessor(){
        return stage;
    }

    private void useItem(ItemComponent.ITEM_TYPES itemNames){
        switch (itemNames){
            case AXE -> {
            }
            case KNIFE -> {
            }
            case HEALTH_POTION -> {
            }
            case MANA_POTION -> {
            }
        }
    }
    private Image createImage(String path) {
        return new Image(new TextureRegionDrawable(new TextureRegion(new Texture(DIRECTORY_PATH + path))));
    }
    private TextureRegionDrawable createTRD(String path) {
        return new TextureRegionDrawable(new TextureRegion(new Texture(DIRECTORY_PATH + path)));
    }
    private Image createItemByItemName(ItemComponent.ITEM_TYPES itemNames) {
        switch (itemNames){
            case AXE -> {
                return new Image(new TextureRegionDrawable(new TextureRegion(new Texture(DIRECTORY_PATH + "axe-icon.png"))));
            }
            case KNIFE -> {
                return new Image(new TextureRegionDrawable(new TextureRegion(new Texture(DIRECTORY_PATH + "knife-icon.png"))));
            }
            case HEALTH_POTION -> {
                return new Image(new TextureRegionDrawable(new TextureRegion(new Texture(DIRECTORY_PATH + "health-point-icon.png"))));
            }
            case MANA_POTION -> {
                return new Image(new TextureRegionDrawable(new TextureRegion(new Texture(DIRECTORY_PATH + "mana-point-icon.png"))));
            }
        }
        return null;
    }

    private Label.LabelStyle generateCountFont(int fontSize){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("backpack/Koulen-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        BitmapFont font = generator.generateFont(parameter);
        font.getData().scale(fontSize/ 500f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return new Label.LabelStyle(font,null);

    }
}
