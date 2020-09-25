package com.dima6120.oglHelper.enums

import android.opengl.GLES20
import com.dima6120.oglHelper.interfaces.GLConstEnumInterface

enum class TextureWrap(val glesValue: Int) : GLConstEnumInterface {
    CLAMP_TO_EDGE(GLES20.GL_CLAMP_TO_EDGE)
    , MIRRORED_REPEAT(GLES20.GL_MIRRORED_REPEAT)
    , REPEAT(GLES20.GL_REPEAT);

    override fun getGLESValue(): Int = glesValue
}