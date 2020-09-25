package com.dima6120.oglHelper.utils

import com.dima6120.oglHelper.gobject.GObject2D

class GLConsts {
    companion object {
        val VERTICE_ATTRIBUTE = "a_Position"
        val TEXTURE_COORDINATE_ATTRIBUTE = "a_TexCoord"

        val RECT_INDICES = shortArrayOf(
            0, 1, 2,
            2, 3, 0
        )

        val RECT_ORIGIN_0_5_VERTICES = floatArrayOf(
            0f, 0f
            , 0f, 1f
            , 1f, 1f
            , 1f, 0f
        )

        val RECT_ORIGIN_0_VERTICES = floatArrayOf(
            -0.5f, -0.5f
            , -0.5f, 0.5f
            , 0.5f, 0.5f
            , 0.5f, -0.5f
        )

        val RECT_TEX_COORDS = floatArrayOf(
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
        )

        val RECT_0_5 = GObject2D(
            RECT_ORIGIN_0_5_VERTICES,
            RECT_INDICES,
            RECT_TEX_COORDS
        )
        val RECT_0 = GObject2D(
            RECT_ORIGIN_0_VERTICES,
            RECT_INDICES,
            RECT_TEX_COORDS
        )
    }
}