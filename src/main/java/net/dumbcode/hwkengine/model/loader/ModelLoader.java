package net.dumbcode.hwkengine.model.loader;

import net.dumbcode.hwkengine.model.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader
{

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indeces)
    {
        int id = createVAO();
        bindIndexes(indeces);
        storeData(0, 3, positions);
        storeData(1, 2, textureCoords);
        unbindVAO();
        return new RawModel(id, indeces.length);
    }

    public int loadTexture(String textureName)
    {
        Texture texture = null;
        try
        {
            //TODO: understand java relative path system. (Cause this is gross)
            texture = TextureLoader.getTexture("PNG", new FileInputStream("src/test/resources/textures/" + textureName + ".png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        int id = texture.getTextureID();
        textures.add(id);
        return id;
    }

    private int createVAO()
    {
        int id = GL30.glGenVertexArrays();
        vaos.add(id);
        GL30.glBindVertexArray(id);
        return id;
    }

    private void storeData(int index, int coordSize, float[] data)
    {
        int id = GL15.glGenBuffers();
        vbos.add(id);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
        FloatBuffer buffer = convertFloat(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, coordSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO()
    {
        GL30.glBindVertexArray(0);
    }

    private void bindIndexes(int[] indeces)
    {
        int id = GL15.glGenBuffers();
        vbos.add(id);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id);
        IntBuffer buffer = storeData(indeces);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer storeData(int[] data)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer convertFloat(float[] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public void clean()
    {
        for(int vao : vaos)
        {
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo : vbos)
        {
            GL15.glDeleteBuffers(vbo);
        }
        for(int texture : textures)
        {
            GL11.glDeleteTextures(texture);
        }
    }
}
