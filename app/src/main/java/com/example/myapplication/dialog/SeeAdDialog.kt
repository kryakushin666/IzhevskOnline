package com.example.myapplication.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.activities.AdActivity

class SeeAdDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.shapealert);
        val view = inflater.inflate(R.layout.dialog_successful_route, container, false)
        val contextCompats = requireContext().applicationContext
        view.findViewById<TextView>(R.id.skip).setOnClickListener {
            dialog!!.hide()
        }
        view.findViewById<ImageView>(R.id.seeadd).setOnClickListener {
            val intent = Intent(contextCompats, AdActivity::class.java)
            startActivity(intent)
        }
        dialog!!.setCanceledOnTouchOutside(false)
        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

    }


}