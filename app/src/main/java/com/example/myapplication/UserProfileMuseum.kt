/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.ItemFragment.MyAdapter.Companion.USERNAME_COORDINATE
import com.example.myapplication.ItemFragment.MyAdapter.Companion.USERNAME_IMAGE
import com.example.myapplication.ItemFragment.MyAdapter.Companion.USERNAME_KEY


var coord = "my work"

/**
 * Shows a profile screen for a user, taking the name from the arguments.
 */
class UserProfileMuseum : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        val name = arguments?.getString(USERNAME_KEY) ?: "HELLO WORLD"
        val image = arguments?.getString(USERNAME_IMAGE) ?: "HELLO WORLD"
        Log.d("dd", "image : $image")
        coord = arguments?.getString(USERNAME_COORDINATE) ?: "HELLO S"
        view.findViewById<TextView>(R.id.profile_user_name).text = name

        /*view.findViewById<ImageView>(R.id.user_avatar_image)
                .setImageResource(image)*/

        val buttontomap: TextView = view.findViewById(R.id.buttontomap)
        buttontomap.setOnClickListener {
            val bundle = bundleOf(USERNAME_COORDINATES to coord)

            view.findNavController().navigate(
                    R.id.action_Museumuser_to_navigation_notifications,
                    bundle)

            /*Navigation.findNavController(
                    requireActivity(),
                    R.id.navigation_home
            ).navigate(R.id.action_Museumuser_to_navigation_notifications, bundle)*/
        }

        view.findViewById<ImageView>(R.id.buttons).setOnClickListener {
            findNavController().navigate(R.id.itemFragment)
        }
        return view
    }
    companion object {
        var USERNAME_COORDINATES = coord
    }
}

