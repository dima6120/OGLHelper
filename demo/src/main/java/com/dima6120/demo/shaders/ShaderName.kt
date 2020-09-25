package com.dima6120.demo.shaders

enum class ShaderName(val vs: String, val fs: String, val attributes: Array<String>, val uniforms: Array<String>) {
    TEXTURE_DRAW(
        "textured_shape_vs",
        "texture_draw_fs",
        arrayOf("a_Position", "a_TexCoord"),
        arrayOf("u_ProjM", "u_ModelM", "u_Texture", "u_Opacity")
    ),

    GRAYSCALE(
        "textured_shape_vs",
        "grayscale_fs",
        arrayOf("a_Position", "a_TexCoord"),
        arrayOf("u_ProjM", "u_ModelM", "u_Texture", "u_ColorFactor")
    )
}