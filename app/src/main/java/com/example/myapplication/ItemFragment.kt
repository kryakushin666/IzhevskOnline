package com.example.myapplication

import android.os.Bundle
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

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_item, container, false)

        fragmentLayout.findViewById<ImageView>(R.id.buttons).setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
        val viewAdapter = MyAdapter(Array(15) { listOfTitle[it % listOfTitle.size] })

        fragmentLayout.findViewById<RecyclerView>(R.id.leaderboard_list).run {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // specify an viewAdapter (see also next example) Person ${it + 1}
            adapter = viewAdapter

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
                .inflate(R.layout.list_view_item, parent, false)


            return ViewHolder(itemView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.item.findViewById<TextView>(R.id.user_name_text).text = myDataset[position]

            holder.item.findViewById<ImageView>(R.id.user_avatar_image)
                .setImageResource(listOfAvatars[position % listOfAvatars.size])

            holder.item.setOnClickListener {
                val bundle = bundleOf(USERNAME_KEY to myDataset[position], USERNAME_COORDINATE to listOfCoordinate[position % listOfCoordinate.size], USERNAME_IMAGE to listOfAvatars[position % listOfAvatars.size])
                holder.item.findNavController().navigate(
                        R.id.action_itemFragment_to_Museumuser,
                        bundle)

            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size

        companion object {
            const val USERNAME_KEY = "userName"
            const val USERNAME_COORDINATE = "userCoord"
            const val USERNAME_IMAGE = "R.drawable.museum_ak"
        }
    }
}
private val listOfAvatars = listOf(
    R.drawable.museum_ak,
    R.drawable.museum_izmash,
    R.drawable.museum_kotleta
)
private var listOfTitle = listOf(
    "Музей стрелкового оружия им. М.Т. Калашникова",
    "Музей Ижмаш",
    "Kotleta bar"
)
private var listOfCoordinate = listOf(
    "56.85285289473385, 53.215664171778975",
    "56.85073186241447, 53.20672264326064",
    "56.84383886160861, 53.191198130527944"
)
