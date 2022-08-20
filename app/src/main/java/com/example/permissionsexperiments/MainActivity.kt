package com.example.permissionsexperiments

import StorefrontLifecycleObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var observer : StorefrontLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observer = StorefrontLifecycleObserver(this.activityResultRegistry, this)
        lifecycle.addObserver(observer)

        val buttonCamera = findViewById<Button>(R.id.req_camera)
        val buttonAudio = findViewById<Button>(R.id.req_audio)
        val buttonMultiple = findViewById<Button>(R.id.req_multiple)

        buttonCamera.setOnClickListener {
            observer.requestPermission(android.Manifest.permission.CAMERA)
        }
        buttonAudio.setOnClickListener {
            observer.requestPermission(android.Manifest.permission.RECORD_AUDIO)
        }
        buttonMultiple.setOnClickListener {
            observer.requestMultiple(
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }
}
