package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.utilits.editData
import com.example.myapplication.utilits.initFirebase
import com.example.myapplication.utilits.initMint
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()
        initMint(this.application)
        initFirebase()
        auth = FirebaseAuth.getInstance()
        backbutton.setOnClickListener {
            finish()
        }
        faqbutton.setOnClickListener {
            val intent = Intent(this, FAQActivity::class.java)
            startActivity(intent)
        }
        exitofaccount.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            editData(this, "AuthSuccessful", "AuthComp", "0", "putInt")
            finishAffinity()
            auth.signOut()
        }
        gototwodesign.setOnClickListener {
            if(editData(this, "BlankTwoFragment", "TwoFragment", "0", "getInt")?.toInt() == 0) {
                editData(this, "BlankTwoFragment", "TwoFragment", "1", "putInt")
            } else editData(this, "BlankTwoFragment", "TwoFragment", "0", "putInt")
            finishAffinity()
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        // Using deprecated methods makes you look way cool
        // Using deprecated methods makes you look way cool
        TapTargetSequence(this)
            .targets(
                TapTarget.forView(
                    findViewById(R.id.trainingbutton),
                    "Вот тут обучение",
                    "Советую посмотреть"
                ).dimColor(R.color.color_dockbar)
                    .targetRadius(55)
                    .textColor(R.color.colorBackground),
                TapTarget.forView(
                    findViewById(R.id.laguagebutton),
                    "А тут языки",
                    "Шаришь за английский - можешь сменить!"
                ).dimColor(R.color.color_dockbar)
                    .textColor(R.color.colorBackground),
                TapTarget.forView(
                    findViewById(R.id.faqbutton),
                    "О, а тут наша команда",
                    "Классные люди, посмотри"
                ).dimColor(R.color.color_dockbar)
                    .textColor(R.color.colorBackground)
            )
            .listener(object : TapTargetSequence.Listener {
                // This listener will tell us when interesting(tm) events happen in regards
                // to the sequence
                override fun onSequenceFinish() {
                    // Yay
                }

                override fun onSequenceStep(lastTarget: TapTarget, targetClicked: Boolean) {
                    // Perform action for the current target
                }

                override fun onSequenceCanceled(lastTarget: TapTarget) {
                    // Boo
                }
            }).start()
    }


}