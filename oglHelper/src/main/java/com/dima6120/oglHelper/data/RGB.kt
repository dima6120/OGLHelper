package com.dima6120.oglHelper.data

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import kotlin.math.abs

class RGB {
    private val EPSILON = 0.00001f

    var r = 0f // red = 0..1
    var g = 0f // green = 0..1
    var b = 0f // blue = 0..1

    constructor()

    constructor(r: Float, g: Float, b: Float) {
        set(r, g, b)
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

    fun set(r: Float, g: Float, b: Float) {
        this.r = r
        this.g = g
        this.b = b
    }

    fun set(color: Int) {
        set(Color.red(color) / 255f, Color.green(color) / 255f, Color.blue(color) / 255f)
    }

    fun set(rgb: RGB) {
        set(rgb.r, rgb.g, rgb.b)
    }

    fun set(hsl: HSL) {
        set(ColorUtils.HSLToColor(floatArrayOf(hsl.h, hsl.s, hsl.l)))
    }

    fun toHSL(): HSL {
        val hsl = FloatArray(3)
        ColorUtils.RGBToHSL((255f * r).toInt(), (255f * g).toInt(), (255f * b).toInt(), hsl)
        return HSL(hsl[0], hsl[1], hsl[2])
    }

    fun toColor(): Int {
        return Color.rgb((255f * r).toInt(), (255f * g).toInt(), (255f * b).toInt())
    }

    override fun equals(other: Any?): Boolean {
        return (other as? RGB)?.let {
            abs(it.r - r) < EPSILON && abs(it.g - g) < EPSILON && abs(it.b - b) < EPSILON
        } ?: false
    }

    override fun hashCode(): Int {
        var result = r.hashCode()
        result = 31 * result + g.hashCode()
        result = 31 * result + b.hashCode()
        return result
    }
}