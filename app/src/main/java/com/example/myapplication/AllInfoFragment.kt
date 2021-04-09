package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MapsFragment.DemoBottomSheetFragment.Companion.OBJECT_NAME

/**
 * A fragment representing a list of Items.
 */
class AllInfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_allinfo, container, false)
        val name = arguments?.getString(OBJECT_NAME) ?: "HELLO WORLD"
        fragmentLayout.findViewById<TextView>(R.id.maintexts).text = name

        fragmentLayout.findViewById<ImageView>(R.id.buttons).setOnClickListener {
            findNavController().navigate(R.id.navigation_notifications)
        }

        return fragmentLayout

    }
}