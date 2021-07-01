package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.utilits.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reg.*
import java.util.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class RegActivity : AppCompatActivity() {

    private val TAG = "FirebaseEmailPassword"

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
        supportActionBar?.hide()
        initMint(this.application)
        exitbutton.setOnClickListener {
            this.finish()
        }
        initFirebase()
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth!!.currentUser
        /*when (loaddata()) {
            0 -> {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
        }*/
        loginbutton.setOnClickListener {
            // val uid:String = UUID.randomUUID().toString()

            val email = email.text.toString()//.replace(".",",")
            val name = name.text.toString()
            val lastname = lastname.text.toString()
            val password = password.text.toString()
            if (email.isEmpty() || name.isEmpty() || lastname.isEmpty() || password.isEmpty()) {
                displayError("Заполните все поля и попробуйте ещё раз.")
                return@setOnClickListener
            }
            if (password.length < 6) {
                displayError("Пароль должен быть не меньше, чем 6 символов!")
                return@setOnClickListener
            }
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_EMAIL] = email
            dateMap[CHILD_NAME] = name
            dateMap[CHILD_LASTNAME] = lastname
            dateMap[CHILD_PASSWORD] = password
            if (email in banWords || name in banWords || lastname in banWords || password in banWords) {
                displayError("Не выражаться! Капитан Америка не одобряет")
                return@setOnClickListener
            }
            when {
                /*email.contains(".", ignoreCase = true) ->
                {
                    displayError("Ошибка: ErrReg#1. Попробуйте ещё раз.")
                    Log.e("ErrReg", "Найден знак `.`. Email/Phone: $email")
                    return@setOnClickListener
                }*/
                else -> {
                    //regAccount(email, dateMap)
                    createAccount(email, password)
                    Handler().postDelayed(
                            {
                                sendEmailVerification()
                                putName(name)
                            },
                            2000 // value in milliseconds
                    )
                }
            }
        }
    }

    private fun displayError(message: String) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundResource(R.drawable.curved_bg_error)
        snackBar.show()
    }

    private fun displayMessage(message: String) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundResource(R.drawable.curved_bg_successful)
        snackBar.show()
    }

    private fun regAccount(email: String, dateMap: MutableMap<String, Any>) {
        REF_DATABASE_ROOT.child(NODE_USERS).child(email).updateChildren(dateMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                when {
                    email.contains("+", ignoreCase = true) && email.contains("@", ignoreCase = true) -> {
                        displayMessage("Регистрация возможна только через почту, либо через номер телефона!")
                        return@addOnCompleteListener
                    }
                    email.contains("+", ignoreCase = true) -> {
                        displayMessage("Вы успешно зарегистрировались с помощью телефона!")
                    }
                    email.contains("@", ignoreCase = true) -> {
                        displayMessage("Вы успешно зарегистрировались с помощью почты!")
                    }
                    else -> {
                        displayError("Вход возможен только через почту или номер телефона!")
                        return@addOnCompleteListener
                    }
                }
                Handler().postDelayed(
                        {
                            val intent = Intent(this, MapsActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        },
                        2000 // value in milliseconds
                )
                putAuth()
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        Log.e(TAG, "createAccount: $email")
        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.e(TAG, "createAccount: Success!")

                        // update UI with the signed-in user's information
                        val user = mAuth!!.currentUser

                    } else {
                        Log.e(TAG, "createAccount: Fail!", task.exception)
                        Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
                    }
                }
    }


    private fun sendEmailVerification() {
        // Disable Verify Email button

        val user = mAuth!!.currentUser
        user!!.sendEmailVerification()
                .addOnCompleteListener(this) { task ->
                    // Re-enable Verify Email button

                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Verification email sent to " + user.email!!, Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e(TAG, "sendEmailVerification failed!", task.exception)
                        Toast.makeText(applicationContext, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun putAuth() {
        val pref = this.getSharedPreferences("AuthSuccessful", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putInt("AuthComp", 1)
        editor?.apply()
    }

    private fun putName(name: String) {
        val pref = this.getSharedPreferences("NameofPeople", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putString("NamePeople", name)
        editor?.apply()
    }
}
