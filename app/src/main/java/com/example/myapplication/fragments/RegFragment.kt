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
        val contextCompats = requireContext().applicationContext
        initFirebase()
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

        val getEmail: String = arguments?.getString(AuthFragment.OBJECT_EMAIL, "email").toString()
        val getName: String = arguments?.getString(AuthFragment.OBJECT_NAME, "name").toString()
        val getLastName: String = arguments?.getString(AuthFragment.OBJECT_LASTNAME, "lastname").toString()
        if(getName != "name") {
            viewName.text = getName.toEditable()
            viewEmail.text = getEmail.toEditable()
            viewLastName.text = getLastName.toEditable()
            Log.d(TAG, "Автозаполение с помощью ВК!")
        }
        fragmentLayout.findViewById<CardView>(R.id.loginbutton).setOnClickListener {
            val uid: String = UUID.randomUUID().toString()

            val email = viewEmail.text.toString()//.replace(".",",")
            val name = viewName.text.toString()
            val lastname = viewLastName.text.toString()
            val password = viewPassword.text.toString()
            if (email.isEmpty() || name.isEmpty() || lastname.isEmpty() || password.isEmpty()) {
                displayError("Заполните все поля и попробуйте ещё раз.", fragmentLayout)
                return@setOnClickListener
            }
            if (password.length < 6) {
                displayError("Пароль должен быть не меньше, чем 6 символов!", fragmentLayout)
                return@setOnClickListener
            }
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_UID] = uid
            dateMap[CHILD_EMAIL] = email
            dateMap[CHILD_NAME] = name
            dateMap[CHILD_LASTNAME] = lastname
            dateMap[CHILD_PASSWORD] = password
            Log.d(TAG, "$uid, $email, $name, $lastname, $password")
            if (email in banWords || name in banWords || lastname in banWords || password in banWords) {
                displayError("Не выражаться! Капитан Америка не одобряет", fragmentLayout)
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
                    displayMessage(
                        "Регистрация возможна только через почту, либо через номер телефона!",
                        fragmentLayout
                    )
                    return@setOnClickListener
                }
                email.contains("+", ignoreCase = true) -> {
                    displayMessage(
                        "Вы успешно зарегистрировались с помощью телефона!",
                        fragmentLayout
                    )
                }
                email.contains("@", ignoreCase = true) -> {
                    //displayMessage("Запрос на регистрацию с помощью эл. почты!", fragmentLayout)
                    regAccountWithEmail(dateMap)
                            //sendEmailVerification()
                }
                //regAccount(email, dateMap)
                else -> {
                    displayError(
                        "Вход возможен только через почту или номер телефона!",
                        fragmentLayout
                    )
                    return@setOnClickListener
                }
            }
        }
        return fragmentLayout
    }
    private fun regAccountWithEmail(dateMap: MutableMap<String, Any>) {
        val contextCompats = requireContext().applicationContext
        Log.d(TAG, "$TAGFirebase start reg. with email")
        val name: String = dateMap[CHILD_NAME].toString()
        val lastname = dateMap[CHILD_LASTNAME].toString()
        val email = dateMap[CHILD_EMAIL].toString()
        val password: String = dateMap[CHILD_PASSWORD].toString()
        val uid: String = dateMap[CHILD_UID].toString()
        Log.d(TAG,"$name, $lastname, $email, $password")
        val bundle = bundleOf(OBJECT_EMAIL to email, OBJECT_NAME to name, OBJECT_LASTNAME to lastname, OBJECT_PASSWORD to password, OBJECT_UID to uid)
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
    private fun createAccount(email: String, password: String) {
        Log.e(TAGFirebase, "createAccount: $email")
        val contextCompats = requireContext().applicationContext
        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity as AuthActivity) { task ->
                    if (task.isSuccessful) {
                        Log.e(TAGFirebase, "createAccount: Success!")

                        // update UI with the signed-in user's information
                        val user = mAuth!!.currentUser
                        

                    } else {
                        Log.e(TAGFirebase, "createAccount: Fail!", task.exception)
                        Toast.makeText(contextCompats, "Authentication failed!", Toast.LENGTH_SHORT).show()
                    }
                }
    }


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
        var OBJECT_NAME = "null uid"
        var OBJECT_LASTNAME = "null uid"
        var OBJECT_PASSWORD = "null uid"
        var OBJECT_EMAIL = "null email"

    }
}
