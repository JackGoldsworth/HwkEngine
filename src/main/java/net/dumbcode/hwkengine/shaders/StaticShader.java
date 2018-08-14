package net.dumbcode.hwkengine.shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends Shader
{

    //TODO: This is the worst thing i've ever seen.
    private static final String VERTEX = "src/main/java/net/dumbcode/hwkengine/shaders/glsl/vertexShader.glsl";
    private static final String FRAGMENT = "src/main/java/net/dumbcode/hwkengine/shaders/glsl/fragmentShader.glsl";

    private int transformMatrix;

    public StaticShader()
    {
        super(VERTEX, FRAGMENT);
    }

    @Override
    public void bindAttributes()
    {
        super.bindAttribute(0, "positions");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    public void getAllUniformLocations()
    {
        transformMatrix = super.getUniformLocation("transformation_matrix");
    }

    public void loadMatrix(Matrix4f matrix)
    {
        super.loadMatrix(transformMatrix, matrix);
    }
}
