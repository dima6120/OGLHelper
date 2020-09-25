package com.dima6120.oglHelper

import android.opengl.EGL14
import android.opengl.GLSurfaceView

class GLThreadHelper(val glView: GLSurfaceView) {
    fun run(task: (() -> Unit)) {
        if (isOGLContext()) {
            task.invoke()
        } else {
            glView.queueEvent(task)
        }
    }

    fun runAndRender(task: (() -> Unit)) {
        run(task)
        requestRender()
    }

    fun setRenderModeCountinuously() {
        glView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }

    fun setRenderModeWhenDirty() {
        glView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    fun requestRender() {
        glView.requestRender()
    }

    fun isOGLContext(): Boolean {
        return EGL14.eglGetCurrentContext() != EGL14.EGL_NO_CONTEXT
    }
}