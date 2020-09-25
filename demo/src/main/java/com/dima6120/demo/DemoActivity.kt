package com.dima6120.demo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.opengl.GLSurfaceView
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dima6120.demo.databinding.ActivityDemoBinding
import com.dima6120.oglHelper.GLThreadHelper

class DemoActivity : AppCompatActivity() {
    private val RC_GALLERY_REQUEST = 0

    private lateinit var binding: ActivityDemoBinding

    private var uriCallback : ((Uri) -> Unit)? = null
    private var requestPermissionsCallback : ((Boolean) -> Unit)? = null

    private lateinit var renderer: Renderer
    private lateinit var glThreadHelper: GLThreadHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDemoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initGLView()

        binding.glView.setOnClickListener {
            openImagePickerWithPermissionsChecking { uri ->
                Glide.with(this).asBitmap().load(uri).into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        glThreadHelper.runAndRender {
                            renderer.setTexture(resource)
                        }
                    }
                })
            }
        }
    }

    private fun initGLView() {
        val glView = binding.glView

        renderer = Renderer(applicationContext)

        glView.setEGLContextClientVersion(3)
        glView.preserveEGLContextOnPause = true
        glView.setRenderer(renderer)
        glView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        glThreadHelper = GLThreadHelper(glView)
    }

    override fun onResume() {
        super.onResume()

        binding.glView.onResume()
    }

    override fun onPause() {
        super.onPause()

        binding.glView.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_GALLERY_REQUEST) {
                data?.data?.let {
                    uriCallback?.invoke(it)
                }

                uriCallback = null
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val granted = PermissionUtils.verifyPermissions(grantResults)

        requestPermissionsCallback?.invoke(granted)
        requestPermissionsCallback = null
    }

    private fun openImagePicker(callback : ((Uri) -> Unit)) {
        uriCallback = callback

        val intent = Intent(Intent.ACTION_PICK)

        intent.type = "image/*"

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, RC_GALLERY_REQUEST)
        }
    }

    private fun openImagePickerWithPermissionsChecking(callback : ((Uri) -> Unit)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            openImagePicker(callback)
        } else {
            requestPermissions(PermissionUtils.STORAGE_PERMISSIONS) { granted ->
                if (granted) {
                    openImagePicker(callback)
                }
            }
        }
    }

    fun requestPermissions(permissions: Array<String>, callback: ((Boolean) -> Unit)) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || PermissionUtils.hasPermissions(this, permissions)) {
            callback.invoke(true)
        } else {
            requestPermissions(permissions, 0)
            requestPermissionsCallback = callback
        }
    }
}