package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.activities.AdActivity
import com.example.myapplication.activities.AuthActivity
import com.example.myapplication.activities.MapsActivity
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

        coords = arguments?.getString(USERNAME_COORDINATE) ?: "HELLO S"

        fragmentLayout.findViewById<TextView>(R.id.profile_user_name).text = name
        fragmentLayout.findViewById<ImageView>(R.id.picture)
            .downloadAndInto(image)

        val buttonToMap = fragmentLayout.findViewById<ImageView>(R.id.buttontomap)

        buttonToMap.setOnClickListener {
            editData(contextCompats, "RouteToMap", "RouteToMap", "1", "putInt")
            editData(contextCompats, "USERNAME", "USERNAME_COORDINATES", coords, "putString")
            editData(contextCompats, "USERNAME", "USERNAME_NAME", name, "putString")
            val intent = Intent(contextCompats, MapsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            contextCompats.startActivity(intent)
        }

        fragmentLayout.findViewById<ImageView>(R.id.buttons).setOnClickListener {
            findNavController().popBackStack()
        }
        return fragmentLayout
    }

    companion object {
        var USERNAME_COORDINATES = "coord"
    }

    /*fun toActivity(data: String?) {
        val activity: Activity? = activity
        if (activity != null && !activity.isFinishing && activity is MapsActivity) {
            activity.fromFragmentData(data)
            Log.d("Data", "data succefull")
        }
    }*/
}

