package net.dumbcode.test;

import net.dumbcode.hwkengine.display.DisplayManager;
import net.dumbcode.hwkengine.model.ModelRenderer;
import net.dumbcode.hwkengine.model.RawModel;
import net.dumbcode.hwkengine.model.loader.Loader;
import net.dumbcode.hwkengine.shaders.StaticShader;
import org.lwjgl.opengl.Display;

public class HwkEngine
{

    private static DisplayManager displayManager;
    private static Loader loader = new Loader();
    private static ModelRenderer renderer = new ModelRenderer();
    private static RawModel model;
    private static StaticShader staticShader;

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

    public static void main(String[] args)
    {
        displayManager = DisplayManager.INSTANCE;
        displayManager.createDisplay("Test Game", 1280, 720);
        model = loader.loadToVAO(vertices, indices);
        staticShader = new StaticShader();
        loop();
    }

    private static void loop()
    {
        while(!Display.isCloseRequested())
        {
            renderer.prepare();
            staticShader.start();
            renderer.render(model);
            staticShader.stop();
            displayManager.updateDisplay(60);
        }
        staticShader.clean();
        loader.clean();
        displayManager.destroyDisplay();
    }
}
