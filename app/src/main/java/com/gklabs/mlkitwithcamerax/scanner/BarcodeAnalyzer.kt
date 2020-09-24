package com.gklabs.mlkitwithcamerax.scanner

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage


class BarcodeAnalyzer(
    private val barcodesDetected: (barCodes: List<Barcode>) -> Unit
) : ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        Log.e("BarcodeAnalyzer", "inside analyze")
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            )
            .build()
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            Log.e("BarcodeAnalyzer", "inside mediaImage")
            val visionImage =
                InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient(options)
            Log.e("BarcodeAnalyzer", "inside scanner")
            val result = scanner.process(visionImage)
                .addOnSuccessListener { barcodes ->
                    barcodesDetected(barcodes)
                }
                .addOnFailureListener {
                    Log.e("BarcodeAnalyzer", "something went wrong", it)
                }

        }
    }
}