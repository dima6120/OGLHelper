package com.dima6120.oglHelper

import android.graphics.Bitmap
import android.graphics.Color
import android.opengl.GLES20
import android.opengl.GLES30
import com.dima6120.oglHelper.data.Point2f
import com.dima6120.oglHelper.data.RGBA
import com.dima6120.oglHelper.enums.ColorAttachment
import com.dima6120.oglHelper.enums.PixelFormat
import com.dima6120.oglHelper.interfaces.RenderRoutine
import com.dima6120.oglHelper.utils.GLUtils
import java.nio.IntBuffer
import kotlin.math.roundToInt

class FBO {
    private var fboId = -1

    private var msFBOId = -1
    private var msRBId = -1

    private lateinit var textures: Array<Texture?>
    private lateinit var attachments: IntArray

    private val oldVP = IntArray(4)
    private val oldFBO = IntArray(1)

    var pixelFormat = PixelFormat.RGBA
        private set
    var fboWidth = -1
        private set
    var fboHeight = -1
        private set
    var msaa = false
        private set

    constructor() {
        val temp = IntArray(1)
        GLES20.glGenFramebuffers(1, temp, 0)

        fboId = temp[0]

        textures = arrayOfNulls(1)
        attachments = IntArray(1)
        attachments[0] = ColorAttachment.ATTACHMENT0.glesValue
    }

    constructor(width: Int, height: Int) {
        initOneTextureFBO(width, height, PixelFormat.RGBA, false)
    }

    constructor(width: Int, height: Int, pixelFormat: PixelFormat, msaa: Boolean) {
        initOneTextureFBO(width, height, pixelFormat, msaa)
    }

    constructor(width: Int, height: Int, msaa: Boolean) {
        initOneTextureFBO(width, height, PixelFormat.RGBA, msaa)
    }

    constructor(width: Int, height: Int, pixelFormat: PixelFormat, textureCount: Int) {
        if (textureCount == 1) {
            initOneTextureFBO(width, height, pixelFormat, false)
            return
        }

        fboWidth = width
        fboHeight = height
        this.pixelFormat = pixelFormat

        storeOld()

        val temp = IntArray(1)

        GLES20.glGenFramebuffers(1, temp, 0)
        fboId = temp[0]

        textures = arrayOfNulls(textureCount)
        attachments = IntArray(textureCount)

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId)

        for (i in 0 until textureCount) {
            addColorAttachment(i)
        }

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)

        restoreOld()
    }

    private fun initOneTextureFBO(width: Int, height: Int, pixelFormat: PixelFormat, msaa: Boolean) {
        fboWidth = width
        fboHeight = height
        this.pixelFormat = pixelFormat
        this.msaa = msaa

        storeOld()

        val temp = IntArray(1)

        GLES20.glGenFramebuffers(1, temp, 0)
        fboId = temp[0]

        textures = arrayOfNulls(1)
        attachments = IntArray(1)

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId)

        addColorAttachment(0)

        if (this.msaa) {
            initMultiFBO()
        }

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)

        restoreOld()
    }

    private fun initMultiFBO() {
        val temp = IntArray(1)

        GLES20.glGenRenderbuffers(1, temp, 0)
        msRBId = temp[0]

        GLES20.glGenFramebuffers(1, temp, 0)
        msFBOId = temp[0]

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, msFBOId)
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, msRBId)
        GLES30.glRenderbufferStorageMultisample(
            GLES20.GL_RENDERBUFFER,
            4,
            pixelFormat.iternalFormat,
            fboWidth,
            fboHeight
        )
        GLES20.glFramebufferRenderbuffer(
            GLES20.GL_FRAMEBUFFER,
            GLES20.GL_COLOR_ATTACHMENT0,
            GLES20.GL_RENDERBUFFER,
            msRBId
        )
    }

    private fun addColorAttachment(attachmentNumber: Int) {
        val texture = Texture(fboWidth, fboHeight, pixelFormat)

        textures[attachmentNumber] = texture

        texture.bind()

        val colorAttachment= ColorAttachment.values()[attachmentNumber].glesValue

        attachments[attachmentNumber] = colorAttachment

        GLES20.glFramebufferTexture2D(
            GLES20.GL_FRAMEBUFFER,
            colorAttachment,
            GLES20.GL_TEXTURE_2D,
            texture.id,
            0
        )
    }

    private fun storeOld() {
        GLES20.glGetIntegerv(GLES20.GL_FRAMEBUFFER_BINDING, oldFBO, 0)
        GLES20.glGetIntegerv(GLES20.GL_VIEWPORT, oldVP, 0)
    }

    private fun restoreOld() {
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, oldFBO[0])
        GLES20.glViewport(oldVP[0], oldVP[1], oldVP[2], oldVP[3])
    }

    fun getTexture(): Texture? {
        return textures[0]
    }

    fun getTexture(number: Int): Texture? {
        return if (number in textures.indices) textures[number] else null
    }

    fun getTextureCount() = textures.size

    fun fillColor(rgba: RGBA) {
        fillColor(rgba.r, rgba.g, rgba.b, rgba.a)
    }

    fun fillColor(r: Float, g: Float, b: Float, a: Float) {
        if (!(fboWidth > 0 && fboHeight > 0)) {
            return
        }

        storeOld()

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId)

        GLES20.glViewport(0, 0, fboWidth, fboHeight)

        GLES30.glDrawBuffers(attachments.size, attachments, 0)

        GLUtils.clearColor(r, g, b, a)

        restoreOld()
    }

    fun attachTexture(texture: Texture) {
        storeOld()

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId)

        textures[0] = texture
        pixelFormat = texture.pixelFormat
        fboWidth = texture.width
        fboHeight = texture.height

        texture.bind()

        GLES20.glFramebufferTexture2D(
            GLES20.GL_FRAMEBUFFER,
            attachments[0],
            GLES20.GL_TEXTURE_2D,
            texture.id,
            0
        )

        texture.unbind()

        restoreOld()
    }

    fun detachTexture() {
        textures[0] = null

        storeOld()

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId)

        GLES20.glFramebufferTexture2D(
            GLES20.GL_FRAMEBUFFER,
            attachments[0],
            GLES20.GL_TEXTURE_2D,
            0,
            0
        )

        restoreOld()
    }

    fun delete() {
        if (fboId != -1) {
            GLES20.glDeleteFramebuffers(1, intArrayOf(fboId), 0)
            fboId = -1
        }

        for (texture in textures) {
            texture?.delete()
        }

        if (msaa) {
            GLES20.glDeleteFramebuffers(1, intArrayOf(msFBOId), 0)
            GLES20.glDeleteRenderbuffers(1, intArrayOf(msRBId), 0)

            msFBOId = -1
            msRBId = -1
        }
    }

    fun deleteWithoutTexture(): Texture? {
        if (fboId != -1) {
            GLES20.glDeleteFramebuffers(1, intArrayOf(fboId), 0)
            fboId = -1
        }

        if (msaa) {
            GLES20.glDeleteFramebuffers(1, intArrayOf(msFBOId), 0)
            GLES20.glDeleteRenderbuffers(1, intArrayOf(msRBId), 0)
            msFBOId = -1
            msRBId = -1
        }

        if (textures.size > 1) {
            for (i in textures.indices) {
                if (i != 0) {
                    textures[i]?.delete()
                }
            }
        }

        return textures[0]
    }

    fun copyFrom(fbo: FBO) {
        copyFrom(fbo, 0, 0, fboWidth, fboHeight)
    }

    fun copyFrom(fbo: FBO, left: Int, top: Int, right: Int, bottom: Int) {
        val oldFBO = IntArray(1)
        GLES20.glGetIntegerv(GLES20.GL_FRAMEBUFFER_BINDING, oldFBO, 0)
        GLES30.glBindFramebuffer(GLES30.GL_READ_FRAMEBUFFER, fbo.fboId)
        GLES30.glBindFramebuffer(GLES30.GL_DRAW_FRAMEBUFFER, fboId)
        GLES30.glBlitFramebuffer(
            left,
            top,
            right,
            bottom,
            0,
            0,
            fboWidth,
            fboHeight,
            GLES20.GL_COLOR_BUFFER_BIT,
            GLES20.GL_NEAREST
        )
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, oldFBO[0])
    }

    fun render(routine: RenderRoutine) {
        storeOld()
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId)
        GLES20.glViewport(0, 0, fboWidth, fboHeight)
        GLES30.glDrawBuffers(attachments.size, attachments, 0)
        routine.render()
        restoreOld()
    }

    fun clearRender(routine: RenderRoutine) {
        storeOld()

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId)
        GLES20.glViewport(0, 0, fboWidth, fboHeight)
        GLES30.glDrawBuffers(attachments.size, attachments, 0)

        GLUtils.clearColor(0f, 0f, 0f, 0f)
        routine.render()

        restoreOld()
    }

    fun renderMS(routine: RenderRoutine) {
        storeOld()
        GLES30.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, msFBOId)
        GLES20.glViewport(0, 0, fboWidth, fboHeight)
        routine.render()
        GLES30.glBindFramebuffer(GLES30.GL_READ_FRAMEBUFFER, msFBOId)
        GLES30.glBindFramebuffer(GLES30.GL_DRAW_FRAMEBUFFER, fboId)
        GLES30.glBlitFramebuffer(
            0,
            0,
            fboWidth,
            fboHeight,
            0,
            0,
            fboWidth,
            fboHeight,
            GLES20.GL_COLOR_BUFFER_BIT,
            GLES20.GL_NEAREST
        )
        restoreOld()
    }

    fun clearRenderMS(routine: RenderRoutine) {
        storeOld()
        GLES30.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, msFBOId)
        GLES20.glViewport(0, 0, fboWidth, fboHeight)

        GLUtils.clearColor(0f, 0f, 0f, 0f)
        routine.render()

        GLES30.glBindFramebuffer(GLES30.GL_READ_FRAMEBUFFER, msFBOId)
        GLES30.glBindFramebuffer(GLES30.GL_DRAW_FRAMEBUFFER, fboId)
        GLES30.glBlitFramebuffer(
            0,
            0,
            fboWidth,
            fboHeight,
            0,
            0,
            fboWidth,
            fboHeight,
            GLES20.GL_COLOR_BUFFER_BIT,
            GLES20.GL_NEAREST
        )
        restoreOld()
    }

    private fun savePixels(x: Int, y: Int, w: Int, h: Int): Bitmap? {
        val b = IntArray(w * h)
        val bt = IntArray(w * h)
        val ib = IntBuffer.wrap(b)
        ib.position(0)
        GLES20.glReadPixels(x, y, w, h, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib)
        var i = 0
        var k = 0
        while (i < h) {
            //remember, that OpenGL bitmap is incompatible with Android bitmap
            //and so, some correction need.
            for (j in 0 until w) {
                val pix = b[i * w + j]
                val pb = pix shr 16 and 0xff
                val pr = pix shl 16 and 0x00ff0000
                val pix1 = pix and -0xff0100 or pr or pb
                bt[k * w + j] = pix1
            }
            i++
            k++
        }
        return Bitmap.createBitmap(bt, w, h, Bitmap.Config.ARGB_8888)
    }

    fun toBitmap(): Bitmap {
        return toBitmap(0, 0, fboWidth, fboHeight)
    }

    fun toBitmap(x: Int, y: Int, w: Int, h: Int): Bitmap {
        val bitmap = arrayOfNulls<Bitmap>(1)

        render(object : RenderRoutine {
            override fun render() {
                bitmap[0] = savePixels(x, y, w, h)
            }
        })

        return bitmap[0]!!
    }

    fun getColor(point: Point2f): Int {
        val color = IntArray(1)
        render(object : RenderRoutine {
            override fun render() {
                val ib = IntBuffer.wrap(color)

                ib.position(0)

                GLES20.glReadPixels(
                    point.x.roundToInt(),
                    point.y.roundToInt(),
                    1,
                    1,
                    GLES20.GL_RGBA,
                    GLES20.GL_UNSIGNED_BYTE,
                    ib
                )

                val pix = color[0]
                val pb = pix shr 16 and 0xff
                val pr = pix shl 16 and 0x00ff0000

                color[0] = pix and -0xff0100 or pr or pb

                if (color[0] == Color.TRANSPARENT) {
                    color[0] = Color.BLACK
                }
            }
        })

        return color[0]
    }
}