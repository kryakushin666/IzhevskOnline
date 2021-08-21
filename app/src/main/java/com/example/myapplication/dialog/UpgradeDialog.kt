package com.example.myapplication.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R


class UpgradeDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.shapealert);
        val view = inflater.inflate(R.layout.dialog_error_route, container, false)
        view.findViewById<TextView>(R.id.skip).setOnClickListener {
            dialog!!.hide()
        }
        view.findViewById<ImageView>(R.id.returnaction).setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(
                Uri.parse("https://nativeonline.fvds.ru:8443/izhevskonline/izhevskonline.apk"),
                "application/vnd.android.package-archive"
            )
            startActivity(intent)
        }
        view.findViewById<TextView>(R.id.maintext).text = "Вышло обновление приложения"
        view.findViewById<TextView>(R.id.returntext).text = "Обновить"
        view.findViewById<TextView>(R.id.helpourproject).text =
            "Без обновления приложение может работать не стабильно!"
        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

    }
}