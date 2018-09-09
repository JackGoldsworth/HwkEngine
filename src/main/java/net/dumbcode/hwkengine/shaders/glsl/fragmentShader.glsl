#version 400 core

in vec2 pass_texture_coords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 light_color;
uniform float damper;
uniform float reflectivity;

void main(void)
{
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot = dot(unitNormal, unitLightVector);
    float brightness = max(nDot, 0.0);

    vec3 diffuse = brightness * light_color;
    vec3 vectorToCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    float specularFactor = dot(reflectedLightDirection, vectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, damper);
    vec3 finalSpecular = dampedFactor * reflectivity * light_color;
    out_Color = vec4(diffuse, 1.0) * texture(textureSampler, pass_texture_coords) + vec4(finalSpecular, 1.0);
}
