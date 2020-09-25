package com.dima6120.oglHelper.utils

import android.content.Context
import android.content.res.Resources.NotFoundException
import java.io.IOException
import java.io.InputStream

class FileUtils {
    companion object {
        @Throws(NotFoundException::class, IOException::class)
        fun readTextFromRaw(context: Context, resourceId: Int): String {
            return readText(context.resources.openRawResource(resourceId))
        }

        @Throws(NotFoundException::class, IOException::class)
        fun readText(inputStream: InputStream): String {
            return inputStream.bufferedReader().use { it.readText() }
        }

        @Throws(NotFoundException::class, IOException::class)
        fun readTextFromAssets(context: Context, fileName: String): String {
            return readText(context.assets.open(fileName))
        }
    }
}