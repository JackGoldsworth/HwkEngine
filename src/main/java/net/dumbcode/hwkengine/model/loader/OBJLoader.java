package net.dumbcode.hwkengine.model.loader;

import net.dumbcode.hwkengine.model.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader
{

    public static RawModel loadObBJModel(File file, ModelLoader loader)
    {
        FileReader fileReader = null;
        try
        {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        float[] verticesArray;
        float[] normalsArray;
        float[] textureArray = null;
        int[] indicesArray;
        try
        {
            while (true)
            {
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if (line.startsWith("v "))
                {
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                } else if (line.startsWith("vt "))
                {
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                } else if (line.startsWith("vn "))
                {
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                } else if (line.startsWith("f "))
                {
                    textureArray = new float[vertices.size() * 2];
                    normalsArray = new float[vertices.size() * 3];
                    break;
                }
            }
            while (line != null)
            {
                if (!line.startsWith("f "))
                {
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for (Vector3f vertex : vertices)
        {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        for (int i = 0; i < indices.size(); i++)
        {
            indicesArray[i] = indices.get(i);
        }
        return loader.loadToVAO(verticesArray, textureArray, indicesArray);
    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalArray)
    {
        int currentPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentPointer);
        Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentPointer * 2] = currentTexture.x;
        textureArray[currentPointer * 2 + 1] = 1 - currentTexture.y;
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalArray[currentPointer * 3] = currentNorm.x;
        normalArray[currentPointer * 3 + 1] = currentNorm.y;
        normalArray[currentPointer * 3 + 2] = currentNorm.z;
    }
}
