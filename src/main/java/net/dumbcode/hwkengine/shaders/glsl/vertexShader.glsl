#version 400 core

in vec3 position;
in vec2 textureCoords;

out vec2 pass_texture_coords;

uniform mat4 transformation_matrix;

void main(void)
{
    gl_Position = transformation_matrix * vec4(position, 1.0);
    pass_texture_coords = textureCoords;
}