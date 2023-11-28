public class Renderer {
    private SpriteBatch spriteBatch;

    public Renderer() {
        spriteBatch = new SpriteBatch();
    }

    public void renderFrame(Array<GameObject> gameObjects) {
        spriteBatch.begin();
        for (GameObject obj : gameObjects) {
            // Render each game object
        }
        spriteBatch.end();
    }
}
