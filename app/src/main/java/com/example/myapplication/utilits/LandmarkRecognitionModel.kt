package com.example.myapplication.utilits

import com.google.firebase.ml.vision.common.FirebaseVisionLatLng

class LandmarkRecognitionModel(val text: String?, val confidence: Float, val location: MutableList<FirebaseVisionLatLng>)