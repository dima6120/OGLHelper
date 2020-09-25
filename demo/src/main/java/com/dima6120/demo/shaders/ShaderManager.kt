package com.dima6120.demo.shaders

import android.content.Context
import com.dima6120.oglHelper.Shader
import com.dima6120.oglHelper.ShaderManagerBase
import com.dima6120.oglHelper.enums.ShaderType

class ShaderManager : ShaderManagerBase() {
    override fun loadShaders(context: Context) {
        loadShadersFromAssets(context, ShaderType.FRAGMENT, "shaders/fs")
        loadShadersFromAssets(context, ShaderType.VERTEX, "shaders/vs")

        for (shaderName in ShaderName.values()) {
            addShader(
                shaderName.name,
                shaderName.vs,
                shaderName.fs,
                shaderName.attributes,
                shaderName.uniforms
            )
        }
    }

    fun getShader(shaderName: ShaderName) = getShader(shaderName.name)
}