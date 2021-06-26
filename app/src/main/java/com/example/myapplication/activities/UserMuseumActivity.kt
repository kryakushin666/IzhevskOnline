package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.activities.ItemActivity.Companion.USERNAME_COORDINATE
import com.example.myapplication.activities.ItemActivity.Companion.USERNAME_IMAGE
import com.example.myapplication.activities.ItemActivity.Companion.USERNAME_KEY
import com.example.myapplication.utilits.initMint

var coords = "my work"

/**
 * Shows a profile screen for a user, taking the name from the arguments.
 */
class UserMuseumActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_user_profile)
        supportActionBar?.hide()
        initMint(this.application)
        val name = intent.getStringExtra(USERNAME_KEY) ?: "HELLO WORLD"
        val image = intent.getIntExtra(USERNAME_IMAGE, 0)
        Log.d("dd", "image : $image")
        //val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        coords = intent.getStringExtra(USERNAME_COORDINATE) ?: "HELLO S"
        findViewById<TextView>(R.id.profile_user_name).text = name
        findViewById<ImageView>(R.id.picture)
                .setImageResource(image)

        val buttontomap: ImageView = findViewById(R.id.buttontomap)
        buttontomap.setOnClickListener {
            //val bundle = bundleOf(USERNAME_COORDINATES to coord, USERNAME_NAME to name)

            //toActivity("ddd")
            finishAffinity()
            putData()
            val intent = Intent(this, MapsActivity::class.java)
            putDataToMap(coords, name)
            startActivity(intent)
            /*view.findNavController().navigate(
                    R.id.action_Museumuser_to_navigation_notifications,
                    bundle)*/

            /*Navigation.findNavController(
                    requireActivity(),
                    R.id.navigation_home
            ).navigate(R.id.action_Museumuser_to_navigation_notifications, bundle)*/
        }

        findViewById<ImageView>(R.id.buttons).setOnClickListener {
            /*finishAffinity()
            val intent = Intent(this, ItemActivity::class.java)
            startActivity(intent)*/
            finish()
        }
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
    private fun putData() {
        val pref = this.getSharedPreferences("RouteToMap", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putInt("RouteToMap", 1)
        editor?.apply()
    }

    private fun putDataToMap(data: String, data1: String) {
        val pref = this.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putString("USERNAME_COORDINATES", data)
        editor?.putString("USERNAME_NAME", data1)
        editor?.apply()
    }
}

