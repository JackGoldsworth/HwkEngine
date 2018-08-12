package net.dumbcode.hwkengine.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RawModel
{

    private int id;
    private int vertexCount;

    public RawModel(int id, int vertexCount)
    {
        this.id = id;
        this.vertexCount = vertexCount;
    }
}
