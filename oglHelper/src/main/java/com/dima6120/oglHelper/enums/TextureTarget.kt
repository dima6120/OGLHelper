package com.dima6120.oglHelper.enums

import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.opengl.GLES30
import com.dima6120.oglHelper.interfaces.GLConstEnumInterface

enum class TextureTarget(val glesValue: Int) : GLConstEnumInterface {
    TEXTURE_EXTERNAL_OES(GLES11Ext.GL_TEXTURE_EXTERNAL_OES),
    TEXTURE_2D(GLES20.GL_TEXTURE_2D),
    TEXTURE_3D(GLES30.GL_TEXTURE_3D),
    TEXTURE_2D_ARRAY(GLES30.GL_TEXTURE_2D_ARRAY),
    TEXTURE_CUBE_MAP (GLES20.GL_TEXTURE_CUBE_MAP);

    override fun getGLESValue(): Int = glesValue
}