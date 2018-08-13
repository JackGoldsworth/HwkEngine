package net.dumbcode.test;

import net.dumbcode.hwkengine.display.DisplayManager;
import net.dumbcode.hwkengine.model.TexturedModel;
import net.dumbcode.hwkengine.render.ModelRenderer;
import net.dumbcode.hwkengine.model.RawModel;
import net.dumbcode.hwkengine.model.loader.ModelLoader;
import net.dumbcode.hwkengine.shaders.StaticShader;
import net.dumbcode.hwkengine.textures.ModelTexture;
import org.lwjgl.opengl.Display;

public class HwkEngine
{

    private static DisplayManager displayManager;
    private static ModelLoader modelLoader = new ModelLoader();
    private static ModelRenderer renderer = new ModelRenderer();
    private static RawModel model;
    private static StaticShader staticShader;
    private static ModelTexture texture;
    private static TexturedModel texturedModel;

    private static float[] vertices = {
            -0.5f, 0.5f, 0f,//v0
            -0.5f, -0.5f, 0f,//v1
            0.5f, -0.5f, 0f,//v2
            0.5f, 0.5f, 0f,//v3
    };

    private static int[] indices = {
            0, 1, 3,
            3, 1, 2
    };

    private static float[] textureCoords = {
            0,0,
            0,1,
            1,1,
            1,0
    };

    public static void main(String[] args)
    {
        displayManager = DisplayManager.INSTANCE;
        displayManager.createDisplay("Test Game", 1280, 720);
        model = modelLoader.loadToVAO(vertices, textureCoords, indices);
        staticShader = new StaticShader();
        texture = new ModelTexture(modelLoader.loadTexture("test"));
        texturedModel = new TexturedModel(model, texture);
        loop();
    }

    private static void loop()
    {
        while(!Display.isCloseRequested())
        {
            renderer.prepare();
            staticShader.start();
            renderer.render(texturedModel);
            staticShader.stop();
            displayManager.updateDisplay(60);
        }
        staticShader.clean();
        modelLoader.clean();
        displayManager.destroyDisplay();
    }
}
