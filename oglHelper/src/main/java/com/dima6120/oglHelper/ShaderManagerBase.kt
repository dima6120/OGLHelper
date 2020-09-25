package com.dima6120.oglHelper

import android.content.Context
import android.util.Log
import com.dima6120.oglHelper.enums.ShaderType
import com.dima6120.oglHelper.utils.FileUtils
import com.dima6120.oglHelper.utils.ShaderUtils
import java.io.IOException

abstract class ShaderManagerBase {
    private val TAG: String = ShaderManagerBase::class.java.getSimpleName()

    protected val shaders = HashMap<String, Shader>()
    protected var fsShaderCache = HashMap<String, Int>()
    protected var vsShaderCache = HashMap<String, Int>()

    protected fun loadShadersFromAssets(context: Context, shaderType: ShaderType, path: String) {
        try {
            context.assets.list(path)?.let { vsShaders ->
                for (i in vsShaders.indices) {
                    val name = vsShaders[i]

                    Log.d(TAG, name)

                    try {
                        val shaderText = FileUtils.readTextFromAssets(context, "$path/$name")
                        val id = ShaderUtils.createShader(shaderType, shaderText)

                        addShaderToCache(shaderType, name.replace(".glsl", ""), id)
                    } catch (e: IOException) {
                        // папка
                        loadShadersFromAssets(context, shaderType, "$path/$name")
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun addShaderToCache(shaderType: ShaderType, name: String, id: Int) {
        when (shaderType) {
            ShaderType.VERTEX -> vsShaderCache[name] = id
            ShaderType.FRAGMENT -> fsShaderCache[name] = id
        }
    }

    protected open fun loadShadersFromRaw(context: Context, shaderType: ShaderType, shaderListFileRawRes: Int) {
        val res = context.resources

        try {
            val inputStream = res.openRawResource(shaderListFileRawRes)

            inputStream.bufferedReader().forEachLine { name ->
                if (name.isNotEmpty()) {
                    Log.d(TAG, name)

                    try {
                        val fileId = res.getIdentifier(name, "raw", context.packageName)
                        val shaderText = FileUtils.readTextFromRaw(context, fileId)
                        val id = ShaderUtils.createShader(shaderType, shaderText)

                        addShaderToCache(shaderType, name, id)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun getFragmentShader(name: String) = fsShaderCache[name] ?: -1

    protected fun getVertexShader(name: String) = vsShaderCache[name] ?: -1

    protected fun addShader(name: String, vsShaderName: String, fsShaderName: String, attributes: Array<String>, uniforms: Array<String>) {
        val shader = Shader(getVertexShader(vsShaderName), getFragmentShader(fsShaderName), attributes, uniforms)

        shaders[name] = shader
    }

    fun getShader(name: String) = shaders[name]

    abstract fun loadShaders(context: Context)
}