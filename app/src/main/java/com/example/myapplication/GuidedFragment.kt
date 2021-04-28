package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController



class GuidedFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_guided, container, false)

        fragmentLayout.findViewById<ImageView>(R.id.guncenter).setOnClickListener {
            findNavController().navigate(R.id.action_guided_screen_to_gunFragment)
        }
        fragmentLayout.findViewById<ImageView>(R.id.buttonback).setOnClickListener {
            findNavController().popBackStack(R.id.maps_screen, false)
            //findNavController().popBackStack()
            findNavController().navigateUp()
        }
        // возвращаем макет фрагмента
        return fragmentLayout
    }
}