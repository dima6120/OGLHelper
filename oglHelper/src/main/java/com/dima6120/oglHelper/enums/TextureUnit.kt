package com.dima6120.oglHelper.enums

import android.opengl.GLES20
import com.dima6120.oglHelper.interfaces.GLConstEnumInterface

enum class TextureUnit(val glesValue: Int) : GLConstEnumInterface {
    UNIT0(GLES20.GL_TEXTURE0),
    UNIT1(GLES20.GL_TEXTURE1),
    UNIT2(GLES20.GL_TEXTURE2),
    UNIT3(GLES20.GL_TEXTURE3),
    UNIT4(GLES20.GL_TEXTURE4),
    UNIT5(GLES20.GL_TEXTURE5),
    UNIT6(GLES20.GL_TEXTURE6),
    UNIT7(GLES20.GL_TEXTURE7),
    UNIT8(GLES20.GL_TEXTURE8),
    UNIT9(GLES20.GL_TEXTURE9),
    UNIT10(GLES20.GL_TEXTURE10),
    UNIT11(GLES20.GL_TEXTURE11),
    UNIT12(GLES20.GL_TEXTURE12),
    UNIT13(GLES20.GL_TEXTURE13),
    UNIT14(GLES20.GL_TEXTURE14),
    UNIT15(GLES20.GL_TEXTURE15),
    UNIT16(GLES20.GL_TEXTURE16),
    UNIT17(GLES20.GL_TEXTURE17),
    UNIT18(GLES20.GL_TEXTURE18),
    UNIT19(GLES20.GL_TEXTURE19),
    UNIT20(GLES20.GL_TEXTURE20),
    UNIT21(GLES20.GL_TEXTURE21),
    UNIT22(GLES20.GL_TEXTURE22),
    UNIT23(GLES20.GL_TEXTURE23),
    UNIT24(GLES20.GL_TEXTURE24),
    UNIT25(GLES20.GL_TEXTURE25),
    UNIT26(GLES20.GL_TEXTURE26),
    UNIT27(GLES20.GL_TEXTURE27),
    UNIT28(GLES20.GL_TEXTURE28),
    UNIT29(GLES20.GL_TEXTURE29),
    UNIT30(GLES20.GL_TEXTURE30),
    UNIT31(GLES20.GL_TEXTURE31);

    override fun getGLESValue(): Int = glesValue
}