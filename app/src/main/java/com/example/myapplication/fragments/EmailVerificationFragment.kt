/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * (c) $Name
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.`interface`.respObjDatabase
import com.example.myapplication.activities.AuthActivity
import com.example.myapplication.activities.MapsActivity
import com.example.myapplication.database.DatabaseHelper
import com.example.myapplication.email.EmailHelper
import com.example.myapplication.utilits.editData


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class EmailVerificationFragment : Fragment() {

    private val TAG = "EmailVerificationFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_email_verfication, container, false)

        fragmentLayout.findViewById<ImageView>(R.id.exitbutton).setOnClickListener {
            findNavController().popBackStack()
        }
        val email: String = arguments?.getString("EmailOfPeople", "email").toString()
        val outputCode: String = sendEmailVerification(email)
        fragmentLayout.findViewById<TextView>(R.id.textemail).text = "Мы отправили код на почту $email"
        Log.d(TAG, outputCode)
        fragmentLayout.findViewById<TextView>(R.id.continue_next).setOnClickListener {
            val codeInput: String = fragmentLayout.findViewById<EditText>(R.id.codeInput).text.toString()
            checkCode(outputCode, codeInput)
        }
        Log.d(TAG, "Init $TAG is successful")
        return fragmentLayout
    }

    private fun sendEmailVerification(email: String): String {
        Log.d(TAG, "Start Email Verification")
        val contextCompats = requireContext().applicationContext
        return EmailHelper().setVerifyCode(contextCompats, email)

    }
    private fun checkCode(outputCode: String, codeInput: String) {
        val contextCompats = requireContext().applicationContext
        if(outputCode == codeInput){
            val name: String = arguments?.getString("NameOfPeople", "name").toString()
            val email: String = arguments?.getString("EmailOfPeople", "email").toString()
            val password: String = arguments?.getString("PasswordOfPeople", "password").toString()
            val lastname: String = arguments?.getString("LastNameOfPeople", "lastname").toString()
            editData(contextCompats, "USERNAME", "USERNAME_EMAIL", email, "putString")
            editData(contextCompats, "NameOfPeople", "NamePeople", name, "putString")
            editData(contextCompats, "AuthSuccessful", "AuthComp", "1", "putInt")
            DatabaseHelper(requireFragmentManager()) {
                Log.d(TAG, respObjDatabase.response[0].email)
            }.getTwoData("INSERT INTO `accounts`(`name`, `lastname`, `email`, `password`) VALUES ('$name','$lastname','$email','$password')")
            Handler().postDelayed(
                {
                    val intent = Intent(contextCompats, MapsActivity::class.java)
                    startActivity(intent)
                    finishAffinity(activity as AuthActivity)
                },
                2000 // value in milliseconds
            )
        } else {
            Log.d(TAG, "Код был введен неверно!")
            Log.d(TAG, "$outputCode != $codeInput")
        }
    }
}
