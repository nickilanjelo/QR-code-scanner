package com.nickilanjelo.qr_code_scanner.app.camera_screen.di

import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@InstallIn(FragmentComponent::class)
@Module
object CameraXModule {
    @Provides
    @FragmentScoped
    fun provideBarCodeScanner(options: BarcodeScannerOptions): BarcodeScanner {
        return BarcodeScanning.getClient(options)

    }

    @Provides
    @FragmentScoped
    fun provideBarCodeScannerOptions(): BarcodeScannerOptions {
        return BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    }
}