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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.`interface`.respObjDatabase
import com.example.myapplication.activities.AuthActivity
import com.example.myapplication.activities.MapsActivity
import com.example.myapplication.helpers.DatabaseHelper
import com.example.myapplication.utilits.editData
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class AuthFragment : Fragment() {

    private val TAG = "AuthEmailPassword"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        val fragmentLayout = inflater.inflate(R.layout.fragment_auth, container, false)
        val contextCompats = requireContext().applicationContext

        fragmentLayout.findViewById<ImageView>(R.id.exitbutton).setOnClickListener {
            val intent = Intent(contextCompats, MapsActivity::class.java)
            startActivity(intent)
            /*VK.logout()*/
        }
        fragmentLayout.findViewById<TextView>(R.id.regAct).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_regFragment)
        }
        if (editData(contextCompats, "AuthSuccessful", "AuthComp", "0", "getInt")?.toInt() == 1) {
            val intent = Intent(contextCompats, MapsActivity::class.java)
            startActivity(intent)
            finishAffinity(activity as AuthActivity)
        }
        Log.d("VK appId", "${VK.getAppId(contextCompats)}")
        fragmentLayout.findViewById<ImageView>(R.id.vkauth).setOnClickListener {
            VK.login(this.requireActivity(), arrayListOf(VKScope.EMAIL, VKScope.PHONE))
        }
        fragmentLayout.findViewById<CardView>(R.id.loginbutton).setOnClickListener {
            //val uid:String = UUID.randomUUID().toString()

            val email = fragmentLayout.findViewById<EditText>(R.id.email).text.toString()
            val password = fragmentLayout.findViewById<EditText>(R.id.password).text.toString()
            Log.d(TAG,"$email, $password")
            DatabaseHelper(requireFragmentManager(), contextCompats) {
                Log.d(TAG, respObjDatabase.response[0].email + respObjDatabase.response[0].password)
                if(respObjDatabase.response[0].email != "") {
                    editData(contextCompats, "AuthSuccessful", "AuthComp", "1", "putInt")
                    val intent = Intent(contextCompats, MapsActivity::class.java)
                    startActivity(intent)
                    finishAffinity(activity as AuthActivity)
                }
            }.getTwoData("SELECT `email`, `password` FROM `accounts` WHERE `email` = '$email' AND `password` = '$password'")
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
                    displayMessage("???? ?????????????? ????????????????????????????")
                } else {
                    displayError("?????????????????????? ???????????????? ????????????")
                }
            } else {
                displayError("???????????? ???????????????????????? ?????????? ?????????????????????? ??????????")
            }
        }*/
        return fragmentLayout
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Log.d("VK", "User passed authorization")
            }

            override fun onLoginFailed(errorCode: Int) {
                Log.d("VK", "User didn't pass authorization")
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    companion object {
        var OBJECT_NAME = ""
        var OBJECT_LASTNAME = ""
        var OBJECT_EMAIL = ""
    }
}
