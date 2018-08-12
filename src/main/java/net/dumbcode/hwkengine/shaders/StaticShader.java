package net.dumbcode.hwkengine.shaders;

public class StaticShader extends Shader
{

    //TODO: This is the worst thing i've ever seen.
    private static final String VERTEX = "src/main/java/net/dumbcode/hwkengine/shaders/glsl/vertexShader.glsl";
    private static final String FRAGMENT = "src/main/java/net/dumbcode/hwkengine/shaders/glsl/fragmentShader.glsl";

    public StaticShader()
    {
        super(VERTEX, FRAGMENT);
    }

    @Override
    public void bindAttributes()
    {
        super.bindAttribute(0, "positions");
    }
}
