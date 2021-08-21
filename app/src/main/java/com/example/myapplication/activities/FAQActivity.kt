package com.example.myapplication.activities

import android.content.pm.PackageInfo
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.utilits.initMint
import kotlinx.android.synthetic.main.activity_faq.*

class FAQActivity : AppCompatActivity() {

    /*var rotatingTextWrapper: RotatingTextWrapper? = null
    var rotatingTextSwitcher: RotatingTextSwitcher? = null
    var rotatable: Rotatable? = null*/
    private var versionCode = "0"
    var packageInfo: PackageInfo? = null
    private val textList =
        arrayOf(
            "❤",
            "🧡",
            "💛",
            "💚",
            "💙",
            "💜",
            "🤎",
            "🖤",
            "🤍",
            "💞",
            "💖"
        ) // Массив с сердвечками для показа
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        supportActionBar?.hide()
        initMint(this.application)
        backbutton.setOnClickListener {
            this.finish()
        }
        versionCode = BuildConfig.VERSION_NAME
        val versions = "ver. $versionCode"
        findViewById<TextView>(R.id.version).text = versions

        /*rotatingTextWrapper = findViewById<View>(R.id.custom_switcher) as RotatingTextWrapper
        rotatingTextWrapper!!.size = 22

        rotatable = Rotatable(Color.parseColor("#FFA036"), 1000, "hello", "hello", "help")
        rotatable!!.size = 22f
        rotatable!!.animationDuration = 500
        rotatingTextWrapper!!.setContent("С ? из Ижевска", rotatable)

        version.setOnClickListener {
            rotatingTextWrapper!!.resume(1)
        }*/
        val textSwitcher = findViewById<TextSwitcher>(R.id.withlove)
        if (textSwitcher != null) {
            textSwitcher.setFactory {
                val textView = TextView(this@FAQActivity)
                textView.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
                textView.textSize = 22f
                textView.setTextColor(Color.parseColor("#2B2C2D"))
                textView
            }
            textSwitcher.setText(textList[index])
            val `in`: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_up)
            textSwitcher.inAnimation = `in`
            val out: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_up)
            textSwitcher.outAnimation = out
            textSwitcher.setOnClickListener {
                index = if (index + 1 < textList.size) index + 1 else 0
                textSwitcher.setText(textList[index])
            }
            tap1.setOnClickListener {
                index = if (index + 1 < textList.size) index + 1 else 0
                textSwitcher.setText(textList[index])
            }
            tap2.setOnClickListener {
                index = if (index + 1 < textList.size) index + 1 else 0
                textSwitcher.setText(textList[index])
            }
        }

    }

}