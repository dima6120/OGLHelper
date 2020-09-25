package com.dima6120.oglHelper

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import com.dima6120.oglHelper.data.GLMatrix
import com.dima6120.oglHelper.enums.ShaderType
import com.dima6120.oglHelper.enums.TextureTarget
import com.dima6120.oglHelper.enums.TextureUnit
import com.dima6120.oglHelper.enums.Type
import com.dima6120.oglHelper.utils.ShaderUtils
import java.nio.Buffer
import java.nio.FloatBuffer

class Shader {
    private val TAG = Shader::class.java.simpleName

    private var programId = -1
    private var vsId = -1
    private var fsId = -1

    private val attributes = HashMap<String, Int>()
    private val uniforms = HashMap<String, Int>()

    constructor(context: Context, vsRawResId: Int, fsRawResId: Int, attributes: Array<String>, uniforms: Array<String>) {
        vsId = ShaderUtils.createShader(context, ShaderType.VERTEX, vsRawResId)

        if (vsRawResId == -1) {
            return
        }

        fsId = ShaderUtils.createShader(context, ShaderType.FRAGMENT, fsRawResId)

        if (fsRawResId == -1) {
            GLES20.glDeleteShader(vsId)
            return
        }

        programId = ShaderUtils.createProgram(vsId, fsId)

        if (programId == -1) {
            GLES20.glDeleteShader(vsId)
            GLES20.glDeleteShader(fsId)
            return
        }

        getAttributeIds(attributes)
        getUniformIds(uniforms)
    }

    constructor(vsId: Int, fsId: Int, attributes: Array<String>, uniforms: Array<String>) {
        this.vsId = vsId
        this.fsId = fsId

        if (vsId != -1 && fsId != -1) {
            programId = ShaderUtils.createProgram(vsId, fsId)

            if (programId != -1) {
                getAttributeIds(attributes)
                getUniformIds(uniforms)
            }
        }
    }

    private fun getAttributeIds(names: Array<String>) {
        for (name in names) {
            val id = GLES20.glGetAttribLocation(programId, name)

            if (id == -1) {
                Log.d(TAG, "Attribute '$name' not found")
            }

            attributes[name] = id
        }
    }

    private fun getUniformIds(names: Array<String>) {
        for (name in names) {
            val id = GLES20.glGetUniformLocation(programId, name)

            if (id == -1) {
                Log.d(TAG, "Uniform '$name' not found")
            }

            uniforms[name] = id
        }
    }

    fun isCompileError(): Boolean = programId == -1

    fun delete() {
        GLES20.glDeleteShader(vsId)
        GLES20.glDeleteShader(fsId)
        GLES20.glDeleteProgram(programId)
    }

    private fun getUniform(name: String) = uniforms[name] ?: -1

    private fun getAttribute(name: String) = attributes[name] ?: -1

    fun use() {
        GLES20.glUseProgram(programId)
    }

    fun setFloatAttrArray(attrName: String, buffer: FloatBuffer, size: Int) {
        setFloatAttrArray(attrName, buffer, size, false, 0)
    }

    fun setFloatAttrArray(attrName: String, buffer: FloatBuffer, size: Int, normalized: Boolean, stride: Int) {
        val attrId = getAttribute(attrName)

        GLES20.glEnableVertexAttribArray(attrId)
        GLES20.glVertexAttribPointer(attrId, size, Type.FLOAT.glesValue, normalized, stride, buffer)
    }

    fun setAttribArray(attrName: String, buffer: Buffer, size: Int, type: Type) {
        setAttribArray(attrName, buffer, size, type, false, 0)
    }

    fun setAttribArray(attrName: String, buffer: Buffer, size: Int, type: Type, normalized: Boolean, stride: Int) {
        val attrId = getAttribute(attrName)

        GLES20.glEnableVertexAttribArray(attrId)
        GLES20.glVertexAttribPointer(attrId, size, type.glesValue, normalized, stride, buffer)
    }

    fun setTexture(uniformName: String, textureUnit: TextureUnit, texture: Texture) {
        setTexture(TextureTarget.TEXTURE_2D, uniformName, textureUnit, texture.id)
    }

    fun setTexture(textureTarget: TextureTarget, uniformName: String, textureUnit: TextureUnit, textureId: Int) {
        GLES20.glActiveTexture(textureUnit.glesValue)
        GLES20.glBindTexture(textureTarget.glesValue, textureId)
        GLES20.glUniform1i(getUniform(uniformName), textureUnit.ordinal)
    }

    fun setMatrix(uniformName: String, matrix: GLMatrix) {
        setMatrix(uniformName, matrix.array())
    }

    fun setMatrix(uniformName: String, matrix: FloatArray) {
        GLES20.glUniformMatrix4fv(getUniform(uniformName), 1, false, matrix, 0)
    }

    /**
     * Векторы
     */
    fun setI1(uniformName: String, value: Int) {
        GLES20.glUniform1i(getUniform(uniformName), value)
    }

    fun setI2(uniformName: String, v1: Int, v2: Int) {
        GLES20.glUniform2i(getUniform(uniformName), v1, v2)
    }

    fun setI3(uniformName: String, v1: Int, v2: Int, v3: Int) {
        GLES20.glUniform3i(getUniform(uniformName), v1, v2, v3)
    }

    fun setI4(uniformName: String, v1: Int, v2: Int, v3: Int, v4: Int) {
        GLES20.glUniform4i(getUniform(uniformName), v1, v2, v3, v4)
    }

    fun setF1(uniformName: String, value: Float) {
        GLES20.glUniform1f(getUniform(uniformName), value)
    }

    fun setF2(uniformName: String, v1: Float, v2: Float) {
        GLES20.glUniform2f(getUniform(uniformName), v1, v2)
    }

    fun setF3(uniformName: String, v1: Float, v2: Float, v3: Float) {
        GLES20.glUniform3f(getUniform(uniformName), v1, v2, v3)
    }

    fun setF4(uniformName: String, v1: Float, v2: Float, v3: Float, v4: Float) {
        GLES20.glUniform4f(getUniform(uniformName), v1, v2, v3, v4)
    }

    /**
     * Массивы векторов
     */

    fun setI1V(uniformName: String, count: Int, array: IntArray) {
        GLES20.glUniform1iv(getUniform(uniformName), count, array, 0)
    }

    fun setI2V(uniformName: String, count: Int, array: IntArray) {
        GLES20.glUniform2iv(getUniform(uniformName), count, array, 0)
    }

    fun setI3V(uniformName: String, count: Int, array: IntArray) {
        GLES20.glUniform3iv(getUniform(uniformName), count, array, 0)
    }

    fun setI4V(uniformName: String, count: Int, array: IntArray) {
        GLES20.glUniform4iv(getUniform(uniformName), count, array, 0)
    }

    fun setF1V(uniformName: String, count: Int, array: FloatArray) {
        GLES20.glUniform1fv(getUniform(uniformName), count, array, 0)
    }

    fun setF2V(uniformName: String, count: Int, array: FloatArray) {
        GLES20.glUniform2fv(getUniform(uniformName), count, array, 0)
    }

    fun setF3V(uniformName: String, count: Int, array: FloatArray) {
        GLES20.glUniform3fv(getUniform(uniformName), count, array, 0)
    }

    fun setF4V(uniformName: String, count: Int, array: FloatArray) {
        GLES20.glUniform4fv(getUniform(uniformName), count, array, 0)
    }
}