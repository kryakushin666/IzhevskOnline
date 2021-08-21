package com.example.myapplication.fragments

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.utilits.LandmarkRecognitionModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.gson.*
import com.ibm.icu.text.Transliterator
import kotlinx.android.synthetic.main.fragment_landmarkcheck.*
import java.io.InputStream


class LandmarkDetectorFragment : Fragment() {

    private lateinit var functions: FirebaseFunctions
    private val landmarkRecognitionModels = ArrayList<LandmarkRecognitionModel>()
    var CYRILLIC_TO_LATIN = "Latin-Russian/BGN"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_landmarkcheck, container, false)
        functions = Firebase.functions
        fragmentLayout.findViewById<ImageView>(R.id.buttonback).setOnClickListener { findNavController().popBackStack() }
        fragmentLayout.findViewById<CardView>(R.id.cameraOpen).setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .saveDir(context?.getExternalFilesDir(Environment.DIRECTORY_DCIM)!!)
                .start()
        }
        // возвращаем макет фрагмента
        return fragmentLayout
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // The last parameter value of shouldHandleResult() is the value we pass to setRequestCode().
        // If we do not call setRequestCode(), we can ignore the last parameter.
        if (requestCode == 2404) {
            val uri: Uri = data?.data!!
            // Do stuff with image's path or id. For example:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    Glide.with(requireContext())
                        .load(uri)
                        .into(inputImage)

                    val inputStream: InputStream =
                        context?.contentResolver?.openInputStream(uri)!!
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream.close()

                    val firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap)

                    val options = FirebaseVisionCloudDetectorOptions.Builder()
                        .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                        .setMaxResults(5)
                        .build()

                    val detector =
                        FirebaseVision.getInstance().getVisionCloudLandmarkDetector(options)


                    detector.detectInImage(firebaseVisionImage)
                        .addOnSuccessListener {
                            val mutableImage = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                            recognizeLandmarks(it, mutableImage)
                        }
                        .addOnFailureListener {
                            // Task failed with an exception
                        }
                } else {

                    Glide.with(requireContext())
                        .load(data.data!!.path?.toUri()!!)
                        .into(inputImage)

                    val inputStream: InputStream =
                        context?.contentResolver?.openInputStream(data.data!!.path?.toUri()!!)!!
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream.close()

                    val firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap)

                    val options = FirebaseVisionCloudDetectorOptions.Builder()
                        .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                        .setMaxResults(5)
                        .build()

                    val detector =
                        FirebaseVision.getInstance().getVisionCloudLandmarkDetector(options)


                    detector.detectInImage(firebaseVisionImage)
                        .addOnSuccessListener {
                            val mutableImage = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                            recognizeLandmarks(it, mutableImage)
                        }
                        .addOnFailureListener {
                            // Task failed with an exception
                        }
                }
        }
        super.onActivityResult(requestCode, resultCode, data)   // This line is REQUIRED in fragment mode
    }
    private fun recognizeLandmarks(landmarks: List<FirebaseVisionCloudLandmark>?, image: Bitmap?) {
        if (landmarks == null || image == null) {
            nameOfObject.text = "Произошла ошибка"
            return
        }

        for (landmark in landmarks) {
            landmarkRecognitionModels.add(LandmarkRecognitionModel(landmark.landmark, landmark.confidence, landmark.locations))
            val nameObject = landmarkRecognitionModels[0].text
            val toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN)
            val result: String = toLatinTrans.transliterate(landmarkRecognitionModels[0].text)
            Log.d("FinallyRecognize", "Name = '$nameObject', onRus = '$result'")

            if(result == "" || result == null) {
                if(nameObject == "" || nameObject == null) {
                    nameOfObject.text = "Объект не был распознан"
                }
                else { nameOfObject.text = nameObject }
            } else {
                nameOfObject.text = result
            }
        }
    }
}