package com.dima6120.oglHelper.enums

import android.opengl.GLES20
import com.dima6120.oglHelper.interfaces.GLConstEnumInterface

enum class MinFilter(val glesValue: Int) : GLConstEnumInterface {
    NEAREST(GLES20.GL_NEAREST)
    , LINEAR(GLES20.GL_LINEAR)
    , NEAREST_MIPMAP_NEAREST(GLES20.GL_NEAREST_MIPMAP_NEAREST)
    , LINEAR_MIPMAP_NEAREST(GLES20.GL_LINEAR_MIPMAP_NEAREST)
    , NEAREST_MIPMAP_LINEAR(GLES20.GL_NEAREST_MIPMAP_LINEAR)
    , LINEAR_MIPMAP_LINEAR(GLES20.GL_LINEAR_MIPMAP_LINEAR);

    override fun getGLESValue(): Int = glesValue
}