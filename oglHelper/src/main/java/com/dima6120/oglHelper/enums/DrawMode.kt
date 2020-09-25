package com.dima6120.oglHelper.enums

import android.opengl.GLES20
import com.dima6120.oglHelper.interfaces.GLConstEnumInterface

enum class DrawMode(val glesValue: Int) : GLConstEnumInterface {
    POINTS(GLES20.GL_POINTS),
    LINE_STRIP(GLES20.GL_LINE_STRIP),
    LINE_LOOP(GLES20.GL_LINE_LOOP),
    LINES(GLES20.GL_LINES),
    TRIANGLE_STRIP(GLES20.GL_TRIANGLE_STRIP),
    TRIANGLE_FAN(GLES20.GL_TRIANGLE_FAN),
    TRIANGLES(GLES20.GL_TRIANGLES),
    ;

    override fun getGLESValue(): Int = glesValue
}