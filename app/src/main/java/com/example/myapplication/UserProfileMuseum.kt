package com.example.myapplication

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.ItemFragment.MyAdapter.Companion.USERNAME_COORDINATE
import com.example.myapplication.ItemFragment.MyAdapter.Companion.USERNAME_IMAGE
import com.example.myapplication.ItemFragment.MyAdapter.Companion.USERNAME_KEY
import java.net.URL


var coord = "my work"

/**
 * Shows a profile screen for a user, taking the name from the arguments.
 */
class UserProfileMuseum : Fragment() {

    fun getDrawable(bitmapUrl: String?): Drawable? {
        return try {
            val url = URL(bitmapUrl)
            BitmapDrawable(BitmapFactory.decodeStream(url.openConnection().getInputStream()))
        } catch (ex: Exception) {
            null
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        val name = arguments?.getString(USERNAME_KEY) ?: "HELLO WORLD"
        val image = arguments?.getInt(USERNAME_IMAGE) ?: R.drawable.museum_ak
        Log.d("dd", "image : $image")

        coord = arguments?.getString(USERNAME_COORDINATE) ?: "HELLO S"
        view.findViewById<TextView>(R.id.profile_user_name).text = name

        view.findViewById<ImageView>(R.id.picture)
                .setImageResource(image)

        val buttontomap: ImageView = view.findViewById(R.id.buttontomap)
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

