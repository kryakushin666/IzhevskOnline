package com.example.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.myapplication.helpers.DatabaseHelper
import com.example.myapplication.utilits.downloadAndInto
import com.example.myapplication.utilits.editData
import com.yalantis.phoenix.PullToRefreshView
import kotlin.math.roundToInt


/**
 * A fragment representing a list of Items.
 */
var allcountername: ArrayList<String> = ArrayList()
var allcounterrating: ArrayList<String> = ArrayList()
var allcounterimage: ArrayList<String> = ArrayList()
var allcounterlatlng: ArrayList<String> = ArrayList()

class ItemFragment : Fragment() {

    var itemCounter: Int = 0
    private var idscreen: Int = 0
    private var viewAdapter: MyAdapter? = null
    private val refreshDelay = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewAdapter?.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentLayout = inflater.inflate(R.layout.activity_item, container, false)
        val contextCompats = requireContext().applicationContext
        allcountername.clear()
        allcounterrating.clear()
        allcounterimage.clear()
        allcounterlatlng.clear()
        idscreen = editData(contextCompats, "NameOfScreen", "idScreen", "-1", "getInt")!!.toInt()
        val recyclerView = fragmentLayout.findViewById<RecyclerView>(R.id.leaderboard_list)
        val notFound = fragmentLayout.findViewById<TextView>(R.id.notFound)
        bottomNavigationView.visibility = View.INVISIBLE
        fragmentLayout.findViewById<ImageView>(R.id.buttons).setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
        fragmentLayout.findViewById<TextView>(R.id.nameoftitle).text = checkname(idscreen)
        DatabaseHelper(requireFragmentManager(), requireContext().applicationContext) {
            itemCounter = respObjDatabase.response.size
            for (i in 0 until respObjDatabase.response.size) {
                allcountername.add(respObjDatabase.response[i].name)
                allcounterimage.add(respObjDatabase.response[i].logo_image)
                allcounterrating.add(respObjDatabase.response[i].rating)
                allcounterlatlng.add(respObjDatabase.response[i].latlng)
            }
            Log.d("dada", itemCounter.toString())
            viewAdapter = MyAdapter(Array(itemCounter) { allcountername[it % allcountername.size] })
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
        }.getTwoData(screenDetection(contextCompats))
        val mPullToRefreshView = fragmentLayout.findViewById<View>(R.id.pull_to_refresh) as PullToRefreshView
        mPullToRefreshView.setOnRefreshListener {
            mPullToRefreshView.postDelayed(
                { mPullToRefreshView.setRefreshing(false) }, refreshDelay.toLong()
            )
        }

        // возвращаем макет фрагмента
        return fragmentLayout

    }

    private fun screenDetection(contextCompats: Context): String {
        return when (editData(contextCompats, "NameOfScreen", "idScreen", "0", "getInt")?.toInt()) {
            0 -> return "SELECT * FROM `museum`"
            1 -> return "SELECT * FROM `park`"
            2 -> return "SELECT * FROM `hotel`"
            3 -> return "SELECT * FROM `restaurant`"
            else -> "SELECT * FROM `museum`"
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
                .inflate(R.layout.list_view_item, parent, false)


            return ViewHolder(itemView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.item.findViewById<TextView>(R.id.user_name_text).text = myDataset[position]
            holder.item.findViewById<ImageView>(R.id.user_avatar_image).downloadAndInto("tps://firebasestor.googlapis.com/v0/b/izhevskonline123.appspot.com/o/8")
            if(allcounterrating.isNotEmpty()) {
                val rating0 = holder.item.findViewById<ImageView>(R.id.rating0)
                val rating1 = holder.item.findViewById<ImageView>(R.id.rating1)
                val rating2 = holder.item.findViewById<ImageView>(R.id.rating2)
                val rating3 = holder.item.findViewById<ImageView>(R.id.rating3)
                val rating4 = holder.item.findViewById<ImageView>(R.id.rating4)
                val rating5 = holder.item.findViewById<ImageView>(R.id.rating5)
                holder.item.findViewById<TextView>(R.id.rating).text = allcounterrating[position % allcounterrating.size]
                holder.item.findViewById<TextView>(R.id.user_name_text).text = myDataset[position]
                if(allcounterrating[position % allcounterrating.size] != "") {
                    when (allcounterrating[position % allcounterrating.size].toFloat().roundToInt()) {
                        0 -> {
                            rating0.visibility = View.VISIBLE
                        }
                        1 -> {
                            rating1.visibility = View.VISIBLE
                        }
                        2 -> {
                            rating1.visibility = View.VISIBLE
                            rating2.visibility = View.VISIBLE
                        }
                        3 -> {
                            rating1.visibility = View.VISIBLE
                            rating2.visibility = View.VISIBLE
                            rating3.visibility = View.VISIBLE
                        }
                        4 -> {
                            rating1.visibility = View.VISIBLE
                            rating2.visibility = View.VISIBLE
                            rating3.visibility = View.VISIBLE
                            rating4.visibility = View.VISIBLE
                        }
                        5 -> {
                            rating1.visibility = View.VISIBLE
                            rating2.visibility = View.VISIBLE
                            rating3.visibility = View.VISIBLE
                            rating4.visibility = View.VISIBLE
                            rating5.visibility = View.VISIBLE
                        }
                    }
                }
                holder.item.findViewById<ImageView>(R.id.user_avatar_image)
                    .downloadAndInto(allcounterimage[position % allcountername.size])

                holder.item.setOnClickListener {
                    val bundle = bundleOf(USERNAME_KEY to myDataset[position], USERNAME_COORDINATE to allcounterlatlng[position % allcounterlatlng.size], USERNAME_IMAGE to allcounterimage[position % allcountername.size])
                    holder.item.findNavController().navigate(
                        R.id.action_itemFragment_to_Museumuser,
                        bundle)
                }
            }
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size

        companion object {
            const val USERNAME_KEY = "userName"
            const val USERNAME_COORDINATE = "userCoord"
            const val USERNAME_IMAGE = "R.drawable.museum_ak"
        }
    }
    private fun checkname(id: Int): String {
        when (id) {
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
}