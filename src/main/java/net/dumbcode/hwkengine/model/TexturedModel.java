package net.dumbcode.hwkengine.model;

import lombok.Getter;
import net.dumbcode.hwkengine.textures.ModelTexture;

@Getter
public class TexturedModel
{

    private RawModel model;
    private ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture)
    {
        this.texture = texture;
        this.model = model;
    }
}
