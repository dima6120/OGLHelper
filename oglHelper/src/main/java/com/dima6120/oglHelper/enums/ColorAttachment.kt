package com.dima6120.oglHelper.enums

import android.opengl.GLES20
import android.opengl.GLES30
import com.dima6120.oglHelper.interfaces.GLConstEnumInterface

enum class ColorAttachment(val glesValue: Int) : GLConstEnumInterface {
    ATTACHMENT0(GLES20.GL_COLOR_ATTACHMENT0),
    ATTACHMENT1(GLES30.GL_COLOR_ATTACHMENT1),
    ATTACHMENT2(GLES30.GL_COLOR_ATTACHMENT2),
    ATTACHMENT3(GLES30.GL_COLOR_ATTACHMENT3),
    ATTACHMENT4(GLES30.GL_COLOR_ATTACHMENT4),
    ATTACHMENT5(GLES30.GL_COLOR_ATTACHMENT5),
    ATTACHMENT6(GLES30.GL_COLOR_ATTACHMENT6),
    ATTACHMENT7(GLES30.GL_COLOR_ATTACHMENT7),
    ATTACHMENT8(GLES30.GL_COLOR_ATTACHMENT8),
    ATTACHMENT9(GLES30.GL_COLOR_ATTACHMENT9),
    ATTACHMENT10(GLES30.GL_COLOR_ATTACHMENT10),
    ATTACHMENT11(GLES30.GL_COLOR_ATTACHMENT11),
    ATTACHMENT12(GLES30.GL_COLOR_ATTACHMENT12),
    ATTACHMENT13(GLES30.GL_COLOR_ATTACHMENT13),
    ATTACHMENT14(GLES30.GL_COLOR_ATTACHMENT14),
    ATTACHMENT15(GLES30.GL_COLOR_ATTACHMENT15);

    override fun getGLESValue(): Int = glesValue
}