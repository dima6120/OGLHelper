#version 300 es

in vec4 a_Position;
in vec2 a_TexCoord;

uniform mat4 u_ProjM;
uniform mat4 u_ModelM;

out vec2 v_TexCoord;

void main() {
    mat4 mvp = u_ProjM * u_ModelM;
    v_TexCoord = a_TexCoord;
    gl_Position = mvp * a_Position;
}
