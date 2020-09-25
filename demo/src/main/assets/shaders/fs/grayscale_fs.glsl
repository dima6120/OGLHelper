#version 300 es

precision highp float;

uniform sampler2D u_Texture;
uniform float u_ColorFactor; // 0.0 = original, 1.0 = greyscale
in vec2 v_TexCoord;

layout(location = 0) out vec4 o_FragColor;

void main() {
    vec4 c = texture(u_Texture, v_TexCoord);
    float grey = 0.21 * c.r + 0.71 * c.g + 0.07 * c.b;
    o_FragColor = vec4(c.rgb * (1.0 - u_ColorFactor) + (grey * u_ColorFactor), 1.0);
}