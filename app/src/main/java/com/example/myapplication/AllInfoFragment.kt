package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MapsFragment.DemoBottomSheetFragment.Companion.OBJECT_ID
import com.example.myapplication.MapsFragment.DemoBottomSheetFragment.Companion.OBJECT_NAME
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * A fragment representing a list of Items.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AllInfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_allinfo, container, false)
        val name = arguments?.getString(OBJECT_NAME) ?: "HELLO WORLD"
        val id = arguments?.getString(OBJECT_ID) ?: "HELLO WORLD"
        val p: Pattern = Pattern.compile("(\\d+)")
        val m: Matcher = p.matcher(id)
        var findedid: String? = null
        while(m.find())
        {
            findedid = m.group(1)
        }
        findedid!!.toInt()
        fun changeText(ids: String)
        {
            when(ids)
            {
                "m0" ->
                {
                    fragmentLayout.findViewById<TextView>(R.id.opisanieobj).text = getString(R.string.opisaniem0)
                    fragmentLayout.findViewById<TextView>(R.id.raspolozhenieobj).text = getString(R.string.raspolozheniem0)
                    fragmentLayout.findViewById<TextView>(R.id.istoriofcreate).text = getString(R.string.istoriasozdaniam0)
                    fragmentLayout.findViewById<TextView>(R.id.ineresniemesta).text = getString(R.string.interesniem0)
                }
                "m1" ->
                {
                    fragmentLayout.findViewById<TextView>(R.id.opisanieobj).text = getString(R.string.opisaniem1)
                    fragmentLayout.findViewById<TextView>(R.id.raspolozhenieobj).text = getString(R.string.raspolozheniem1)
                    fragmentLayout.findViewById<TextView>(R.id.istoriofcreate).text = getString(R.string.istoriasozdaniam1)
                    fragmentLayout.findViewById<TextView>(R.id.ineresniemesta).text = getString(R.string.interesniem1)
                }
                "m2" ->
                {
                    fragmentLayout.findViewById<TextView>(R.id.opisanieobj).text = getString(R.string.opisaniem2)
                    fragmentLayout.findViewById<TextView>(R.id.raspolozhenieobj).text = getString(R.string.raspolozheniem2)
                    fragmentLayout.findViewById<TextView>(R.id.istoriofcreate).text = getString(R.string.istoriasozdaniam2)
                    fragmentLayout.findViewById<TextView>(R.id.ineresniemesta).text = getString(R.string.interesniem2)
                }
                "m3" ->
                {
                    fragmentLayout.findViewById<TextView>(R.id.opisanieobj).text = getString(R.string.opisaniem3)
                    fragmentLayout.findViewById<TextView>(R.id.raspolozhenieobj).text = getString(R.string.raspolozheniem3)
                    fragmentLayout.findViewById<TextView>(R.id.istoriofcreate).text = getString(R.string.istoriasozdaniam3)
                    fragmentLayout.findViewById<TextView>(R.id.ineresniemesta).text = getString(R.string.interesniem3)
                }
                "m4" ->
                {
                    fragmentLayout.findViewById<TextView>(R.id.opisanieobj).text = getString(R.string.opisaniem4)
                    fragmentLayout.findViewById<TextView>(R.id.raspolozhenieobj).text = getString(R.string.raspolozheniem4)
                    fragmentLayout.findViewById<TextView>(R.id.istoriofcreate).text = getString(R.string.istoriasozdaniam4)
                    fragmentLayout.findViewById<TextView>(R.id.ineresniemesta).text = getString(R.string.interesniem4)
                }
                "m5" ->
                {
                    fragmentLayout.findViewById<TextView>(R.id.opisanieobj).text = getString(R.string.opisaniem5)
                    fragmentLayout.findViewById<TextView>(R.id.raspolozhenieobj).text = getString(R.string.raspolozheniem5)
                    fragmentLayout.findViewById<TextView>(R.id.istoriofcreate).text = getString(R.string.istoriasozdaniam5)
                    fragmentLayout.findViewById<TextView>(R.id.ineresniemesta).text = getString(R.string.interesniem5)
                }
                "m6" ->
                {
                    fragmentLayout.findViewById<TextView>(R.id.opisanieobj).text = getString(R.string.opisaniem6)
                    fragmentLayout.findViewById<TextView>(R.id.raspolozhenieobj).text = getString(R.string.raspolozheniem6)
                    fragmentLayout.findViewById<TextView>(R.id.istoriofcreate).text = getString(R.string.istoriasozdaniam6)
                    fragmentLayout.findViewById<TextView>(R.id.ineresniemesta).text = getString(R.string.interesniem6)
                }
                "m7" ->
                {
                    fragmentLayout.findViewById<TextView>(R.id.opisanieobj).text = getString(R.string.opisaniem7)
                    fragmentLayout.findViewById<TextView>(R.id.raspolozhenieobj).text = getString(R.string.raspolozheniem7)
                    fragmentLayout.findViewById<TextView>(R.id.istoriofcreate).text = getString(R.string.istoriasozdaniam7)
                    fragmentLayout.findViewById<TextView>(R.id.ineresniemesta).text = getString(R.string.interesniem7)
                }
                "m8" ->
                {
                    fragmentLayout.findViewById<TextView>(R.id.opisanieobj).text = getString(R.string.opisaniem8)
                    fragmentLayout.findViewById<TextView>(R.id.raspolozhenieobj).text = getString(R.string.raspolozheniem8)
                    fragmentLayout.findViewById<TextView>(R.id.istoriofcreate).text = getString(R.string.istoriasozdaniam8)
                    fragmentLayout.findViewById<TextView>(R.id.ineresniemesta).text = getString(R.string.interesniem8)
                }
                else -> {
                    fragmentLayout.findViewById<TextView>(R.id.opisanieobj).text = getString(R.string.notfound)
                    fragmentLayout.findViewById<TextView>(R.id.raspolozhenieobj).text = getString(R.string.notfound)
                    fragmentLayout.findViewById<TextView>(R.id.istoriofcreate).text = getString(R.string.notfound)
                    fragmentLayout.findViewById<TextView>(R.id.ineresniemesta).text = getString(R.string.notfound)
                }
            }
        }
        fragmentLayout.findViewById<TextView>(R.id.maintexts).text = name

        changeText(id)


        fragmentLayout.findViewById<ImageView>(R.id.buttons).setOnClickListener {
            //findNavController().popBackStack(R.id.navigation_notifications,true)
            findNavController().popBackStack()
        }

        return fragmentLayout

    }
}

