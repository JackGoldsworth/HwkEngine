import net.dumbcode.hwkengine.display.DisplayManager;
import net.dumbcode.hwkengine.model.ModelRenderer;
import net.dumbcode.hwkengine.model.RawModel;
import net.dumbcode.hwkengine.model.loader.Loader;
import org.lwjgl.opengl.Display;

public class HwkEngine
{

    private static DisplayManager displayManager;
    private static Loader loader = new Loader();
    private static ModelRenderer renderer = new ModelRenderer();
    private static RawModel model;

    private static float[] vertices = {
            -0.5f, 0.5f, 0f,//v0
            -0.5f, -0.5f, 0f,//v1
            0.5f, -0.5f, 0f,//v2
            0.5f, 0.5f, 0f,//v3
    };

    private static int[] indeces = {
            0, 1, 3,
            3, 1, 2
    };

    public static void main(String[] args)
    {
        displayManager = DisplayManager.INSTANCE;
        displayManager.createDisplay("Test Game", 1280, 720);
        model = loader.loadToVAO(vertices, indeces);
        loop();
    }

    private static void loop()
    {
        while(!Display.isCloseRequested())
        {
            renderer.prepare();
            renderer.render(model);
            displayManager.updateDisplay(60);
        }
        loader.clean();
        displayManager.destroyDisplay();
    }
}
