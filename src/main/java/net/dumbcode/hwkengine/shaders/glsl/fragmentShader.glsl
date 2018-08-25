#version 400 core

in vec2 pass_texture_coords;
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 light_color;

void main(void)
{
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot = dot(unitNormal, unitLightVector);
    float brightness = max(nDot, 0.0);
    vec3 diffuse = brightness * light_color;

    out_Color = vec4(diffuse, 1.0) * texture(textureSampler, pass_texture_coords);
}
