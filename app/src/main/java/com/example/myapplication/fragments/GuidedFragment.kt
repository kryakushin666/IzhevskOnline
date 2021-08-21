package com.example.myapplication.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.`interface`.respObjDatabase
import com.example.myapplication.activities.bottomNavigationView
import com.example.myapplication.database.DatabaseHelper


var guidedFragment_allCounterName: ArrayList<String> = ArrayList()
var guidedFragment_alLCounterId: ArrayList<Int> = ArrayList()
var guidedFragment_allCounterAuthor: ArrayList<String> = ArrayList()
var guidedFragment_allCounterType: ArrayList<String> = ArrayList()

class GuidedFragment : Fragment() {

    var itemCounter: Int = 0
    private var viewAdapter: MyAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_guided, container, false)

        /*fragmentLayout.findViewById<ImageView>(R.id.guncenter).setOnClickListener {
            findNavController().navigate(R.id.action_guided_screen_to_gunFragment)
        }*/
        val recyclerView = fragmentLayout.findViewById<RecyclerView>(R.id.list_excursion)
        val notFound = fragmentLayout.findViewById<TextView>(R.id.notFound)
        guidedFragment_allCounterName.clear()
        guidedFragment_alLCounterId.clear()
        guidedFragment_allCounterType.clear()
        guidedFragment_allCounterAuthor.clear()
        fragmentLayout.findViewById<ImageView>(R.id.buttonback).setOnClickListener {
            findNavController().popBackStack()
        }
        bottomNavigationView.visibility = View.INVISIBLE

        DatabaseHelper(requireFragmentManager()) {
            itemCounter = respObjDatabase.response.size
            for (i in 0 until respObjDatabase.response.size) {
                guidedFragment_allCounterName.add(respObjDatabase.response[i].name)
                guidedFragment_alLCounterId.add(respObjDatabase.response[i].id)
                guidedFragment_allCounterType.add(respObjDatabase.response[i].type)
                guidedFragment_allCounterAuthor.add(respObjDatabase.response[i].author)

            }
            viewAdapter = MyAdapter(Array(itemCounter) { guidedFragment_allCounterName[it % guidedFragment_allCounterName.size] })
            activity?.runOnUiThread {
                recyclerView.run {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)

                // specify an viewAdapter (see also next example) Person ${it + 1}
                adapter = viewAdapter
            }
                if(viewAdapter!!.itemCount != 0) {
                    recyclerView.visibility = View.VISIBLE
                    notFound.visibility = View.INVISIBLE
                }
            }
        }.getTwoData("SELECT * FROM `excursions`")
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
                .inflate(R.layout.list_view_excursion, parent, false)


            return ViewHolder(itemView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.item.findViewById<TextView>(R.id.excname).text = myDataset[position]

            /*holder.item.findViewById<ImageView>(R.id.user_avatar_image)
                .downloadAndInto(allcounterimage[position % allcountername.size])*/

            holder.item.setOnClickListener {
                val bundle = bundleOf(EXCURSION_NAME to myDataset[position], EXCURSION_ID to guidedFragment_alLCounterId[position % guidedFragment_alLCounterId.size], EXCURSION_AUTHOR to guidedFragment_allCounterAuthor[position % guidedFragment_allCounterAuthor.size])
                holder.item.findNavController().navigate(
                    R.id.action_guided_screen_to_gunFragment,
                    bundle)
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size

        companion object {
            const val EXCURSION_NAME = "excursion_name"
            const val EXCURSION_AUTHOR = "excursion_author"
            const val EXCURSION_ID = "excursion_id"
        }
    }
}