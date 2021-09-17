package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.`interface`.respObjPlace
import com.example.myapplication.activities.bottomNavigationView
import com.example.myapplication.helpers.PlaceHelper
import com.example.myapplication.utilits.downloadAndInto


var LocationCheckFragment_allCounterName: ArrayList<String> = ArrayList()
var LocationCheckFragment_alLCounterRating: ArrayList<Double> = ArrayList()
var LocationCheckFragment_allCounterLatLng: ArrayList<String> = ArrayList()
var LocationCheckFragment_allCounterIcon: ArrayList<String> = ArrayList()
var LocationCheckFragment_allCounterOpenHour: ArrayList<Boolean> = ArrayList()

class LocationCheckFragment : Fragment() {

    var itemCounter: Int = 0
    private var viewAdapter: MyAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_locationcheck, container, false)
        val contextCompats = requireContext().applicationContext
        val recyclerView = fragmentLayout.findViewById<RecyclerView>(R.id.list_location_nearby)
        LocationCheckFragment_allCounterName.clear()
        LocationCheckFragment_alLCounterRating.clear()
        LocationCheckFragment_allCounterLatLng.clear()
        LocationCheckFragment_allCounterIcon.clear()
        LocationCheckFragment_allCounterOpenHour.clear()
        fragmentLayout.findViewById<ImageView>(R.id.buttonback).setOnClickListener {
            findNavController().popBackStack()
        }
        bottomNavigationView.visibility = View.INVISIBLE

        PlaceHelper(requireFragmentManager(), {
            itemCounter = respObjPlace.results.size
            if(itemCounter != 0) {
                for (i in 0 until itemCounter) {
                    LocationCheckFragment_allCounterName.add(respObjPlace.results[i].name)
                    val gps = DoubleArray(2)
                    gps[0] = respObjPlace.results[i].geometry.location.lat
                    gps[1] = respObjPlace.results[i].geometry.location.lng
                    LocationCheckFragment_allCounterLatLng.add("${gps[0]}, ${gps[1]}")
                    LocationCheckFragment_alLCounterRating.add(respObjPlace.results[i].rating)
                    LocationCheckFragment_allCounterIcon.add(respObjPlace.results[i].icon)
                    LocationCheckFragment_allCounterOpenHour.add(respObjPlace.results[i].opening_hours.open_now)
                }
                viewAdapter =
                    MyAdapter(Array(itemCounter) { LocationCheckFragment_allCounterName[it % LocationCheckFragment_allCounterName.size] })
                activity?.runOnUiThread {
                    recyclerView.run {
                        setHasFixedSize(true)
                        adapter = viewAdapter
                    }
                    if(viewAdapter!!.itemCount != 0) {
                        recyclerView.visibility = View.VISIBLE
                        fragmentLayout.findViewById<TextView>(R.id.notFound).visibility = View.INVISIBLE
                    }
                }
            }
        }, requireActivity(), fragmentLayout).getPlace(arguments?.getString("UserLocation", "0")!!)
            // возвращаем макет фрагмента
            return fragmentLayout
        }
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
                .inflate(R.layout.list_view_check, parent, false)


            return ViewHolder(itemView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.item.findViewById<TextView>(R.id.user_name_text).text = myDataset[position]

            holder.item.findViewById<ImageView>(R.id.user_avatar_image)
                .downloadAndInto(LocationCheckFragment_allCounterIcon[position % LocationCheckFragment_allCounterIcon.size])

            holder.item.findViewById<RatingBar>(R.id.listitemrating).rating =
                LocationCheckFragment_alLCounterRating[position % LocationCheckFragment_alLCounterRating.size].toFloat()
            holder.item.findViewById<TextView>(R.id.rating).text =
                LocationCheckFragment_alLCounterRating[position % LocationCheckFragment_alLCounterRating.size].toString()
            if(LocationCheckFragment_allCounterOpenHour[position % LocationCheckFragment_allCounterOpenHour.size]) {
                holder.item.findViewById<TextView>(R.id.openOrClose).text = "Открыто"
            } else holder.item.findViewById<TextView>(R.id.openOrClose).text = "Закрыто"

            holder.item.setOnClickListener {
                val bundle = bundleOf(USERNAME_KEY to myDataset[position], USERNAME_COORDINATE to LocationCheckFragment_allCounterLatLng[position % LocationCheckFragment_allCounterLatLng.size], USERNAME_IMAGE to LocationCheckFragment_allCounterIcon[position % LocationCheckFragment_allCounterIcon.size])
                holder.item.findNavController().navigate(
                    R.id.action_checkloc_screen_to_Museumuser,
                    bundle)
            }

            /*holder.item.setOnClickListener {
                val bundle = bundleOf(EXCURSION_NAME to myDataset[position])
                holder.item.findNavController().navigate(
                    R.id.action_guided_screen_to_gunFragment,
                    bundle)
            }*/
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size

        companion object {
            const val EXCURSION_NAME = "excursion_name"
            const val EXCURSION_AUTHOR = "excursion_author"
            const val EXCURSION_ID = "excursion_id"
            const val USERNAME_KEY = "userName"
            const val USERNAME_COORDINATE = "userCoord"
            const val USERNAME_IMAGE = "R.drawable.museum_ak"
        }
    }