package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.R
import com.example.myapplication.utilits.editData
import com.example.myapplication.utilits.initMint
import com.example.myapplication.utilits.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

lateinit var bottomNavigationView: BottomNavigationView

class MapsActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*DatabaseHelper(supportFragmentManager) {
            if(respObjDatabase.response.size != 0) {
                editData(this@MapsActivity, "AuthSuccessful", "AuthComp", "1", "putInt")
                val intent = Intent(this@MapsActivity, MapsActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
            else {
                DatabaseHelper(supportFragmentManager) {
                    editData(this@MapsActivity, "AuthSuccessful", "AuthComp", "1", "putInt")
                    val intent = Intent(this@MapsActivity, MapsActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }.getTwoData("INSERT INTO `accounts`(`name`, `lastname`, `email`, `authVK`) VALUES ('${user.firstName}','${user.lastName}','${token.email}','1')")
            }
        }.getTwoData("SELECT `email`, `authVK` FROM `accounts` WHERE `email` = '${token.email}' AND `authVK` = '1'")*/
        setContentView(R.layout.activity_maps)
        supportActionBar?.hide()
        initMint(this.application)
        bottomNavigationView = findViewById(R.id.bottom_nav)
        /*val badge = bottomNavigationView.getOrCreateBadge(R.id.navigation_home)
        badge.isVisible = true
        badge.number = 100*/

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

        if (editData(this, "RouteToMap", "RouteToMap", "0", "getInt")?.toInt() == 1) {
            bottomNavigationView.selectedItemId = R.id.navigation_notifications
            editData(this, "RouteToMap", "RouteToMap", "0", "putInt")
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

        val navGraphIds = listOf(
            R.navigation.navigation_home,
            R.navigation.navigation_notifications,
            R.navigation.navigation_profile
        )

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data) // You MUST have this line to be here
        // so ImagePicker can work with fragment mode
    }
}