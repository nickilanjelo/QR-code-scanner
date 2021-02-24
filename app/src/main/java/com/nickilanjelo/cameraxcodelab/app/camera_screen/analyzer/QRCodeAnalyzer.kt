package com.nickilanjelo.cameraxcodelab.app.camera_screen.analyzer

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import javax.inject.Inject

class QRCodeAnalyzer(
    private val scanner: BarcodeScanner,
    private val listener: (Barcode) -> Unit
) : ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        Log.d("myTag", "analyzing...")
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    Log.d("myTag", "processing images")
                    val barcode = barcodes.firstOrNull {
                        it.format == Barcode.FORMAT_QR_CODE
                    }

                    barcode?.let {
                        listener.invoke(it)
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}