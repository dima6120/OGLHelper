package com.dima6120.demo

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLSurfaceView
import com.dima6120.demo.shaders.ShaderManager
import com.dima6120.demo.shaders.ShaderName
import com.dima6120.oglHelper.FBO
import com.dima6120.oglHelper.Shader
import com.dima6120.oglHelper.Texture
import com.dima6120.oglHelper.data.GLMatrix
import com.dima6120.oglHelper.data.Point2f
import com.dima6120.oglHelper.enums.TextureUnit
import com.dima6120.oglHelper.utils.GLConsts
import com.dima6120.oglHelper.utils.GLUtils
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.min

class Renderer(val context: Context) : GLSurfaceView.Renderer {
    private val shaderManager = ShaderManager()

    private lateinit var textureDrawShader: Shader
    private lateinit var grayscaleShader: Shader

    private var glViewWidth = 0
    private var glViewHeight = 0

    private var glViewProjM = GLMatrix.identity()
    private var glViewModelM = GLMatrix.identity()

    private var textureProjM = GLMatrix.identity()
    private val textureModelM = GLMatrix.identity()

    private val textureXY = Point2f()
    private var textureScale = 1f

    private var texture: Texture? = null
    private var fbo: FBO? = null

    fun setTexture(bitmap: Bitmap) {
        texture?.delete()
        texture = Texture(bitmap)

        updateMatrices()

        fbo?.delete()
        fbo = FBO(texture!!.width, texture!!.height)
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        GLUtils.setBlendEnabled(true)

        shaderManager.loadShaders(context)

        textureDrawShader = shaderManager.getShader(ShaderName.TEXTURE_DRAW)!!
        grayscaleShader = shaderManager.getShader(ShaderName.GRAYSCALE)!!
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        glViewWidth = width
        glViewHeight = height

        updateMatrices()
    }

    private fun updateMatrices() {
        if (glViewWidth <= 0 || glViewHeight <= 0) {
            return
        }

        texture?.let {
            val textureWidth = it.width.toFloat()
            val textureHeight = it.height.toFloat()

            glViewProjM = GLMatrix.orthoM(0f, glViewWidth.toFloat(), glViewHeight.toFloat(), 0f, 0f, 1f)

            textureScale = min(glViewWidth.toFloat() / textureWidth, glViewHeight.toFloat() / textureHeight)
            textureXY.set( (glViewWidth - textureWidth * textureScale) / 2f, (glViewHeight - textureHeight * textureScale) / 2f)

            glViewModelM.setIdentity()
            glViewModelM.translate(textureXY)
            glViewModelM.scale(textureWidth * textureScale, textureHeight * textureScale, 0f)

            textureProjM = GLMatrix.orthoM(0f, textureWidth, 0f, textureHeight, 0f, 1f)

            textureModelM.setIdentity()
            textureModelM.scale(textureWidth, textureHeight, 0f)
        }
    }

    private fun applyGrayscaleShader() {
        texture?.let { texture ->
            fbo?.let { fbo ->
                grayscaleShader.use()
                grayscaleShader.setMatrix("u_ProjM", textureProjM)
                grayscaleShader.setMatrix("u_ModelM", textureModelM)
                grayscaleShader.setF1("u_ColorFactor", 1f)
                grayscaleShader.setTexture("u_Texture", TextureUnit.UNIT0, texture)

                GLConsts.RECT_0_5.setShaderAttributes(grayscaleShader)
                fbo.clearRender(GLConsts.RECT_0_5)
            }
        }
    }

    private fun drawFBOTexture() {
        fbo?.getTexture()?.let { fboTexture ->
            textureDrawShader.use()
            textureDrawShader.setMatrix("u_ProjM", glViewProjM)
            textureDrawShader.setMatrix("u_ModelM", glViewModelM)
            textureDrawShader.setTexture("u_Texture", TextureUnit.UNIT0, fboTexture)
            textureDrawShader.setF1("u_Opacity", 1f)

            GLConsts.RECT_0_5.setShaderAttributes(textureDrawShader)
            GLConsts.RECT_0_5.render()
        }
    }

    override fun onDrawFrame(gl: GL10) {
        GLUtils.clearColor(0f, 0f, 0f, 0f)

        applyGrayscaleShader()
        drawFBOTexture()
    }
}