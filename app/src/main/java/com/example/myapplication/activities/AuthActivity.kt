package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.models.VKUser
import com.example.myapplication.utilits.VKUsersCommand
import com.example.myapplication.utilits.initFirebase
import com.example.myapplication.utilits.initMint
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.activity_auth.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class AuthActivity : AppCompatActivity() {

    private lateinit var refUsers: DatabaseReference
    private var checkemail: String? = null
    private var checkpassword: String? = null
    private var checkname: String? = null
    var emailvk: String? = "hellp"
    private val TAG = "FirebaseAuthEmailPassword"

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar?.hide()
        initMint(this.application)
        exitbutton.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finishAffinity()
            /*VK.logout()*/
        }
        regAct.setOnClickListener {
            val intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
            //requestUsers()*/
        }
        if (loadAuth() == 1) {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        initFirebase()
        Log.d("appid", "${VK.getAppId(this)}")
        vkauth.setOnClickListener {
            VK.login(this, arrayListOf(VKScope.EMAIL, VKScope.PHONE))
        }

        loginbutton.setOnClickListener {
            //val uid:String = UUID.randomUUID().toString()

            val email = email.text.toString().replace(".", ",")
            val password = password.text.toString()
            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.e(TAG, "authAccount: Success!")

                    // update UI with the signed-in user's information
                    val user = mAuth!!.currentUser


                } else {
                    Log.e(TAG, "authAccount: Fail!", task.exception)
                    android.widget.Toast.makeText(applicationContext, "Authentication failed!", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        }

            /*refUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(email)
            refUsers.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user: User? = snapshot.getValue(User::class.java)

                        checkemail = user!!.getEmail()
                        checkpassword = user.getPassword()
                        checkname = user.getName()
                    }
                }
            })
            if (email == checkemail) {
                if (password == checkpassword) {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    Log.d("email", "email: $checkemail")
                    putAuth()
                    putName(checkname!!)
                    displayMessage("Вы успешно авторизовались")
                } else {
                    displayError("Неправильно подобран пароль")
                }
            } else {
                displayError("Указан некорректный адрес электронной почты")
            }
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Log.d("VK", "User passed authorization")
                emailvk = token.email
            }

            override fun onLoginFailed(errorCode: Int) {
                Log.d("VK", "User didn't pass authorization")
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun requestUsers() {
        VK.execute(VKUsersCommand(), object : VKApiCallback<List<VKUser>> {
            override fun success(result: List<VKUser>) {
                if (!isFinishing && result.isNotEmpty()) {
                    val nameTV = findViewById<TextView>(R.id.password)
                    val emailTV = findViewById<TextView>(R.id.email)
                    val user = result[0]
                    emailTV.text = "$emailvk"
                    nameTV.text = "${user.firstName} ${user.lastName}"
                }
            }

            override fun fail(error: Exception) {
                Log.e("fail", error.toString())
            }
        })
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

    private fun putName(name: String) {
        val pref = this.getSharedPreferences("NameofPeople", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putString("NamePeople", name)
        editor?.apply()
    }

    private fun loadAuth(): Int {
        val pref = this.getSharedPreferences("AuthSuccessful", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        val authComp = pref!!.getInt("AuthComp", 0)
        editor?.apply()
        return authComp
    }

    private fun putAuth() {
        val pref = this.getSharedPreferences("AuthSuccessful", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putInt("AuthComp", 1)
        editor?.apply()
    }
}