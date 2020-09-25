package com.dima6120.oglHelper.enums

import android.opengl.GLES20
import com.dima6120.oglHelper.interfaces.GLConstEnumInterface

enum class ShaderType(val glesValue: Int) : GLConstEnumInterface {
    VERTEX(GLES20.GL_VERTEX_SHADER),
    FRAGMENT(GLES20.GL_FRAGMENT_SHADER);

    override fun getGLESValue(): Int = glesValue
}