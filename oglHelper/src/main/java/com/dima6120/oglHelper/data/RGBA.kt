package com.dima6120.oglHelper.data

import android.graphics.Color
import androidx.core.graphics.ColorUtils

class RGBA {
    private val EPSILON = 0.00001f

    var r = 0f // red = 0..1
    var g = 0f // green = 0..1
    var b = 0f // blue = 0..1
    var a = 0f // alpha = 0..1

    constructor()

    constructor(r: Float, g: Float, b: Float, a: Float) {
        set(r, g, b, a)
    }

    constructor(rgba: RGBA) {
        set(rgba)
    }

    constructor(rgb: RGB) {
        set(rgb)
    }

    constructor(color: Int) {
        set(color)
    }

    constructor(hsl: HSL) {
        set(hsl)
    }

    fun set(r: Float, g: Float, b: Float, a: Float) {
        this.r = r
        this.g = g
        this.b = b
        this.a = a
    }

    fun set(rgb: RGB) {
        set(rgb.r, rgb.g, rgb.b, 1f)
    }

    fun set(hsl: HSL) {
        set(ColorUtils.HSLToColor(floatArrayOf(hsl.h, hsl.s, hsl.l)))
    }

    fun set(rgba: RGBA) {
        set(rgba.r, rgba.g, rgba.b, rgba.a)
    }

    fun set(color: Int) {
        set(Color.red(color) / 255f, Color.green(color) / 255f, Color.blue(color) / 255f, Color.alpha(color) / 255f)
    }
}