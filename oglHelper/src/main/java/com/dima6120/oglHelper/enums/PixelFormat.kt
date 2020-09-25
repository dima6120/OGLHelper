package com.dima6120.oglHelper.enums

import android.opengl.GLES30

enum class PixelFormat(val iternalFormat: Int, val format: Int, val type: Type, val formatName: String) {
    // 8 бит на компоненту
    RED(GLES30.GL_R8, GLES30.GL_RED, Type.UNSIGNED_BYTE, "RED8"),
    RG(GLES30.GL_RG8, GLES30.GL_RG, Type.UNSIGNED_BYTE, "RG8"),
    RGB(GLES30.GL_RGB8, GLES30.GL_RGB, Type.UNSIGNED_BYTE, "RGB8"),
    RGBA(GLES30.GL_RGBA8, GLES30.GL_RGBA, Type.UNSIGNED_BYTE, "RGBA8"),
    // 16 бит на компоненту
    RED16F(GLES30.GL_R16F, GLES30.GL_RED, Type.HALF_FLOAT, "RED16"),
    RG16F(GLES30.GL_RG16F, GLES30.GL_RG, Type.FLOAT, "RG16"),
    // 32 бита на компоненту
    RED32F(GLES30.GL_R32F, GLES30.GL_RED, Type.FLOAT, "RED32"),
    RG32F(GLES30.GL_RG32F, GLES30.GL_RG, Type.FLOAT, "RG32");

    override fun toString(): String = formatName
}