package com.dima6120.oglHelper.data

class Point4f {
    var x = 0f
    var y = 0f
    var z = 0f
    var w = 1f

    constructor()

    constructor(x: Float, y: Float, z: Float, w: Float) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    constructor(p: Point4f) {
        x = p.x
        y = p.y
        z = p.z
        w = p.w
    }

    constructor(p: Point3f) {
        x = p.x
        y = p.y
        z = p.z
    }

    constructor(p: Point2f) {
        x = p.x
        y = p.y
    }

    fun set(x: Float, y: Float, z: Float, w: Float) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    fun set(p: Point3f) {
        x = p.x
        y = p.y
        z = p.z
        w = 1f
    }

    fun set(p: Point2f) {
        x = p.x
        y = p.y
        z = 0f
        w = 1f
    }
}