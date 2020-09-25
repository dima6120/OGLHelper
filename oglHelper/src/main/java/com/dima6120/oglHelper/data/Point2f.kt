package com.dima6120.oglHelper.data

import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

class Point2f {
    var x = 0f
    var y = 0f

    constructor()

    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    constructor(p: Point2f) {
        x = p.x
        y = p.y
    }

    fun set(p: Point2f): Point2f {
        x = p.x
        y = p.y

        return this
    }

    fun set(x: Float, y: Float): Point2f {
        this.x = x
        this.y = y

        return this
    }

    fun inRadius(r: Float, p: Point2f): Boolean {
        return if (r > 0) {
            val d = sqrt((x - p.x.toDouble()).pow(2.0) + (y - p.y.toDouble()).pow(2.0)).toFloat()
            d <= r
        } else {
            false
        }
    }

    fun angle(): Float {
        var angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble())).toFloat() % 360

        if (angle < -180f) {
            angle += 360.0f
        }

        if (angle > 180f) {
            angle -= 360.0f
        }

        return angle
    }

    fun angle(p: Point2f): Float {
        val angle1 = atan2(y.toDouble(), x.toDouble()).toFloat()
        val angle2 = atan2(p.y.toDouble(), p.x.toDouble()).toFloat()
        var angle = Math.toDegrees(angle1 - angle2.toDouble()).toFloat() % 360

        if (angle < -180f) {
            angle += 360.0f
        }

        if (angle > 180f) {
            angle -= 360.0f
        }

        return angle
    }

    fun length(): Float = sqrt(x.toDouble().pow(2.0) + y.toDouble().pow(2.0)).toFloat()

    fun distance(p: Point2f): Float = sqrt((p.x - x.toDouble()).pow(2.0) + (p.y - y.toDouble()).pow(2.0)).toFloat()

    fun distance(px: Float, py: Float): Float = sqrt((px - x.toDouble()).pow(2.0) + (py - y.toDouble()).pow(2.0)).toFloat()

    fun dot(p: Point2f): Float = x * p.x + y * p.y

    fun add(v: Float): Point2f = set(x + v, y + v)

    fun add(vx: Float, vy: Float): Point2f = set(x + vx, y + vy)

    fun add(p: Point2f): Point2f = set(x + p.x, y + p.y)

    fun added(p: Point2f): Point2f = Point2f(x + p.x, y + p.y)

    fun added(vx: Float, vy: Float): Point2f = Point2f(x + vx, y + vy)

    fun sub(p: Point2f): Point2f = set(x - p.x, y - p.y)

    fun sub(vx: Float, vy: Float): Point2f = set(x - vx, y - vy)

    fun subed(p: Point2f): Point2f = Point2f(x - p.x, y - p.y)

    fun subed(vx: Float, vy: Float): Point2f = Point2f(x - vx, y - vy)

    fun mult(v: Float): Point2f = set(x * v, y * v)

    fun mult(p: Point2f): Point2f = set(x * p.x, y * p.y)

    fun mult(vx: Float, vy: Float): Point2f = set(x * vx, y * vy)

    fun div(v: Float): Point2f = set(x / v, y / v)

    fun div(p: Point2f): Point2f = set(x / p.x, y / p.y)

    fun div(vx: Float, vy: Float): Point2f = set(x / vx, y / vy)

    fun multed(v: Float): Point2f = Point2f(x * v, y * v)

    fun multed(p: Point2f): Point2f = Point2f(x * p.x, y * p.y)

    fun dived(v: Float): Point2f = Point2f(x / v, y / v)

    fun dived(p: Point2f): Point2f = Point2f(x / p.x, y / p.y)

    fun normalize(): Point2f {
        val len = sqrt(x * x + y * y.toDouble()).toFloat()

        if (len > 0) {
            x /= len
            y /= len
        }

        return this
    }

    fun normalized(): Point2f {
        val len = sqrt(x * x + y * y)
        var newX = x
        var newY = y

        if (len > 0) {
            newX = x / len
            newY = y / len
        }

        return Point2f(newX, newY)
    }

    override fun toString(): String = "$x, $y"
}