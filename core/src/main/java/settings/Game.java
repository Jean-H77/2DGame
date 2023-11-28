public class MyGame extends Game {
    private AssetManager assetManager;
    private Renderer renderer;
    private InputManager inputManager;
    private AudioManager audioManager;
    private SettingsManager settingsManager;

    @Override
    public void create() {
        assetManager = new AssetManager();
        assetManager.loadAssets(assetDescriptors);

        renderer = new Renderer();
        inputManager = new InputManager();
        audioManager = new AudioManager();
        settingsManager = new SettingsManager();

        setScreen(new GameScreen(this));
    }
}
