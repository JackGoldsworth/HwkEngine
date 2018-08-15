package net.dumbcode.test;

import net.dumbcode.hwkengine.display.DisplayManager;
import net.dumbcode.hwkengine.entities.Camera;
import net.dumbcode.hwkengine.entities.Entity;
import net.dumbcode.hwkengine.model.RawModel;
import net.dumbcode.hwkengine.model.TexturedModel;
import net.dumbcode.hwkengine.model.loader.ModelLoader;
import net.dumbcode.hwkengine.render.ModelRenderer;
import net.dumbcode.hwkengine.shaders.StaticShader;
import net.dumbcode.hwkengine.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

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

    static float[] vertices = {
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,

            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,

            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,

            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f

    };

    static float[] textureCoords = {

            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0,0,
            0,1,
            1,1,
            1,0


    };

    static int[] indices = {
            0, 1, 3,
            3, 1, 2,
            4, 5, 7,
            7, 5, 6,
            8, 9, 11,
            11, 9, 10,
            12, 13, 15,
            15, 13, 14,
            16, 17, 19,
            19, 17, 18,
            20, 21, 23,
            23, 21, 22

    };

    public static void main(String[] args)
    {
        displayManager = DisplayManager.INSTANCE;
        displayManager.createDisplay("Test Game", 1280, 720);
        model = modelLoader.loadToVAO(vertices, textureCoords, indices);
        staticShader = new StaticShader();
        renderer = new ModelRenderer(staticShader);
        texture = new ModelTexture(modelLoader.loadTexture("test"));
        texturedModel = new TexturedModel(model, texture);
        entity = new Entity(texturedModel, new Vector3f(0, 0, -4), 0, 0, 0, 1);
        loop();
    }

    private static void loop()
    {
        while(!Display.isCloseRequested())
        {
            entity.increaseRotation(1, 0, 0);
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
