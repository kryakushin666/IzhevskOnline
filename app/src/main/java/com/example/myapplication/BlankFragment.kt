package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController



class BlankFragment : Fragment() {


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
        // возвращаем макет фрагмента
        return fragmentLayout
    }
    private fun initData(id: Int)
    {
        val pref = context?.getSharedPreferences("NameOfScreen", Context.MODE_PRIVATE)
        var editor = pref?.edit()
        editor?.putInt("idScreen", id)
        editor?.apply()
        findNavController().navigate(R.id.action_navigation_home_to_itemFragment)
    }
}