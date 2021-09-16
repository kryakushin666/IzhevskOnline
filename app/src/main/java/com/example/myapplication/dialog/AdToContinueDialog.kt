package com.example.myapplication.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R

class AdToContinueDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.shapealert);
        val view = inflater.inflate(R.layout.dialog_ad_to_continue, container, false)
        view.findViewById<CardView>(R.id.returnaction).setOnClickListener {
            // add ad
        }
        view.findViewById<TextView>(R.id.skip).visibility = View.INVISIBLE
        view.findViewById<TextView>(R.id.helpourproject).visibility = View.INVISIBLE
        dialog!!.setCanceledOnTouchOutside(false)
        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

    }
}