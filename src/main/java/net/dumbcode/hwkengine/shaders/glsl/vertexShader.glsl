#version 400 core

in vec3 position;
in vec3 normal;
in vec2 textureCoords;

out vec2 pass_texture_coords;
out vec3 surfaceNormal;
out vec3 toLightVector;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;
uniform vec3 light_position;

void main(void)
{
    vec4 worldPosition = transformation_matrix * vec4(position, 1.0);
    gl_Position = projection_matrix * view_matrix * transformation_matrix * vec4(position, 1.0);
    pass_texture_coords = textureCoords;
    surfaceNormal = (transformation_matrix * vec4(normal, 0.0)).xyz;
    toLightVector = light_position - worldPosition.xyz;
}