package com.csun.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import static com.badlogic.gdx.graphics.GL20.*;

public class ShaderToyShader implements Disposable {
    private ShaderProgram shaderToy;
    private Mesh fullScreenMesh;
    private Texture iChannel0, iChannel1;

    public ShaderToyShader(FileHandle fragment, Texture iChannel0, Texture iChannel1) {
        this.iChannel0 = iChannel0;
        this.iChannel1 = iChannel1;
        shaderToy = new ShaderProgram(Gdx.files.internal("shaders/menushader/menuShader.vert"), fragment);
        if (!shaderToy.isCompiled())
            Gdx.app.error("Shader", "Shader compilation failed: " + shaderToy.getLog());

        fullScreenMesh = createFullScreenQuad();
    }
    private Mesh createFullScreenQuad() {
        Mesh mesh = new Mesh(true, 4, 6, new VertexAttribute(VertexAttributes.Usage.Position, 2, "vPos"));
        mesh.setVertices(new float[] {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
        });
        mesh.setIndices(new short[] { 0, 1, 2, 1, 2, 3 });
        return mesh;
    }

    public void uploadAllUniforms() {
        /*
        uniform vec3      iResolution;           // viewport resolution (in pixels)
uniform float     iTime;                 // shader playback time (in seconds)
uniform float     iTimeDelta;            // render time (in seconds)
uniform float     iFrameRate;            // shader frame rate
uniform int       iFrame;                // shader playback frame
uniform float     iChannelTime[4];       // channel playback time (in seconds)
uniform vec3      iChannelResolution[4]; // channel resolution (in pixels)
uniform vec4      iMouse;                // mouse pixel coords. xy: current (if MLB down), zw: click
uniform vec4      iDate;                 // (year, month, day, time in seconds)
uniform sampler2D iChannel0;
uniform sampler2D iChannel1;
         */

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Set shader uniforms
        shaderToy.setUniformf("iResolution", screenWidth, screenHeight, 0); // viewport resolution
        shaderToy.setUniformf("iTime", (System.nanoTime() / 1_000_000_000f)); // shader playback time
        shaderToy.setUniformf("iTimeDelta", Gdx.graphics.getDeltaTime()); // render time
        shaderToy.setUniformf("iFrameRate", 60f); // shader frame rate
        shaderToy.setUniformi("iFrame", (int) Gdx.graphics.getFrameId()); // shader playback frame

        // Set iChannel0 and iChannel1 textures
        shaderToy.setUniformi("iChannel0", 0); // bind iChannel0 to texture unit 0
        iChannel0.bind(0);
        shaderToy.setUniformi("iChannel1", 1); // bind iChannel1 to texture unit 1
        iChannel1.bind(1);

        // Example of setting other uniforms
        shaderToy.setUniform2fv("iChannelTime", new float[] {0f, 0f, 0f, 0f}, 0, 4); // channel playback time
        shaderToy.setUniform3fv("iChannelResolution", new float[] {screenWidth, screenHeight, 0f}, 0, 3); // channel resolution

        // Example of setting iMouse uniform (assuming mouse position is (x, y) and mouse click is (0 or 1))
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Vector2 click = new Vector2(Gdx.input.isButtonPressed(Input.Buttons.LEFT) ? 1 : 0, Gdx.input.isButtonPressed(Input.Buttons.RIGHT) ? 1 : 0);
        shaderToy.setUniform4fv("iMouse", new float[] {mousePos.x, mousePos.y, click.x, click.y}, 0, 4); // mouse pixel coords

        // Example of setting iDate uniform
        shaderToy.setUniform4fv("iDate", new float[] {0, 0, 0, 0}, 0, 4); // date
    }

    public void render() {
        shaderToy.bind();
        uploadAllUniforms();
        fullScreenMesh.render(shaderToy, GL_TRIANGLES);
        Gdx.gl.glActiveTexture(GL_TEXTURE0);
        Gdx.gl.glBindTexture(GL_TEXTURE_2D, 0);
        Gdx.gl.glActiveTexture(1);
        Gdx.gl.glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void dispose() {
        shaderToy.dispose();
        fullScreenMesh.dispose();
        iChannel0.dispose();
        iChannel1.dispose();
    }

    public ShaderProgram getShaderToy() {
        return shaderToy;
    }
}
