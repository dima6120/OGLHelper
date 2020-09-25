package com.dima6120.oglHelper

import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLUtils
import com.dima6120.oglHelper.enums.MagFilter
import com.dima6120.oglHelper.enums.MinFilter
import com.dima6120.oglHelper.enums.PixelFormat
import com.dima6120.oglHelper.enums.TextureWrap
import java.nio.Buffer

class Texture {
    var usageCount = 1
        private set

    var id = 1
        private set

    var width = -1
        private set
    var height = -1
        private set

    var pixelFormat = PixelFormat.RGBA
        private set

    var minFilter = MinFilter.LINEAR
        private set
    var magFilter = MagFilter.LINEAR
        private set

    var wrapS = TextureWrap.CLAMP_TO_EDGE
        private set
    var wrapT = TextureWrap.CLAMP_TO_EDGE
        private set

    constructor(bitmap: Bitmap) : this(bitmap, PixelFormat.RGBA)

    constructor(bitmap: Bitmap, pixelFormat: PixelFormat) {
        val temp = IntArray(1)

        GLES20.glGenTextures(1, temp, 0)

        id = temp[0]
        this.pixelFormat = pixelFormat
        width = bitmap.width
        height = bitmap.height

        initFromBitmap(bitmap, pixelFormat)
    }

    constructor(width: Int, height: Int, pixelFormat: PixelFormat) {
        val temp = IntArray(1)

        GLES20.glGenTextures(1, temp, 0)

        id = temp[0]
        this.pixelFormat = pixelFormat
        this.width = width
        this.height = height

        init()
    }

    constructor(id: Int, width: Int, height: Int, pixelFormat: PixelFormat) {
        this.id = id
        this.pixelFormat = pixelFormat
        this.width = width
        this.height = height

        init()
    }

    constructor(width: Int, height: Int) : this(width, height, PixelFormat.RGBA)

    constructor(id: Int, width: Int, height: Int) : this(id, width, height, PixelFormat.RGBA)

    private fun init() {
        bind()

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, pixelFormat.iternalFormat, width
            , height, 0, pixelFormat.format, pixelFormat.type.glesValue, null)

        setTexParameters()

        unbind()
    }

    private fun initFromBitmap(bitmap: Bitmap, pixelFormat: PixelFormat) {
        bind()

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, pixelFormat.format, bitmap, 0)

        setTexParameters()

        unbind()
    }

    private fun setTexParameters() {
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, wrapS.glesValue)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, wrapT.glesValue)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, magFilter.glesValue)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, minFilter.glesValue)
    }

    fun use() {
        usageCount++
    }

    fun bind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, id)
    }

    fun unbind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
    }

    fun update(data: Buffer) {
        GLES20.glTexSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, width, height
            , pixelFormat.format, pixelFormat.type.glesValue, data)
    }

    fun update(x: Int, y: Int, w: Int, h: Int, data: Buffer) {
        GLES20.glTexSubImage2D(GLES20.GL_TEXTURE_2D, 0, x, y, w, h, pixelFormat.format, pixelFormat.type.glesValue, data);
    }

    fun delete() {
        usageCount--

        if (id != -1 && usageCount == 0) {
            GLES20.glDeleteTextures(1, intArrayOf(id), 0)
            id = -1
            width = -1
            height = -1
        }
    }

    fun setMinFilter(filter: MinFilter) {
        minFilter = filter

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, minFilter.glesValue)
    }

    fun setMagFilter(filter: MagFilter) {
        magFilter = filter

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, magFilter.glesValue)
    }

    fun setWrapS(textureWrap: TextureWrap) {
        wrapS = textureWrap

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, wrapS.glesValue)
    }

    fun setWrapT(textureWrap: TextureWrap) {
        wrapT = textureWrap

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, wrapT.glesValue)
    }
}