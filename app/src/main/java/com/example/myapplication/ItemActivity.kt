package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

/**
 * A fragment representing a list of Items.
 */


class ItemActivity : Fragment() {

    private var idscreen: Int = -1
    private var namescreen: String = "null"
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.activity_item, container, false)
        loadData()
        voteScreen(idscreen)
        view.findViewById<ImageView>(R.id.buttons).setOnClickListener {
            view.findNavController().popBackStack()
        }
        view.findViewById<TextView>(R.id.nameoftitle).text = checkname(idscreen)
        val viewAdapter = MyAdapter(Array(15) { listOfTitle[it % listOfTitle.size] }, idscreen)

        view.findViewById<RecyclerView>(R.id.leaderboard_list).run {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // specify an viewAdapter (see also next example) Person ${it + 1}
            adapter = viewAdapter

        }
        return view
    }
    fun loadData() {
        val contextCompats = requireContext().applicationContext
        val pref = contextCompats.getSharedPreferences("NameOfScreen", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        idscreen = pref!!.getInt("idScreen", -1)
        editor?.apply()
    }
    fun checkname(id: Int): String {
        when(id) {
            0 -> {
                return "Музеи"
            }
            1 -> {
                return "Парки"
            }
            2 -> {
                return "Отели"
            }
            3 -> {
                return "Рестораны"
            }
            else -> {
                return "Перезагрузите экран"
            }
        }
    }
    private var listOfAvatars = listOf(
            R.drawable.museum_ak
    )
    private var listOfTitle = listOf(
            "Музей стрелкового оружия им. М.Т. Калашникова"
    )
    private var listOfCoordinate = listOf(
            "56.85285289473385, 53.215664171778975"
    )
    fun voteScreen(id: Int) {
        when (id) {
            0 -> {
                listOfAvatars = listOf(
                        R.drawable.museum_ak,
                        R.drawable.museum_izmash
                )
                listOfTitle = listOf(
                        "Музей стрелкового оружия им. М.Т. Калашникова",
                        "Музей Ижмаш"
                )
                listOfCoordinate = listOf(
                        "56.85285289473385, 53.215664171778975",
                        "56.85073186241447, 53.20672264326064"
                )
            }
            1 -> {
                listOfAvatars = listOf(
                        R.drawable.museum_ak,
                        R.drawable.museum_izmash,
                        R.drawable.museum_kotleta
                )
                listOfTitle = listOf(
                        "Музей стрелкового оружия им. М.Т. Калашникова",
                        "Музей Ижмаш",
                        "Kotleta bar"
                )
                listOfCoordinate = listOf(
                        "56.85285289473385, 53.215664171778975",
                        "56.85073186241447, 53.20672264326064"
                )
            }
            2 -> {
                listOfAvatars = listOf(
                        R.drawable.park_inn,
                        R.drawable.amaks,
                        R.drawable.izhotel,
                        R.drawable.bobrdol,
                        R.drawable.parkhotel,
                )
                listOfTitle = listOf(
                        "Park inn",
                        "AMAKS Центральная",
                        "ИжОтель",
                        "Бобровая Долина",
                        "Park hotel",
                )
                listOfCoordinate = listOf(
                        "56.85285289473385, 53.215664171778975",
                        "56.85073186241447, 53.20672264326064"
                )
            }
            3 -> {
                listOfAvatars = listOf(
                        R.drawable.cafe_mama_pizza,
                        R.drawable.cafe_kare,
                        R.drawable.museum_kotleta
                )
                listOfTitle = listOf(
                        "Mama Pizza",
                        "Каре",
                        "Kotleta bar"
                )
                listOfCoordinate = listOf(
                        "56.85285289473385, 53.215664171778975",
                        "56.85073186241447, 53.20672264326064"
                )
            }
        }
    }

    class MyAdapter(private val myDataset: Array<String>, idscreens: Int) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        private var idscreens = idscreens
        class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)
        private var listOfAvatars = listOf(
                R.drawable.museum_ak
        )
        private var listOfTitle = listOf(
                "Музей стрелкового оружия им. М.Т. Калашникова"
        )
        private var listOfCoordinate = listOf(
                "56.85285289473385, 53.215664171778975"
        )
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
            voteScreen(idscreens)
            holder.item.findViewById<ImageView>(R.id.user_avatar_image)
                .setImageResource(listOfAvatars[position % listOfAvatars.size])

            holder.item.setOnClickListener {
                val bundle = bundleOf(USERNAME_KEY to myDataset[position], USERNAME_COORDINATE to listOfCoordinate[position % listOfCoordinate.size], USERNAME_IMAGE to listOfAvatars[position % listOfAvatars.size])
                holder.item.findNavController().navigate(
                        R.id.action_itemFragment_to_Museumuser,
                        bundle)
            }
        }
        fun voteScreen(id: Int) {
            when (id) {
                0 -> {
                    listOfAvatars = listOf(
                            R.drawable.museum_ak,
                            R.drawable.museum_izmash
                    )
                    listOfTitle = listOf(
                            "Музей стрелкового оружия им. М.Т. Калашникова",
                            "Музей Ижмаш"
                    )
                    listOfCoordinate = listOf(
                            "56.85285289473385, 53.215664171778975",
                            "56.85073186241447, 53.20672264326064"
                    )
                }
                1 -> {
                    listOfAvatars = listOf(
                            R.drawable.museum_ak,
                            R.drawable.museum_izmash,
                            R.drawable.museum_kotleta
                    )
                    listOfTitle = listOf(
                            "Музей стрелкового оружия им. М.Т. Калашникова",
                            "Музей Ижмаш",
                            "Kotleta bar"
                    )
                    listOfCoordinate = listOf(
                            "56.85285289473385, 53.215664171778975",
                            "56.85073186241447, 53.20672264326064"
                    )
                }
                2 -> {
                    listOfAvatars = listOf(
                            R.drawable.park_inn,
                            R.drawable.amaks,
                            R.drawable.izhotel,
                            R.drawable.bobrdol,
                            R.drawable.parkhotel,
                    )
                    listOfTitle = listOf(
                            "Park inn",
                            "AMAKS Центральная",
                            "ИжОтель",
                            "Бобровая Долина",
                            "Park hotel",
                    )
                    listOfCoordinate = listOf(
                            "56.85285289473385, 53.215664171778975",
                            "56.85073186241447, 53.20672264326064"
                    )
                }
                3 -> {
                    listOfAvatars = listOf(
                            R.drawable.cafe_mama_pizza,
                            R.drawable.cafe_kare,
                            R.drawable.museum_kotleta
                    )
                    listOfTitle = listOf(
                            "Mama Pizza",
                            "Каре",
                            "Kotleta bar"
                    )
                    listOfCoordinate = listOf(
                            "56.85285289473385, 53.215664171778975",
                            "56.85073186241447, 53.20672264326064"
                    )
                }
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
