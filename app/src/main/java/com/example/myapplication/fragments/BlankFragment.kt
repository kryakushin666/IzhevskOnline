package com.example.myapplication.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
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
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.activities.ItemActivity
import com.example.myapplication.activities.SettingsActivity
import java.util.*


class BlankFragment : Fragment() {

    var Date = 0
    var Month = 0
    var Year = 0
    var calendar: Calendar = Calendar.getInstance()

    private lateinit var pref: SharedPreferences

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_blank, container, false)
        val contextCompats = requireContext().applicationContext

        fragmentLayout.findViewById<ImageView>(R.id.Button).setOnClickListener {
            initData(0)
        }
        fragmentLayout.findViewById<ImageView>(R.id.button1).setOnClickListener {
            initData(1)
        }
        fragmentLayout.findViewById<ImageView>(R.id.button2).setOnClickListener {
            initData(2)
        }
        fragmentLayout.findViewById<ImageView>(R.id.button3).setOnClickListener {
            initData(3)
        }
        fragmentLayout.findViewById<ImageView>(R.id.settingb).setOnClickListener {
            val intent = Intent(contextCompats, SettingsActivity::class.java)
            startActivity(intent)
            //SeeAdDialog().show(requireFragmentManager(), "MyCustomFragment")
           //resources.getColor(R.color.colorMain) == "#121421".toInt()
        }
        Date = calendar.get(Calendar.DAY_OF_MONTH)
        Month = calendar.get(Calendar.MONTH) + 1
        Year = calendar.get(Calendar.YEAR)
        val monthconverted = when (Month) {
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
        val monthandday = "$Date $monthconverted"
        val textSwitcher = fragmentLayout.findViewById<TextSwitcher>(R.id.goodmorning)
        val textSwitcher2 = fragmentLayout.findViewById<TextSwitcher>(R.id.goodforpeople)
        val allname = "Добрый день, ${loadName()}!"

        if (textSwitcher != null) {
            textSwitcher.setFactory {
                val textView = TextView(contextCompats)
                textView.textSize = 22f
                textView.typeface = Typeface.createFromAsset(contextCompats.assets, "mainfont.ttf")
                textView.gravity = Gravity.TOP
                textView.setTextColor(Color.parseColor("#2B2C2D"))
                textView
            }
            textSwitcher2.setFactory {
                val textView = TextView(contextCompats)
                textView.gravity = Gravity.TOP
                textView.textSize = 22f
                textView.typeface = Typeface.createFromAsset(contextCompats.assets, "mainfont.ttf")
                textView.setTextColor(Color.parseColor("#2B2C2D"))
                textView
            }
            val `in`: Animation = AnimationUtils.loadAnimation(contextCompats, R.anim.slide_in_up)
            Handler().postDelayed(
                {
                    textSwitcher.inAnimation = `in`
                    textSwitcher.setText(monthandday)
                },
                500 // value in milliseconds
            )
            Handler().postDelayed(
                {
                    textSwitcher2.inAnimation = `in`
                    textSwitcher2.setText(allname)
                },
                1000 // value in milliseconds
            )

        }
        // возвращаем макет фрагмента
        return fragmentLayout
    }

    private fun initData(id: Int) {
        val contextCompats = requireContext().applicationContext
        val pref = context?.getSharedPreferences("NameOfScreen", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putInt("idScreen", id)
        editor?.apply()
        val intent = Intent(contextCompats, ItemActivity::class.java)
        startActivity(intent)
        //findNavController().navigate(R.id.action_navigation_home_to_itemFragment)
    }

    private fun loadName(): String? {
        val pref = context?.getSharedPreferences("NameofPeople", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        val name = pref!!.getString("NamePeople", "")
        editor?.apply()
        return name
    }
}