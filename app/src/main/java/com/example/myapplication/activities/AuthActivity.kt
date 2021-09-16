package com.example.myapplication.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.myapplication.R
import com.example.myapplication.`interface`.respObjDatabase
import com.example.myapplication.helpers.DatabaseHelper
import com.example.myapplication.models.VKUser
import com.example.myapplication.utilits.VKUsersCommand
import com.example.myapplication.utilits.editData
import com.example.myapplication.utilits.initMint
import com.splunk.mint.Mint
import com.splunk.mint.MintLogLevel
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.utils.VKUtils.getCertificateFingerprint


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

class AuthActivity : AppCompatActivity() {

    private var TAG = "AuthActivity" // Тэг для логирования

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            // Устанавливаем layout и убираем верхнюю панель
            setContentView(R.layout.activity_auth)
            supportActionBar?.hide()
            // Инициализируем Mint
            initMint(this.application)
            val fingerprints = getCertificateFingerprint(this, this.packageName)
            //Log.d("$TAG FingerPrint", fingerprints!![0]!!)
            //Mint.logEvent("FingerPrint Log", MintLogLevel.Debug, "FingerPrint is", fingerprints[0])
            DatabaseHelper(supportFragmentManager, this.applicationContext) {
            }.getTwoData("INSERT INTO `fingerprints`(`fingerprint`) VALUE ('${fingerprints!![0]}')")
            // Переходим на AuthFragment
            Log.d(TAG, "Transaction to AuthFragment")
        } catch (e: Exception) {
            Log.e(TAG, "onCreateView", e)
            throw e
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Log.d("VKA", "User passed authorization")
                VK.execute(VKUsersCommand(), object : VKApiCallback<List<VKUser>> {
                    override fun success(result: List<VKUser>) {
                        if (result.isNotEmpty()) {
                            val user = result[0]
                            DatabaseHelper(supportFragmentManager, this@AuthActivity.applicationContext) {
                                if (respObjDatabase.response.size != 0) {
                                    editData(
                                        this@AuthActivity,
                                        "AuthSuccessful",
                                        "AuthComp",
                                        "1",
                                        "putInt"
                                    )
                                    val intent = Intent(this@AuthActivity, MapsActivity::class.java)
                                    startActivity(intent)
                                    finishAffinity()
                                } else {
                                    DatabaseHelper(supportFragmentManager, this@AuthActivity.applicationContext) {
                                        editData(
                                            this@AuthActivity,
                                            "AuthSuccessful",
                                            "AuthComp",
                                            "1",
                                            "putInt"
                                        )
                                        val intent =
                                            Intent(this@AuthActivity, MapsActivity::class.java)
                                        startActivity(intent)
                                        finishAffinity()
                                    }.getTwoData("INSERT INTO `accounts`(`name`, `lastname`, `email`, `authVK`) VALUES ('${user.firstName}','${user.lastName}','${token.email}','1')")
                                }
                            }.getTwoData("SELECT `email`, `authVK` FROM `accounts` WHERE `email` = '${token.email}' AND `authVK` = '1'")
                        }
                    }

                    override fun fail(error: Exception) {
                        Log.e("fail", error.toString())
                    }
                })
            }

            override fun onLoginFailed(errorCode: Int) {
                Log.d("VKA", "User didn't pass authorization")
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}