package com.dima6120.oglHelper.data

import androidx.core.graphics.ColorUtils
import com.dima6120.oglHelper.enums.HSLComponent
import kotlin.math.abs

class HSL {
    private val HUE_EPSILON = 0.001f
    private val SL_EPSILON = 0.00001f

    var h = 0f // hue = 0..360
    var s = 0f // saturation = 0..1
    var l = 0f // lightness = 0..1

    constructor()

    constructor(color: Int) {
        set(color)
    }

    constructor(rgb: RGB) {
        set(rgb)
    }

    constructor(hsl: HSL) {
        set(hsl)
    }

    constructor(h: Float, s: Float, l: Float) {
        set(h, s, l)
    }

    fun set(rgb: RGB) {
        set(rgb.toColor())
    }

    fun set(hsl: HSL) {
        set(hsl.h, hsl.s, hsl.l)
    }

    fun set(h: Float, s: Float, l: Float) {
        this.h = h
        this.s = s
        this.l = l
    }

    fun set(color: Int) {
        val hsl = FloatArray(3)
        ColorUtils.colorToHSL(color, hsl)

        set(hsl[0], hsl[1], hsl[2])
    }

    fun set(componentName: HSLComponent, value: Float) {
        when (componentName) {
            HSLComponent.HUE -> h = value
            HSLComponent.SATURATION -> s = value
            HSLComponent.LIGHTNESS -> l = value
        }
    }

    fun toRGB(): RGB {
        return RGB(this)
    }

    fun toColor(): Int {
        return ColorUtils.HSLToColor(floatArrayOf(h, s, l))
    }

    override fun equals(other: Any?): Boolean {
        return (other as? HSL)?.let {
            abs(it.h - h) < HUE_EPSILON && abs(it.s - s) < SL_EPSILON && abs(it.l - l) < SL_EPSILON
        } ?: false
    }

    override fun hashCode(): Int {
        var result = h.hashCode()
        result = 31 * result + s.hashCode()
        result = 31 * result + l.hashCode()
        return result
    }

}