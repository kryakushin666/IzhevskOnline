package com.example.myapplication.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextSwitcher
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.activities.SettingsActivity
import com.example.myapplication.activities.bottomNavigationScreen
import com.example.myapplication.activities.bottomNavigationView
import com.example.myapplication.database.SQLiteHelper
import com.example.myapplication.utilits.editData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import es.dmoral.toasty.Toasty
import java.io.File
import java.util.*
import java.util.Base64.getEncoder


var Data: String? = null

class BlankFragment : Fragment() {

    var Date = 0
    var Month = 0
    var Year = 0
    var calendar: Calendar = Calendar.getInstance()

    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_blank, container, false)
        val contextCompats = requireContext().applicationContext
        bottomNavigationView.visibility = View.VISIBLE
        bottomNavigationScreen.state = BottomSheetBehavior.STATE_EXPANDED
        /*val versionCode = BuildConfig.VERSION_CODE.toFloat()
        DatabaseHelper(requireFragmentManager()) {
            if (versionCode != respObjDatabase.response[0].lastversion) {
                UpgradeDialog().show(requireFragmentManager(), "MyCustomFragment")
            }
        }.getTwoData("SELECT * FROM `version_app`")*/
        mAuth = FirebaseAuth.getInstance()
        SQLiteHelper(requireContext(), null)
        fragmentLayout!!.findViewById<ImageView>(R.id.settingb)?.setOnClickListener {
            val intent = Intent(contextCompats, SettingsActivity::class.java)
            startActivity(intent)
        }
        fragmentLayout.findViewById<ImageView>(R.id.settingb)?.setOnLongClickListener {
            editData(contextCompats, "NameOfScreen", "idScreen", "0", "putInt")
            findNavController().navigate(R.id.action_navigation_home_to_newitemFragment)
            return@setOnLongClickListener true
        }
        fragmentLayout.findViewById<ImageView>(R.id.Button).setOnClickListener {
            editData(contextCompats, "NameOfScreen", "idScreen", "0", "putInt")
            findNavController().navigate(R.id.action_navigation_home_to_itemFragment)
        }
        fragmentLayout.findViewById<ImageView>(R.id.button1).setOnClickListener {
            editData(contextCompats, "NameOfScreen", "idScreen", "1", "putInt")
            findNavController().navigate(R.id.action_navigation_home_to_itemFragment)
        }
        fragmentLayout.findViewById<ImageView>(R.id.button2).setOnClickListener {
            editData(contextCompats, "NameOfScreen", "idScreen", "2", "putInt")
            findNavController().navigate(R.id.action_navigation_home_to_itemFragment)
        }
        fragmentLayout.findViewById<ImageView>(R.id.button3).setOnClickListener {
            editData(contextCompats, "NameOfScreen", "idScreen", "3", "putInt")
            findNavController().navigate(R.id.action_navigation_home_to_itemFragment)
        }
        Date = calendar.get(Calendar.DAY_OF_MONTH)
        Month = calendar.get(Calendar.MONTH) + 1
        Year = calendar.get(Calendar.YEAR)
        val monthConverted = when (Month) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> ""
        }
        val monthAndDay = "$Date $monthConverted"
        val textSwitcher = fragmentLayout.findViewById<TextSwitcher>(R.id.goodmorning)
        val textSwitcher2 = fragmentLayout.findViewById<TextSwitcher>(R.id.goodforpeople)
        val name = editData(contextCompats, "NameOfPeople", "NamePeople", "0", "getString")
        var allName = "Добрый день!"
        if(name != "0") {
            allName = "Добрый день, $name!"
        }
        if (textSwitcher != null) {
            textSwitcher.setFactory {
                val textView = TextView(contextCompats)
                textView.textSize = 22f
                textView.typeface =
                    Typeface.createFromAsset(contextCompats.assets, "mainfont.ttf")
                textView.gravity = Gravity.TOP
                textView.setTextColor(Color.parseColor("#2B2C2D"))
                textView
            }
            textSwitcher2.setFactory {
                val textView = TextView(contextCompats)
                textView.gravity = Gravity.TOP
                textView.textSize = 22f
                textView.typeface =
                    Typeface.createFromAsset(contextCompats.assets, "mainfont.ttf")
                textView.setTextColor(Color.parseColor("#2B2C2D"))
                textView
            }
            val `in`: Animation =
                AnimationUtils.loadAnimation(contextCompats, R.anim.slide_in_up)
            Handler().postDelayed(
                {
                    textSwitcher.inAnimation = `in`
                    textSwitcher.setText(monthAndDay)
                },
                500 // value in milliseconds
            )
            Handler().postDelayed(
                {
                    textSwitcher2.inAnimation = `in`
                    textSwitcher2.setText(allName)
                },
                1000 // value in milliseconds
            )

        }
        // возвращаем макет фрагмента
        return fragmentLayout
    }

    override fun onResume() {
        super.onResume()
        bottomNavigationView.visibility = View.VISIBLE
        bottomNavigationScreen.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onStart() {
        super.onStart()
        bottomNavigationView.visibility = View.VISIBLE
        bottomNavigationScreen.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun encoder(filePath: String): String {
        val bytes = File(filePath).readBytes()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getEncoder().encodeToString(bytes)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }
}
