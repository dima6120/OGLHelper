package com.dima6120.oglHelper.enums

import android.opengl.GLES20
import android.opengl.GLES30
import com.dima6120.oglHelper.interfaces.GLConstEnumInterface

enum class BlendEquation(val glesValue: Int) : GLConstEnumInterface {
    ADD(GLES20.GL_FUNC_ADD),
    SUB(GLES20.GL_FUNC_SUBTRACT),
    REVERSE_SUB(GLES20.GL_FUNC_REVERSE_SUBTRACT),
    MIN(GLES30.GL_MIN),
    MAX(GLES30.GL_MAX);

    override fun getGLESValue(): Int = glesValue
}