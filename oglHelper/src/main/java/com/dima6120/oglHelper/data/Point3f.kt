package com.dima6120.oglHelper.data

class Point3f {
    var x = 0f
    var y = 0f
    var z = 0f

    constructor()

    constructor(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
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

    fun set(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    fun set(p: Point3f) {
        x = p.x
        y = p.y
        z = p.z
    }

    fun set(p: Point2f) {
        x = p.x
        y = p.y
        z = 0f
    }
}