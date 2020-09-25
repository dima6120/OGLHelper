package com.dima6120.oglHelper.utils

import android.opengl.GLES20
import com.dima6120.oglHelper.data.RGB
import com.dima6120.oglHelper.data.RGBA
import com.dima6120.oglHelper.enums.BlendEquation
import com.dima6120.oglHelper.enums.ColorFactor

class GLUtils {
    companion object {
        private val blendColor = RGBA(0f, 0f, 0f, 1f)

        fun setDefaultBlendMode() {
            GLES20.glBlendEquation(GLES20.GL_FUNC_ADD)
            GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA)
        }

        fun setBlendMode(equation: BlendEquation, sfactor: ColorFactor, dfactor: ColorFactor) {
            GLES20.glBlendEquation(equation.glesValue)
            GLES20.glBlendFunc(sfactor.glesValue, dfactor.glesValue)
        }

        fun setBlendColor(r: Float, g: Float, b: Float, a: Float) {
            blendColor.set(r, g, b, a)
            GLES20.glBlendColor(r, g, b, a)
        }

        fun setBlendColor(rgba: RGBA) {
            blendColor.set(rgba)
            GLES20.glBlendColor(rgba.r, rgba.g, rgba.b, rgba.a)
        }

        fun setBlendColor(rgb: RGB) {
            blendColor.set(rgb)
            GLES20.glBlendColor(rgb.r, rgb.g, rgb.b, 1f)
        }

        fun setBlendAlpha(a: Float) {
            blendColor.a = a
            GLES20.glBlendColor(blendColor.r, blendColor.g, blendColor.b, a)
        }

        fun setBlendEnabled(enabled: Boolean) {
            if (enabled) {
                GLES20.glEnable(GLES20.GL_BLEND)
            } else {
                GLES20.glDisable(GLES20.GL_BLEND)
            }
        }

        fun clearColor(r: Float, g: Float, b: Float, a: Float) {
            GLES20.glClearColor(r, g, b, a)
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        }

        fun clearColor(rgba: RGBA) {
            GLES20.glClearColor(rgba.r, rgba.g, rgba.b, rgba.a)
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        }

        fun clearColor(rgb: RGB) {
            GLES20.glClearColor(rgb.r, rgb.g, rgb.b, 1f)
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        }
    }
}