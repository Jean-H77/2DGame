public class AssetManager {
    private com.badlogic.gdx.assets.AssetManager manager;

    public AssetManager() {
        manager = new com.badlogic.gdx.assets.AssetManager();
    }

    public void loadAssets(Array<AssetDescriptor> assetDescriptors) {
        for (AssetDescriptor descriptor : assetDescriptors) {
            manager.load(descriptor);
        }
        manager.finishLoading();
    }

    public <T> T getAsset(String fileName, Class<T> type) {
        return manager.get(fileName, type);
    }
}
