package com.example.myapplication

import android.content.pm.PackageInfo
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.splunk.mint.Mint
import kotlinx.android.synthetic.main.activity_faq.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FAQActivity : AppCompatActivity() {

    private var versionCode = "0"
    var packageInfo: PackageInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        supportActionBar?.hide()
        Mint.initAndStartSession(this.application, "e35d8a22")
        Mint.enableLogging(true)
        backbutton.setOnClickListener {
            this.finish()
        }
        versionCode = BuildConfig.VERSION_NAME
        val versions = "ver. $versionCode"
        findViewById<TextView>(R.id.version).text = versions
    }

}