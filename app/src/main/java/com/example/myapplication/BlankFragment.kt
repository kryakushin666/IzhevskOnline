package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController



class BlankFragment : Fragment() {

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
            findNavController().navigate(R.id.action_navigation_home_to_itemFragment)
        }
        fragmentLayout.findViewById<ImageView>(R.id.button1).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_parksFragment)
        }
        fragmentLayout.findViewById<ImageView>(R.id.button2).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_hotelsFragment)
        }
        fragmentLayout.findViewById<ImageView>(R.id.button3).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_cafeFragment)
        }
        fragmentLayout.findViewById<ImageView>(R.id.button4).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_gunFragment)
        }
        // возвращаем макет фрагмента
        return fragmentLayout
    }
}