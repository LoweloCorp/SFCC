package com.lowelostudents.caloriecounter.models;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;

import lombok.Data;

@Data
public class Scanner {
    public CameraSelector cameraSelector;
    public Preview preview;

    public ImageAnalysis imageAnalysis;
}
