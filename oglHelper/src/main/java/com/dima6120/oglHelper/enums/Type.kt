package com.dima6120.oglHelper.enums

import android.opengl.GLES20
import android.opengl.GLES30
import com.dima6120.oglHelper.interfaces.GLConstEnumInterface

enum class Type(val glesValue: Int) : GLConstEnumInterface {
    BYTE(GLES20.GL_BYTE),
    UNSIGNED_BYTE(GLES20.GL_UNSIGNED_BYTE),
    SHORT(GLES20.GL_SHORT),
    UNSIGNED_SHORT(GLES20.GL_UNSIGNED_SHORT),
    INT(GLES20.GL_INT),
    UNSIGNED_INT(GLES20.GL_UNSIGNED_INT),
    FLOAT(GLES20.GL_FLOAT),
    HALF_FLOAT(GLES30.GL_HALF_FLOAT),
    FIXED(GLES20.GL_FIXED);

    override fun getGLESValue(): Int = glesValue
}