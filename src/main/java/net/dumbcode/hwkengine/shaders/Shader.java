package net.dumbcode.hwkengine.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class Shader
{
    private int programID;
    private int vertexID;
    private int fragmentID;

    public Shader(String vertexFile, String fragmentFile)
    {
        this.vertexID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        this.fragmentID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        this.programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
    }

    public void start()
    {
        GL20.glUseProgram(programID);
    }

    public void stop()
    {
        GL20.glUseProgram(0);
    }

    public void clean()
    {
        stop();
        GL20.glDetachShader(programID, vertexID);
        GL20.glDetachShader(programID, fragmentID);
        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);
        GL20.glDeleteProgram(programID);
    }

    public abstract void bindAttributes();

    public void bindAttribute(int attribute, String name)
    {
        GL20.glBindAttribLocation(programID, attribute, name);
    }

    private static int loadShader(String file, int type)
    {
        StringBuilder source = new StringBuilder();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null)
            {
                source.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        int shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, source);
        GL20.glCompileShader(shaderId);
        if(GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
        {
            System.out.println(GL20.glGetShaderInfoLog(shaderId, 500));
            System.err.println("Could not compile shader");
            System.exit(-1);
        }
        return shaderId;
    }
}
