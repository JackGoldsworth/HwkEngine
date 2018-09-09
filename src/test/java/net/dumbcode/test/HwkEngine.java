package net.dumbcode.test;

import net.dumbcode.hwkengine.display.DisplayManager;
import net.dumbcode.hwkengine.entities.Camera;
import net.dumbcode.hwkengine.entities.Entity;
import net.dumbcode.hwkengine.entities.Light;
import net.dumbcode.hwkengine.model.RawModel;
import net.dumbcode.hwkengine.model.TexturedModel;
import net.dumbcode.hwkengine.model.loader.ModelLoader;
import net.dumbcode.hwkengine.model.loader.OBJLoader;
import net.dumbcode.hwkengine.render.ModelRenderer;
import net.dumbcode.hwkengine.shaders.StaticShader;
import net.dumbcode.hwkengine.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
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
    private static Light light;
    private static Camera camera = new Camera();

    public static void main(String[] args)
    {
        displayManager = DisplayManager.INSTANCE;
        displayManager.createDisplay("Test Game", 1280, 720);
        model = OBJLoader.loadObBJModel(new File("src/test/resources/models/stall.obj"), modelLoader);
        staticShader = new StaticShader();
        renderer = new ModelRenderer(staticShader);
        texture = new ModelTexture(modelLoader.loadTexture("stall"));
        texture.setDamper(10f);
        texture.setReflectivity(1f);
        texturedModel = new TexturedModel(model, texture);
        entity = new Entity(texturedModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));
        loop();
    }

    private static void loop()
    {
        while(!Display.isCloseRequested())
        {
            GL11.glClearColor(0, 0, 1, 0);
            entity.increaseRotation(0, 1, 0);
            camera.move();
            renderer.prepare();
            staticShader.start();
            staticShader.loadLight(light);
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
