package com.dima6120.oglHelper.gobject

import com.dima6120.oglHelper.Shader
import com.dima6120.oglHelper.interfaces.RenderRoutine

abstract class GObject : RenderRoutine {
    abstract fun setShaderAttributes(shader: Shader)
}