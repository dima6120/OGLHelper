package com.dima6120.oglHelper.utils

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import com.dima6120.oglHelper.enums.ShaderType

class ShaderUtils {
    companion object {
        private val TAG = ShaderUtils::class.java.simpleName

        fun createProgram(vsShaderId: Int, fsShaderId: Int): Int {
            val programId = GLES20.glCreateProgram()

            if (programId == 0) {
                return -1
            }

            GLES20.glAttachShader(programId, vsShaderId)
            GLES20.glAttachShader(programId, fsShaderId)
            GLES20.glLinkProgram(programId)

            val linkStatus = IntArray(1)

            GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, linkStatus, 0)

            if (linkStatus[0] == 0) {
                val error = GLES20.glGetProgramInfoLog(programId)

                Log.d(TAG, error)

                GLES20.glDeleteProgram(programId)

                return -1
            }

            return programId
        }

        fun createShader(context: Context, type: ShaderType, shaderRawId: Int): Int {
            try {
                val shaderText = FileUtils.readTextFromRaw(context, shaderRawId)
                return createShader(type, shaderText)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return -1
        }

        fun createShader(type: ShaderType, shaderText: String): Int {
            val shaderId = GLES20.glCreateShader(type.glesValue)

            if (shaderId == 0) {
                return -1
            }

            GLES20.glShaderSource(shaderId, shaderText)
            GLES20.glCompileShader(shaderId)

            val compileStatus = IntArray(1)

            GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compileStatus, 0)

            if (compileStatus[0] == 0) {
                val error = GLES20.glGetShaderInfoLog(shaderId)

                Log.d(TAG, error)

                GLES20.glDeleteShader(shaderId)

                return -1
            }

            return shaderId
        }
    }
}