package net.dumbcode.hwkengine.shaders;

import net.dumbcode.hwkengine.entities.Camera;
import net.dumbcode.hwkengine.entities.Light;
import net.dumbcode.hwkengine.utils.MathUtils;
import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends Shader
{

    //TODO: This is the worst thing i've ever seen.
    private static final String VERTEX = "src/main/java/net/dumbcode/hwkengine/shaders/glsl/vertexShader.glsl";
    private static final String FRAGMENT = "src/main/java/net/dumbcode/hwkengine/shaders/glsl/fragmentShader.glsl";

    private int transformMatrix;
    private int projectionMatrix;
    private int viewMatrix;
    private int lightPos;
    private int lightColor;

    public StaticShader()
    {
        super(VERTEX, FRAGMENT);
    }

    @Override
    public void bindAttributes()
    {
        super.bindAttribute(0, "positions");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    public void getAllUniformLocations()
    {
        transformMatrix = super.getUniformLocation("transformation_matrix");
        projectionMatrix = super.getUniformLocation("projection_matrix");
        viewMatrix = super.getUniformLocation("view_matrix");
        lightPos = super.getUniformLocation("light_position");
        lightColor = super.getUniformLocation("light_color");
    }

    public void loadLight(Light light)
    {
        super.loadVec(lightColor, light.getColor());
        super.loadVec(lightPos, light.getPosition());
    }

    public void loadMatrix(Matrix4f matrix)
    {
        super.loadMatrix(transformMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix)
    {
        super.loadMatrix(projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera)
    {
        Matrix4f viewMatrix = MathUtils.createViewMatrix(camera);
        super.loadMatrix(this.viewMatrix, viewMatrix);
    }
}
