/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * (c) $Name
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.fragments.ItemFragment.MyAdapter.Companion.USERNAME_COORDINATE
import com.example.myapplication.fragments.ItemFragment.MyAdapter.Companion.USERNAME_IMAGE
import com.example.myapplication.fragments.ItemFragment.MyAdapter.Companion.USERNAME_KEY
import com.example.myapplication.utilits.downloadAndInto
import com.example.myapplication.utilits.editData

var coords = "my work"

/**
 * Shows a profile screen for a user, taking the name from the arguments.
 */
class UserMuseumFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_user_profile, container, false)
        val contextCompats = requireContext().applicationContext
        val name = arguments?.getString(USERNAME_KEY) ?: "HELLO WORLD"
        val image = arguments?.getString(USERNAME_IMAGE) ?: "tps://firebasestor.googlapis.com/v0/b/izhevskonline123.appspot.com/o/8"
        Log.d("dd", "image : $image")
        //val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        coords = arguments?.getString(USERNAME_COORDINATE) ?: "HELLO S"
        fragmentLayout.findViewById<TextView>(R.id.profile_user_name).text = name
        fragmentLayout.findViewById<ImageView>(R.id.picture)
            .downloadAndInto(image)

        val buttontomap: ImageView = fragmentLayout.findViewById(R.id.buttontomap)
        buttontomap.setOnClickListener {
            //val bundle = bundleOf(USERNAME_COORDINATES to coord, USERNAME_NAME to name)

            //toActivity("ddd")
            editData(contextCompats, "RouteToMap", "RouteToMap", "1", "putInt")
            findNavController().popBackStack()
            editData(contextCompats, "USERNAME", "USERNAME_COORDINATES", coords, "putString")
            editData(contextCompats, "USERNAME", "USERNAME_NAME", name, "putString")
            /*view.findNavController().navigate(
                    R.id.action_Museumuser_to_navigation_notifications,
                    bundle)*/

            /*Navigation.findNavController(
                    requireActivity(),
                    R.id.navigation_home
            ).navigate(R.id.action_Museumuser_to_navigation_notifications, bundle)*/
        }

        fragmentLayout.findViewById<ImageView>(R.id.buttons).setOnClickListener {
            /*finishAffinity()
            val intent = Intent(this, ItemActivity::class.java)
            startActivity(intent)*/
            findNavController().popBackStack()
        }
        return fragmentLayout
    }

    companion object {
        var USERNAME_COORDINATES = "coord"
        var USERNAME_NAME = "nane"
    }

    /*fun toActivity(data: String?) {
        val activity: Activity? = activity
        if (activity != null && !activity.isFinishing && activity is MapsActivity) {
            activity.fromFragmentData(data)
            Log.d("Data", "data succefull")
        }
    }*/
}

