package com.example.myapplication.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.R
import com.example.myapplication.setupWithNavController
import com.example.myapplication.utilits.initFirebase
import com.example.myapplication.utilits.initMint
import com.google.android.material.bottomnavigation.BottomNavigationView

lateinit var bottomNavigationView: BottomNavigationView

class MapsActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        supportActionBar?.hide()
        initMint(this.application)
        initFirebase()
        bottomNavigationView = findViewById(R.id.bottom_nav)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
        if (loadRoute() == 1) {
            bottomNavigationView.selectedItemId = R.id.navigation_notifications
            putData()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(R.navigation.navigation_home, R.navigation.navigation_notifications, R.navigation.navigation_profile)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_container,
                intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    fun fromFragmentData(data: String?) {
        if (data != null) {
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
            bottomNavigationView.selectedItemId = R.id.navigation_notifications
            Log.d("Data", "data loaded")
        }
    }

    private fun loadRoute(): Int {
        val pref = this.getSharedPreferences("RouteToMap", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        val authComp = pref!!.getInt("RouteToMap", 0)
        editor?.apply()
        return authComp
    }

    private fun putData() {
        val pref = this.getSharedPreferences("RouteToMap", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putInt("RouteToMap", 0)
        editor?.apply()
    }
}