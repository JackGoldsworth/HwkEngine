package net.dumbcode.hwkengine.entities;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.util.vector.Vector3f;

@Getter
@Setter
public class Light {

    private Vector3f position;
    private Vector3f color;

    public Light(Vector3f position, Vector3f color)
    {
        this.position = position;
        this.color = color;
    }
}
