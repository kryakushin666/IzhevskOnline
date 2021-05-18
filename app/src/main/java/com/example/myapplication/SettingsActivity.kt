package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.splunk.mint.Mint
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()
        Mint.initAndStartSession(this.application, "e35d8a22")
        Mint.enableLogging(true)
        backbutton.setOnClickListener {
            this.finish()
        }
        findViewById<TextView>(R.id.faqbutton).setOnClickListener {
            val intent = Intent(this, FAQActivity::class.java)
            startActivity(intent)
        }
    }

}