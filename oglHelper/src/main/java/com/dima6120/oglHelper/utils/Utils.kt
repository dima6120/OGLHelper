package com.dima6120.oglHelper.utils

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

class Utils {
    companion object {
        fun createFloatBuffer(data: FloatArray): FloatBuffer {
            val buffer = ByteBuffer.allocateDirect(data.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()

            buffer.put(data)
            buffer.position(0)

            return buffer
        }

        fun createShortBuffer(data: ShortArray): ShortBuffer {
            val buffer = ByteBuffer.allocateDirect(data.size * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()

            buffer.put(data)
            buffer.position(0)

            return buffer
        }
    }
}