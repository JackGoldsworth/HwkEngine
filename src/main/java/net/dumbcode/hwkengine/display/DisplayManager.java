package net.dumbcode.hwkengine.display;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public enum DisplayManager
{

    INSTANCE;

    public void createDisplay(String title, int width, int height)
    {

        ContextAttribs attribs = new ContextAttribs(3,2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try
        {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle(title);
        } catch (LWJGLException e)
        {
            e.printStackTrace();
        }

        GL11.glViewport(0,0, width, height); // Use the whole display to render.
    }

    public void destroyDisplay()
    {
        Display.destroy();
    }

    public void updateDisplay(int fpsCap)
    {
        Display.sync(fpsCap);
        Display.update();
    }
}
