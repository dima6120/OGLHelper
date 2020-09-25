package com.dima6120.oglHelper.gobject

import android.opengl.GLES20
import com.dima6120.oglHelper.Shader
import com.dima6120.oglHelper.enums.DrawMode
import com.dima6120.oglHelper.enums.Type
import com.dima6120.oglHelper.utils.GLConsts
import com.dima6120.oglHelper.utils.Utils
import java.nio.FloatBuffer
import java.nio.ShortBuffer

class GObject2D : GObject {
    private var vertices: FloatBuffer? = null
    private var texCoords : FloatBuffer? = null
    private var indices : ShortBuffer? = null
    private var drawMode = DrawMode.TRIANGLES

    constructor() {}

    constructor(vertices: FloatArray, indices: ShortArray) {
        this.vertices = Utils.createFloatBuffer(vertices)
        this.indices = Utils.createShortBuffer(indices)
    }

    constructor(vertices: FloatArray, indices: ShortArray, drawMode: DrawMode) : this(vertices, indices) {
        this.drawMode = drawMode
    }

    constructor(vertices: FloatArray, indices: ShortArray, texCoords: FloatArray) : this(vertices, indices) {
        this.texCoords = Utils.createFloatBuffer(texCoords)
    }

    constructor(vertices: FloatArray, indices: ShortArray, texCoords: FloatArray, drawMode: DrawMode) : this(vertices, indices, texCoords) {
        this.drawMode = drawMode
    }

    constructor(vertices: FloatBuffer, indices: ShortBuffer) {
        this.vertices = vertices
        this.indices = indices
    }

    constructor(vertices: FloatBuffer, indices: ShortBuffer, drawMode: DrawMode) : this(vertices, indices) {
        this.drawMode = drawMode
    }

    constructor(vertices: FloatBuffer, indices: ShortBuffer, texCoords: FloatBuffer) : this(vertices, indices) {
        this.texCoords = texCoords
    }

    constructor(vertices: FloatBuffer, indices: ShortBuffer, texCoords: FloatBuffer, drawMode: DrawMode) : this(vertices, indices, texCoords) {
        this.drawMode = drawMode
    }

    fun setVertices(vertices: FloatArray) {
        this.vertices = Utils.createFloatBuffer(vertices)
    }

    fun setVertices(vertices: FloatBuffer) {
        this.vertices = vertices
    }

    fun setIndices(indices: ShortArray) {
        this.indices = Utils.createShortBuffer(indices)
    }

    fun setIndices(indices: ShortBuffer) {
        this.indices = indices
    }

    fun setTexCoords(texCoords: FloatArray) {
        this.texCoords = Utils.createFloatBuffer(texCoords)
    }

    fun setTexCoords(texCoords: FloatBuffer?) {
        this.texCoords = texCoords
    }

    override fun setShaderAttributes(shader: Shader) {
        vertices?.let {
            shader.setFloatAttrArray(GLConsts.VERTICE_ATTRIBUTE, it, 2)
        }

        texCoords?.let {
            shader.setFloatAttrArray(GLConsts.TEXTURE_COORDINATE_ATTRIBUTE, it, 2)
        }
    }

    override fun render() {
        indices?.let {
            GLES20.glDrawElements(drawMode.glesValue, it.limit(), Type.UNSIGNED_SHORT.glesValue, it)
        }
    }
}