package net.dumbcode.hwkengine.textures;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelTexture
{

    private final int id;

    private float damper = 1f;
    private float reflectivity = 0f;

    public ModelTexture(int id)
    {
        this.id = id;
    }
}
