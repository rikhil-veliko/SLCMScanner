package com.kefi.demo.basecomponet

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager



import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.kefi.demo.R

import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector



class ScanningActivity: AppCompatActivity(), SurfaceHolder.Callback, Detector.Processor<Barcode> ,
    View.OnClickListener{
    lateinit var  cameraView:SurfaceView
    lateinit var txtView:TextView
    lateinit var cameraSource:CameraSource
    lateinit var detector: BarcodeDetector
    lateinit var button:Button
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode:Int, @NonNull permissions:Array<String>, @NonNull grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    try
                    {
                        cameraSource.start(cameraView.getHolder())
                    }
                    catch (e:Exception) {
                    }
                }else{
                    val intent = Intent()
                    intent.putExtra("Error", "Please allow camera permission")
                    setResult(1, intent)
                    finish()
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scaning)
        cameraView = findViewById(R.id.surface_view)
        detector = BarcodeDetector.Builder(getApplicationContext())
            .setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE)
            .build()
        if (!detector.isOperational())
        {
            val intent = Intent()
            intent.putExtra("Error", "Hardware error, Please check your camera")
            setResult(1, intent)
            finish()
            return
        } else
        {
            cameraSource = CameraSource.Builder(getApplicationContext(), detector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(300, 300)
                .setRequestedFps(10.0f)
                .setAutoFocusEnabled(true)
                .build()
            cameraView.getHolder().addCallback(this)
            detector.setProcessor(this)
        }

    }
    override fun surfaceCreated(holder:SurfaceHolder) {
        try
        {
            if ((ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED))
            {
                ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.CAMERA), 1)
                return
            }
            cameraSource.start(cameraView.getHolder())
        }
        catch (e:Exception) {
            e.printStackTrace()
        }
    }
    override fun surfaceChanged(holder:SurfaceHolder, format:Int, width:Int, height:Int) {
    }
    override fun surfaceDestroyed(holder:SurfaceHolder) {
        cameraSource.stop()
    }
    override fun release() {
    }
    override fun receiveDetections(detections:Detector.Detections<Barcode> ) {
        val items = detections.getDetectedItems()

        if( items.size()>0) {
            val intent = Intent()
            intent.putExtra("BarcodeValue", items.valueAt(0).displayValue)
            setResult(2, intent)
            finish()


        }

    }

    override fun onClick(v: View?) {
        detector = BarcodeDetector.Builder(getApplicationContext())
            .setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE)
            .build()
        if (!detector.isOperational())
        {
            txtView.setText("Could not set up the detector!")
            return
        } else
        {
            cameraSource = CameraSource.Builder(getApplicationContext(), detector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setRequestedFps(2.0f)
                .setAutoFocusEnabled(true)
                .build()
            cameraView.getHolder().addCallback(this)
            detector.setProcessor(this)
        }
        if ((ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.CAMERA), 1)
            return
        }
        cameraSource.start(cameraView.getHolder())
    }
}