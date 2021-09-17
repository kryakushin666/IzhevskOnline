package com.example.myapplication.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R


class NoConnectionDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.shapealert);
        val view = inflater.inflate(R.layout.dialog_no_connection, container, false)
        view.findViewById<TextView>(R.id.skip).setOnClickListener {
            dialog!!.hide()
        }
        view.findViewById<CardView>(R.id.returnaction).setOnClickListener {}
        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

    }
}