#version 330 core

layout(location = 0) in vec2 vPos; // Vertex position attribute

void main() {
    // Transform vertex position to clip space
    gl_Position = vec4(vPos, 1., 1.);
}
