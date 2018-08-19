package net.dumbcode.test;

import net.dumbcode.hwkengine.display.DisplayManager;
import net.dumbcode.hwkengine.entities.Camera;
import net.dumbcode.hwkengine.entities.Entity;
import net.dumbcode.hwkengine.model.RawModel;
import net.dumbcode.hwkengine.model.TexturedModel;
import net.dumbcode.hwkengine.model.loader.ModelLoader;
import net.dumbcode.hwkengine.model.loader.OBJLoader;
import net.dumbcode.hwkengine.render.ModelRenderer;
import net.dumbcode.hwkengine.shaders.StaticShader;
import net.dumbcode.hwkengine.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;

public class HwkEngine
{

    private static DisplayManager displayManager;
    private static ModelLoader modelLoader = new ModelLoader();
    private static ModelRenderer renderer;
    private static RawModel model;
    private static StaticShader staticShader;
    private static ModelTexture texture;
    private static TexturedModel texturedModel;
    private static Entity entity;
    private static Camera camera = new Camera();

    public static void main(String[] args)
    {
        displayManager = DisplayManager.INSTANCE;
        displayManager.createDisplay("Test Game", 1280, 720);
        model = OBJLoader.loadObBJModel(new File("src/test/resources/models/stall.obj"), modelLoader);
        staticShader = new StaticShader();
        renderer = new ModelRenderer(staticShader);
        texture = new ModelTexture(modelLoader.loadTexture("stall"));
        texturedModel = new TexturedModel(model, texture);
        entity = new Entity(texturedModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        loop();
    }

    private static void loop()
    {
        while(!Display.isCloseRequested())
        {
            entity.increaseRotation(0, 1, 0);
            camera.move();
            renderer.prepare();
            staticShader.start();
            staticShader.loadViewMatrix(camera);
            renderer.render(entity, staticShader);
            staticShader.stop();
            displayManager.updateDisplay(60);
        }
        staticShader.clean();
        modelLoader.clean();
        displayManager.destroyDisplay();
    }
}
