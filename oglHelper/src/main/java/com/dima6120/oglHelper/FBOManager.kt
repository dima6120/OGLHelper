package com.dima6120.oglHelper

import com.dima6120.oglHelper.enums.PixelFormat

class FBOManager {
    private val freeEmptyFBOs = HashSet<FBO>()
    private val busyEmptyFBOs = HashSet<FBO>()

    private val freeFBOs = HashMap<Int, HashMap<PixelFormat, HashSet<FBO>>>()
    private val busyFBOs = HashMap<Int, HashMap<PixelFormat, HashSet<FBO>>>()

    private val freeMSAAFBOs = HashSet<FBO>()
    private val busyMSAAFBOs = HashSet<FBO>()


    var fboWidth = -1
        private set
    var fboHeight = -1
        private set

    private constructor() {
        initFreeBusyCollections(1, PixelFormat.RGBA)
    }

    constructor(width: Int, height: Int) : this() {
        fboWidth = width
        fboHeight = height
    }

    private fun initFreeBusyCollections(textureCount: Int, pixelFormat: PixelFormat) {
        freeFBOs[textureCount] = let {
            val map = HashMap<PixelFormat, HashSet<FBO>>()
            map[pixelFormat] = HashSet()
            map
        }

        busyFBOs[textureCount] = let {
            val map = HashMap<PixelFormat, HashSet<FBO>>()
            map[pixelFormat] = HashSet()
            map
        }
    }

    fun createUnmanagedFBO() = createUnmanagedFBO(PixelFormat.RGBA, false)

    fun createUnmanagedFBO(pixelFormat: PixelFormat) = createUnmanagedFBO(pixelFormat, false)

    fun createUnmanagedFBO(pixelFormat: PixelFormat, msaa: Boolean) = FBO(fboWidth, fboHeight, pixelFormat, msaa)

    fun takeFBO(): FBO {
        val fbo: FBO

        val freeFBOs = freeFBOs[1]!![PixelFormat.RGBA]!!

        if (freeFBOs.isEmpty()) {
            fbo = FBO(fboWidth, fboHeight)
        } else {
            fbo = freeFBOs.iterator().next()
            freeFBOs.remove(fbo)
        }

        busyFBOs[1]!![PixelFormat.RGBA]!!.add(fbo)

        return fbo
    }

    fun take(textureCount: Int, pixelFormat: PixelFormat): FBO {
        val fbo = let fbo@ {
            freeFBOs[textureCount]?.let { freeTextureCountMap ->
                freeTextureCountMap[pixelFormat]?.let { freeFBOs ->
                    if (freeFBOs.isEmpty()) {
                        FBO(fboWidth, fboHeight, pixelFormat, textureCount)
                    } else {
                        freeFBOs.iterator().next().also {
                            freeFBOs.remove(it)
                        }
                    }
                } ?: run {
                    freeTextureCountMap[pixelFormat] = HashSet()
                    busyFBOs[textureCount]!![pixelFormat] = HashSet()

                    FBO(fboWidth, fboHeight, pixelFormat, textureCount)
                }
            } ?: run {
                initFreeBusyCollections(textureCount, pixelFormat)

                FBO(fboWidth, fboHeight, pixelFormat, textureCount)
            }
        }

        busyFBOs[textureCount]!![pixelFormat]!!.add(fbo)

        return fbo
    }

    fun takeMSAA(): FBO {
        val fbo = if (freeMSAAFBOs.isEmpty()) {
            FBO(fboWidth, fboHeight, true)
        } else {
            freeMSAAFBOs.iterator().next().also {
                freeMSAAFBOs.remove(it)
            }
        }

        busyMSAAFBOs.add(fbo)

        return fbo
    }

    fun takeEmpty(): FBO {
        val fbo = if (freeEmptyFBOs.isEmpty()) {
            FBO()
        } else {
            freeEmptyFBOs.iterator().next().also {
                freeEmptyFBOs.remove(it)
            }
        }

        busyEmptyFBOs.add(fbo)

        return fbo
    }

    fun free(fbo: FBO?) {
        fbo?.let {
            when {
                busyEmptyFBOs.contains(fbo) -> {
                    busyEmptyFBOs.remove(fbo)
                    freeEmptyFBOs.add(fbo)
                }

                fbo.msaa -> {
                    busyMSAAFBOs.remove(fbo)
                    freeMSAAFBOs.add(fbo)
                }

                else -> {
                    val textureCount = fbo.getTextureCount()
                    val pixelFormat = fbo.pixelFormat

                    busyFBOs[textureCount]?.get(pixelFormat)?.remove(fbo)
                    freeFBOs[textureCount]?.get(pixelFormat)?.add(fbo)
                }
            }
        }
    }

    fun release(fbo: FBO?) {
        fbo?.let {
            when {
                busyEmptyFBOs.contains(fbo) -> busyEmptyFBOs.remove(fbo)
                fbo.msaa -> busyMSAAFBOs.remove(fbo)
                else -> busyFBOs[fbo.getTextureCount()]?.get(fbo.pixelFormat)?.remove(fbo)
            }
        }
    }

    fun delete(fbo: FBO?) {
        fbo?.let {
            if (busyEmptyFBOs.contains(fbo)) {
                fbo.detachTexture()
            }

            release(fbo)
            fbo.delete()
        }
    }

    private fun deleteFBOs(fbos: Collection<FBO>) {
        fbos.forEach {
            it.delete()
        }
    }

    private fun deleteEmptyFBOs(fbos: Collection<FBO>) {
        fbos.forEach {
            it.detachTexture()
            it.delete()
        }
    }

    fun delete() {
        freeFBOs.forEach { textureCountEntry ->
            textureCountEntry.value.forEach {
                deleteFBOs(it.value)
            }
        }

        busyFBOs.forEach { textureCountEntry ->
            textureCountEntry.value.forEach {
                deleteFBOs(it.value)
            }
        }

        deleteFBOs(freeMSAAFBOs)
        deleteFBOs(busyMSAAFBOs)

        deleteEmptyFBOs(freeEmptyFBOs)
        deleteEmptyFBOs(busyEmptyFBOs)

        freeFBOs.clear()
        freeMSAAFBOs.clear()
        freeEmptyFBOs.clear()
        busyFBOs.clear()
        busyMSAAFBOs.clear()
        busyEmptyFBOs.clear()

        initFreeBusyCollections(1, PixelFormat.RGBA)
    }
}