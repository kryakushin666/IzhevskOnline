package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*


class BlankFragment : Fragment() {

    var Date = 0
    var Month = 0
    var Year = 0
    var calendar: Calendar = Calendar.getInstance()
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        }
        Date = calendar.get(Calendar.DAY_OF_MONTH)
        Month = calendar.get(Calendar.MONTH) + 1
        Year = calendar.get(Calendar.YEAR)
        val monthconverted = when(Month) {
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
        fragmentLayout.findViewById<TextView>(R.id.goodmorning).text = monthandday
        // возвращаем макет фрагмента
        return fragmentLayout
    }
    private fun initData(id: Int)
    {
        val contextCompats = requireContext().applicationContext
        val pref = context?.getSharedPreferences("NameOfScreen", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putInt("idScreen", id)
        editor?.apply()
        val intent = Intent(contextCompats, ItemActivity::class.java)
        startActivity(intent)
    }
}