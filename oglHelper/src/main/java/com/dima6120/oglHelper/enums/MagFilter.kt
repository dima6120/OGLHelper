package com.dima6120.oglHelper.enums

import android.opengl.GLES20
import com.dima6120.oglHelper.interfaces.GLConstEnumInterface

enum class MagFilter(val glesValue: Int) : GLConstEnumInterface {
    NEAREST(GLES20.GL_NEAREST)
    , LINEAR(GLES20.GL_LINEAR);

    override fun getGLESValue(): Int = glesValue
}