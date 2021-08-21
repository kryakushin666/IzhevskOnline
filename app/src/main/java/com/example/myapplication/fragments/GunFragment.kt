package com.example.myapplication.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.`interface`.respObjDatabase
import com.example.myapplication.activities.bottomNavigationView
import com.example.myapplication.database.DatabaseHelper
import com.example.myapplication.fragments.GuidedFragment.MyAdapter.Companion.EXCURSION_AUTHOR
import kotlinx.android.synthetic.main.fragment_gun.*

var gunFragment_allCounterName: ArrayList<String> = ArrayList()
var gunFragment_alLCounterId: ArrayList<Int> = ArrayList()
var gunFragment_allCounterLatLng: ArrayList<String> = ArrayList()

class GunFragment : Fragment() {

    var itemCounter: Int = 0
    private var viewAdapter: MyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_gun, container, false)
        gunFragment_allCounterName.clear()
        gunFragment_alLCounterId.clear()
        gunFragment_allCounterLatLng.clear()
        fragmentLayout.findViewById<TextView>(R.id.mainname).text = arguments?.getString(EXCURSION_NAME, "Ошибка, перезапустите приложение")
        val idExc = arguments?.getInt(EXCURSION_ID, -1)
        val authorName = arguments?.getString(EXCURSION_AUTHOR, "None")
        fragmentLayout.findViewById<TextView>(R.id.authorName).text = authorName
        fragmentLayout.findViewById<ImageView>(R.id.buttonback).setOnClickListener {
            findNavController().popBackStack()
        }
        fragmentLayout.findViewById<CardView>(R.id.routeButton).setOnClickListener {
            val bundle = bundleOf(EXCURSION_ID to idExc)
            findNavController().navigate(R.id.action_gunFragment_to_maps_screen, bundle)
        }
        bottomNavigationView.visibility = View.INVISIBLE
        if(idExc != -1) {
            DatabaseHelper(requireFragmentManager()) {
                itemCounter = respObjDatabase.response.size
                for (i in 0 until respObjDatabase.response.size) {
                    gunFragment_allCounterName.add(respObjDatabase.response[i].name)
                    gunFragment_alLCounterId.add(respObjDatabase.response[i].id)
                    gunFragment_allCounterLatLng.add(respObjDatabase.response[i].latlng)
                }
                viewAdapter = MyAdapter(Array(itemCounter) { gunFragment_allCounterName[it % gunFragment_allCounterName.size] })
                activity?.runOnUiThread {
                    fragmentLayout.findViewById<RecyclerView>(R.id.recyclerView).run {
                        setHasFixedSize(true)
                        adapter = viewAdapter
                    }
                }
            }.getTwoData("SELECT * FROM `excursionsObjects` WHERE `id` = '$idExc'")
        }
        // возвращаем макет фрагмента
        return fragmentLayout
    }

    class MyAdapter(private val myDataset: Array<String>) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)


        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): ViewHolder {
            // create a new view
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_view_text_excursion, parent, false)


            return ViewHolder(itemView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.item.findViewById<TextView>(R.id.nameObject).text = myDataset[position]

            /*holder.item.findViewById<ImageView>(R.id.user_avatar_image)
                .downloadAndInto(allcounterimage[position % allcountername.size])*/
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size
    }
    companion object {
        const val EXCURSION_NAME = "excursion_name"
        const val EXCURSION_ID = "excursion_id"
    }
}