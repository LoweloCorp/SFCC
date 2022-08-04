package com.lowelostudents.caloriecounter.ui.models;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.lowelostudents.caloriecounter.databinding.ActivityScannerBinding;
import com.lowelostudents.caloriecounter.models.Scanner;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.services.OpenFoodFactsService;
import com.lowelostudents.caloriecounter.services.ResponseCallback;

import org.json.JSONException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;

public class ScannerActivity extends AppCompatActivity {
    private ActivityScannerBinding binding;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    Scanner scanner = new Scanner();

    @SneakyThrows
    protected void setEventHandlers() {
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivityWithResult(Activity.RESULT_CANCELED, null);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityScannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        setEventHandlers();
        checkPermissions();
        buildCamera();

    }

    private void checkPermissions() {
        String[] ungrantedPermissions = new String[2];

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
            ungrantedPermissions[0] = Manifest.permission.INTERNET;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ungrantedPermissions[1] = Manifest.permission.CAMERA;
        }

        requestPermissions(ungrantedPermissions, 666);
    }

    private void buildPreview() {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());

        this.scanner.setPreview(preview);
        this.scanner.setCameraSelector(cameraSelector);
    }

    @OptIn(markerClass = androidx.camera.core.ExperimentalGetImage.class)
    private void buildImageAnalysis() {
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                // enable the following line if RGBA output is needed.
                //.setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .setTargetResolution(new Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        imageAnalysis.setAnalyzer(executorService, new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy imageProxy) {
                Image image = imageProxy.getImage();

                if (image != null) {
                    InputImage inputImage = InputImage.fromMediaImage(image, imageProxy.getImageInfo().getRotationDegrees());

                    BarcodeScanner barcodeScanner = BarcodeScanning.getClient();

                    barcodeScanner.process(inputImage).addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            OpenFoodFactsService openFoodFactsService = OpenFoodFactsService.getInstance(getApplicationContext());

                            if (!barcodes.isEmpty()) {
                                openFoodFactsService.getProduct(barcodes.get(0), new ResponseCallback() {

                                    @Override
                                    public void onResult(Food food) {
                                        if (Double.isNaN(food.getCalTotal())) {
                                            CharSequence info = "Food not found - Please try again";
                                            Toast toast = Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG);
                                            toast.show();
                                            finishActivityWithResult(RESULT_CANCELED, null);
                                        }

                                        Log.w("SCANNEDFOOD", food.toString());

//                                        barcodeScanner.close();
                                        finishActivityWithResult(Activity.RESULT_OK, food);
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        CharSequence info = "Food not found - Please try again";
                                        Toast toast = Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG);
                                        toast.show();
                                        finishActivityWithResult(Activity.RESULT_CANCELED, null);
                                    }
                                });
                            }
                        }
                    });
                }

                imageProxy.close();
            }
        });

        this.scanner.setImageAnalysis(imageAnalysis);
    }


    private void buildCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                buildPreview();
                buildImageAnalysis();
                Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, this.scanner.cameraSelector, this.scanner.imageAnalysis, this.scanner.preview);
                camera.getCameraControl().cancelFocusAndMetering();
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));
    }




    private void finishActivityWithResult(int resultCode, @Nullable Food food) {

        if (resultCode == Activity.RESULT_OK) {
            setResult(resultCode, new Intent().putExtra("food", (Serializable) food));
        }

        finish();
    }
}