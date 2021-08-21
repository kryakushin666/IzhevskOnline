package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.`interface`.respObjDatabase
import com.example.myapplication.database.DatabaseHelper
import com.example.myapplication.fragments.MapsFragment.Companion.OBJECT_ID
import com.example.myapplication.fragments.MapsFragment.Companion.OBJECT_NAME
import com.example.myapplication.utilits.downloadAndInto
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * A fragment representing a list of Items.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AllInfoFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_allinfo, container, false)
        val name = arguments?.getString(OBJECT_NAME) ?: "HELLO WORLD"
        fun changeText(name: String) {
            DatabaseHelper(requireFragmentManager()) {
                activity?.runOnUiThread {
                    fragmentLayout.findViewById<TextView>(R.id.describeText).text = respObjDatabase.response[0].opisanie
                    fragmentLayout.findViewById<TextView>(R.id.locationText).text = respObjDatabase.response[0].raspolozhenie
                    fragmentLayout.findViewById<TextView>(R.id.historyText).text = respObjDatabase.response[0].historycreate
                    fragmentLayout.findViewById<TextView>(R.id.interestingFactsText).text = respObjDatabase.response[0].interestplace
                }
            }.getTwoData("SELECT * FROM `excursionsObjects` WHERE `name` = '$name'")
        }
        fragmentLayout.findViewById<TextView>(R.id.mainText).text = name

        changeText(name)


        fragmentLayout.findViewById<ImageView>(R.id.buttonback).setOnClickListener {
            //findNavController().popBackStack(R.id.navigation_notifications,true)
            findNavController().popBackStack()
        }

        return fragmentLayout

    }
}

