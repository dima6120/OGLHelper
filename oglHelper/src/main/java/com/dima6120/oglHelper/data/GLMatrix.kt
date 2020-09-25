package com.dima6120.oglHelper.data

import android.opengl.Matrix

class GLMatrix {
    companion object {
        fun identity(): GLMatrix {
            val matrix = GLMatrix()
            Matrix.setIdentityM(matrix.m, 0)
            return matrix
        }

        fun orthoM(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): GLMatrix {
            val matrix = GLMatrix()
            Matrix.orthoM(matrix.m, 0, left, right, bottom, top, near, far)
            return matrix
        }

        fun perspectiveM(fovy: Float, aspect: Float, zNear: Float, zFar: Float): GLMatrix {
            val matrix = GLMatrix()
            Matrix.perspectiveM(matrix.m, 0, fovy, aspect, zNear, zFar)
            return matrix
        }

        fun frustumM(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): GLMatrix {
            val matrix = GLMatrix()
            Matrix.frustumM(matrix.m, 0, left, right, bottom, top, near, far)
            return matrix
        }

        fun lookAtM(eyeX: Float, eyeY: Float, eyeZ: Float, centerX: Float, centerY: Float
                    , centerZ: Float, upX: Float, upY: Float, upZ: Float): GLMatrix {
            val matrix = GLMatrix()
            Matrix.setLookAtM(matrix.m, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ)
            return matrix
        }

        fun rotateM(a: Float, x: Float, y: Float, z: Float): GLMatrix {
            val matrix = identity()
            Matrix.rotateM(matrix.m, 0, a, x, y, z)
            return matrix
        }

        fun translateM(x: Float, y: Float, z: Float): GLMatrix {
            val matrix = identity()
            Matrix.translateM(matrix.m, 0, x, y, z)
            return matrix
        }

        fun scaleM(x: Float, y: Float, z: Float): GLMatrix {
            val matrix = identity()
            Matrix.scaleM(matrix.m, 0, x, y, z)
            return matrix
        }
    }

    private val m = FloatArray(16)

    fun array(): FloatArray = m

    fun setIdentity() {
        Matrix.setIdentityM(m, 0)
    }

    fun translate(x: Float, y: Float, z: Float) {
        Matrix.translateM(m, 0, x, y, z)
    }

    fun translate(p: Point2f) {
        Matrix.translateM(m, 0, p.x, p.y, 0f)
    }

    fun translate(p: Point3f) {
        Matrix.translateM(m, 0, p.x, p.y, p.x)
    }

    fun scale(x: Float, y: Float, z: Float) {
        Matrix.scaleM(m, 0, x, y, z)
    }

    fun rotate(a: Float, x: Float, y: Float, z: Float) {
        Matrix.rotateM(m, 0, a, x, y, z)
    }

    fun setLookAtM(eyeX: Float, eyeY: Float, eyeZ: Float, centerX: Float, centerY: Float
                   , centerZ: Float, upX: Float, upY: Float, upZ: Float) {
        Matrix.setLookAtM(m, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ)
    }

    fun setRotateXM(a: Float) {
        Matrix.setRotateM(m, 0, a, 1f, 0f, 0f)
    }

    fun setRotateYM(a: Float) {
        Matrix.setRotateM(m, 0, a, 0f, 1f, 0f)
    }

    fun setRotateZM(a: Float) {
        Matrix.setRotateM(m, 0, a, 0f, 0f, 1f)
    }

    fun multiplyV(v: Point4f): Point4f {
        val p = floatArrayOf(v.x, v.y, v.z, v.w)
        val res = FloatArray(4)
        Matrix.multiplyMV(res, 0, m, 0, p, 0)
        return Point4f(res[0], res[1], res[2], res[3])
    }

    fun multiplyV2(v: Point2f) {
        val p = floatArrayOf(v.x, v.y, 0f, 1f)
        val res = FloatArray(4)
        Matrix.multiplyMV(res, 0, m, 0, p, 0)
        v.set(res[0], res[1])
    }
}