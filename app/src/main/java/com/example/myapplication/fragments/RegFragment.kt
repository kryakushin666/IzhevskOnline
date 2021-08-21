/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * (c) $Name
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

package com.example.myapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.activities.AuthActivity
import com.example.myapplication.utilits.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import java.util.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class RegFragment : Fragment() {

    private val TAGFirebase = "FirebaseEmailPassword"
    private val TAG = "RegFragment"

    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.activity_reg, container, false)
        fragmentLayout.findViewById<ImageView>(R.id.exitbutton).setOnClickListener {
            findNavController().popBackStack()
        }
        mAuth = FirebaseAuth.getInstance()
        /*when (loaddata()) {
            0 -> {
                val intent = Intent(this, AuthFragment::class.java)
                startActivity(intent)
            }
        }*/
        Log.d(TAG, "init $TAG is successful")

        val viewName = fragmentLayout.findViewById<EditText>(R.id.name)
        val viewEmail = fragmentLayout.findViewById<EditText>(R.id.email)
        val viewLastName = fragmentLayout.findViewById<EditText>(R.id.lastname)
        val viewPassword = fragmentLayout.findViewById<EditText>(R.id.password)

        val getEmail: String = arguments?.getString("emailAuthActivity", "email").toString()
        val getName: String = arguments?.getString("nameAuthActivity", "name").toString()
        val getLastName: String = arguments?.getString("lastNameAuthActivity", "lastname").toString()
        if(getName != "name" && getName != "null") {
            viewName.text = getName.toEditable()
            viewEmail.text = getEmail.toEditable()
            viewLastName.text = getLastName.toEditable()
            Log.d(TAG, "Автозаполение с помощью ВК!")
        }
        fragmentLayout.findViewById<CardView>(R.id.loginbutton).setOnClickListener {

            val email = viewEmail.text.toString()//.replace(".",",")
            val name = viewName.text.toString()
            val lastname = viewLastName.text.toString()
            val password = viewPassword.text.toString()
            if (email.isEmpty() || name.isEmpty() || lastname.isEmpty() || password.isEmpty()) {
                return@setOnClickListener
            }
            if (password.length < 6) {
                return@setOnClickListener
            }
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_EMAIL] = email
            dateMap[CHILD_NAME] = name
            dateMap[CHILD_LASTNAME] = lastname
            dateMap[CHILD_PASSWORD] = password
            Log.d(TAG, "$email, $name, $lastname, $password")
            if (email in banWords || name in banWords || lastname in banWords || password in banWords) {
                return@setOnClickListener
            }
            when {
                /*email.contains(".", ignoreCase = true) ->
                {
                    displayError("Ошибка: ErrReg#1. Попробуйте ещё раз.")
                    Log.e("ErrReg", "Найден знак `.`. Email/Phone: $email")
                    return@setOnClickListener
                }*/
                email.contains("+", ignoreCase = true) && email.contains("@", ignoreCase = true) -> {
                    return@setOnClickListener
                }
                email.contains("+", ignoreCase = true) -> {
                }
                email.contains("@", ignoreCase = true) -> {
                    //displayMessage("Запрос на регистрацию с помощью эл. почты!", fragmentLayout)
                    regAccountWithEmail(dateMap)
                            //sendEmailVerification()
                }
                //regAccount(email, dateMap)
                else -> {
                    return@setOnClickListener
                }
            }
        }
        return fragmentLayout
    }
    private fun regAccountWithEmail(dateMap: MutableMap<String, Any>) {
        val contextCompats = requireContext().applicationContext
        Log.d(TAG, "$TAGFirebase start reg. with email")
        val name = dateMap[CHILD_NAME].toString()
        val lastname = dateMap[CHILD_LASTNAME].toString()
        val email = dateMap[CHILD_EMAIL].toString()
        val password: String = dateMap[CHILD_PASSWORD].toString()
        Log.d(TAG,"$name, $lastname, $email, $password")
        val bundle = bundleOf("EmailOfPeople" to email, "NameOfPeople" to name, "LastNameOfPeople" to lastname, "PasswordOfPeople" to password)
        findNavController().navigate(R.id.action_RegFragment_to_EmailVerificationFragment, bundle)
        /*REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.e(TAGFirebase, "createAccount: 1-2!")
                Handler().postDelayed(
                    {
                        findNavController().navigate(R.id.action_RegFragment_to_EmailVerificationFragment)
                    },
                    2000 // value in milliseconds
                )

                Log.e(TAGFirebase, "createAccount: Ok!")
            }
            else
            {
                Log.d(TAGFirebase, task.exception.toString())
            }
        }*/
    }
    private fun regAccountWithPhone(
        phone: String,
        password: String,
        uid: String,
        dateMap: MutableMap<String, Any>
    ) {
        
    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)


    /*private fun sendEmailVerification() {
        // Disable Verify Email button
        Log.d(TAG, "$TAGFirebase start emailVerification")
        val contextCompats = requireContext().applicationContext
        val user = mAuth!!.currentUser
        user!!.sendEmailVerification()
                .addOnCompleteListener(activity as AuthActivity) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(contextCompats, "Verification email sent to " + user.email!!, Toast.LENGTH_SHORT).show()

                    }
                    else {
                        Log.e(TAGFirebase, "sendEmailVerification failed!", task.exception)
                        Toast.makeText(contextCompats, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                    }
                }

    }*/
    companion object {
        var OBJECT_UID = "null uid"
        var OBJECT_LASTNAME = "null uid"
        var OBJECT_PASSWORD = "null uid"
        var OBJECT_EMAIL = "null email"

    }
}
