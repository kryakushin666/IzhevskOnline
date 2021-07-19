package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_profile, container, false)
        val listOfTitle = listOf(
            "Добавить маршрут"
        )
        val bm = fragmentLayout.findViewById<ImageView>(R.id.toproute)
        bm.setOnClickListener {
            //val color = ContextCompat.getDrawable(requireContext(), R.drawable.ic_excursion)
            //bmsetBackgroundColor(Color.parseColor("#5D8EEF"))
        }
        val viewAdapter = MyAdapter(Array(15) { listOfTitle[it % listOfTitle.size] }, fragmentLayout)
        val recyclerView = fragmentLayout.findViewById<RecyclerView>(R.id.route_lists)
        recyclerView.run {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // specify an viewAdapter (see also next example) Person ${it + 1}
            adapter = viewAdapter

        }
        // возвращаем макет фрагмента
        return fragmentLayout

    }

    class MyAdapter(private val myDataset: Array<String>, private val view: View) :
            RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        private var counter: Int = 0
        private var relativheight = 255
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): ViewHolder {
            // create a new view
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_view_route, parent, false)


            return ViewHolder(itemView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.item.findViewById<TextView>(R.id.addtoroute).text = myDataset[position]
            holder.item.setOnClickListener {
                /*val bundle = bundleOf(USERNAME_KEY to myDataset[position], USERNAME_COORDINATE to listOfCoordinate[position % listOfCoordinate.size], USERNAME_IMAGE to listOfAvatars[position % listOfAvatars.size])
                holder.item.findNavController().navigate(
                        R.id.action_itemFragment_to_Museumuser,
                        bundle)*/
                Log.d("help", position.toString())

            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size

    }
}
