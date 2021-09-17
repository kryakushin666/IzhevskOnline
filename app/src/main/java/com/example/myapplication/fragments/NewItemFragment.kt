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
import com.example.myapplication.database.SQLiteHelper
import com.example.myapplication.helpers.DatabaseHelper
import com.example.myapplication.models.DatabaseItem
import com.example.myapplication.utilits.downloadAndInto
import com.example.myapplication.utilits.editData
import com.yalantis.phoenix.PullToRefreshView
import kotlinx.android.synthetic.main.activity_item.*
import kotlin.math.roundToInt


/**
 * A fragment representing a list of Items.
 */
var newAllCounterName: ArrayList<String> = ArrayList()
var newAllCounterRating: ArrayList<String> = ArrayList()
var newAllCounterImage: ArrayList<String> = ArrayList()
var newAllCounterLatLng: ArrayList<String> = ArrayList()

var newAllCounterNameMuseum: ArrayList<String> = ArrayList()
var newAllCounterRatingMuseum: ArrayList<String> = ArrayList()
var newAllCounterImageMuseum: ArrayList<String> = ArrayList()
var newAllCounterLatLngMuseum: ArrayList<String> = ArrayList()

var newAllCounterNamePark: ArrayList<String> = ArrayList()
var newAllCounterRatingPark: ArrayList<String> = ArrayList()
var newAllCounterImagePark: ArrayList<String> = ArrayList()
var newAllCounterLatLngPark: ArrayList<String> = ArrayList()
var newAllCounterNameHotel: ArrayList<String> = ArrayList()
var newAllCounterRatingHotel: ArrayList<String> = ArrayList()
var newAllCounterImageHotel: ArrayList<String> = ArrayList()
var newAllCounterLatLngHotel: ArrayList<String> = ArrayList()

var newAllCounterNameRestaurant: ArrayList<String> = ArrayList()
var newAllCounterRatingRestaurant: ArrayList<String> = ArrayList()
var newAllCounterImageRestaurant: ArrayList<String> = ArrayList()
var newAllCounterLatLngRestaurant: ArrayList<String> = ArrayList()



class NewItemFragment : Fragment() {

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
        SQLiteHelper(requireContext(), null).createItemBD()
        idscreen = editData(contextCompats, "NameOfScreen", "idScreen", "-1", "getInt")!!.toInt()
        val recyclerView = fragmentLayout.findViewById<RecyclerView>(R.id.leaderboard_list)
        val notFound = fragmentLayout.findViewById<TextView>(R.id.notFound)
        bottomNavigationView.visibility = View.INVISIBLE
        fragmentLayout.findViewById<ImageView>(R.id.buttons).setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
        fragmentLayout.findViewById<TextView>(R.id.nameoftitle).text = checkname(idscreen)
        checkInfo(fragmentLayout)
        val mPullToRefreshView = fragmentLayout.findViewById<View>(R.id.pull_to_refresh) as PullToRefreshView
        mPullToRefreshView.setOnRefreshListener {
            mPullToRefreshView.postDelayed(
                {
                    mPullToRefreshView.setRefreshing(false)
                    val dbHandler = SQLiteHelper(requireContext(), null)
                    val cursor = dbHandler.getAllItem(ItemInfo(requireContext()).screenForName())
                    cursor!!.moveToFirst()
                    dbHandler.execSQL("DELETE FROM 'localItem'")
                    ItemInfo(requireContext()).nameDetection("name").clear()
                    ItemInfo(requireContext()).nameDetection("latlng").clear()
                    ItemInfo(requireContext()).nameDetection("rating").clear()
                    ItemInfo(requireContext()).nameDetection("image").clear()
                    updateInfo(fragmentLayout)
                }, refreshDelay.toLong()
            )
        }

        // возвращаем макет фрагмента
        return fragmentLayout

    }

    private fun checkInfo(view: View) {
        val dbHandler = SQLiteHelper(requireContext(), null)
        val cursor = dbHandler.getAllItem(ItemInfo(requireContext()).screenForName())
        cursor!!.moveToFirst()
        val count = cursor.count
        if(cursor != null && count != 0) {
                cursor.moveToFirst()
                ItemInfo(requireContext()).nameDetection("name").add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
                ItemInfo(requireContext()).nameDetection("latlng").add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
                ItemInfo(requireContext()).nameDetection("rating").add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_RATING)))
                ItemInfo(requireContext()).nameDetection("image").add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_IMAGE)))
            while (cursor.moveToNext()) {
                ItemInfo(requireContext()).nameDetection("name").add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
                ItemInfo(requireContext()).nameDetection("latlng").add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
                ItemInfo(requireContext()).nameDetection("rating").add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_RATING)))
                ItemInfo(requireContext()).nameDetection("image").add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_IMAGE)))
            }
            viewAdapter = MyAdapter(Array(cursor.count) { ItemInfo(requireContext()).nameDetection("name")[it % ItemInfo(requireContext()).nameDetection("name").size] }, view)
            activity?.runOnUiThread {
                view.findViewById<RecyclerView>(R.id.leaderboard_list).run {
                    setHasFixedSize(true)
                    adapter = viewAdapter
                }
                if(viewAdapter!!.itemCount != 0) {
                    view.findViewById<RecyclerView>(R.id.leaderboard_list).visibility = View.VISIBLE
                    view.findViewById<TextView>(R.id.notFound).visibility = View.INVISIBLE
                }
            }
        }
        else {
            DatabaseHelper(requireFragmentManager(), requireContext().applicationContext) {
                itemCounter = respObjDatabase.response.size
                for (i in 0 until respObjDatabase.response.size) {
                    SQLiteHelper(requireContext(), null).addItem(DatabaseItem(respObjDatabase.response[i].name, respObjDatabase.response[i].latlng, respObjDatabase.response[i].rating, respObjDatabase.response[i].logo_image, ItemInfo(requireContext()).screenForName()) )
                }
                Log.d("dada", itemCounter.toString())
                checkInfo(view)
            }.getTwoData(ItemInfo(requireContext()).screenDetection())
        }
    }
    private fun updateInfo(view: View) {
        DatabaseHelper(requireFragmentManager(), requireContext().applicationContext) {
            itemCounter = respObjDatabase.response.size
            for (i in 0 until respObjDatabase.response.size) {
                SQLiteHelper(requireContext(), null).addItem(DatabaseItem(respObjDatabase.response[i].name, respObjDatabase.response[i].latlng, respObjDatabase.response[i].rating, respObjDatabase.response[i].logo_image, ItemInfo(requireContext()).screenForName()) )
            }
            Log.d("dada", itemCounter.toString())
            checkInfo(view)
        }.getTwoData(ItemInfo(requireContext()).screenDetection())
    }
    class MyAdapter(private val myDataset: Array<String>, private val viewInput: View) :
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
            if(ItemInfo(viewInput.context).nameDetection("rating").isNotEmpty()) {
                val rating0 = holder.item.findViewById<ImageView>(R.id.rating0)
                val rating1 = holder.item.findViewById<ImageView>(R.id.rating1)
                val rating2 = holder.item.findViewById<ImageView>(R.id.rating2)
                val rating3 = holder.item.findViewById<ImageView>(R.id.rating3)
                val rating4 = holder.item.findViewById<ImageView>(R.id.rating4)
                val rating5 = holder.item.findViewById<ImageView>(R.id.rating5)
                holder.item.findViewById<TextView>(R.id.rating).text = ItemInfo(viewInput.context).nameDetection("rating")[position % ItemInfo(viewInput.context).nameDetection("rating").size]
                holder.item.findViewById<TextView>(R.id.user_name_text).text = myDataset[position]
                if(ItemInfo(viewInput.context).nameDetection("rating")[position % ItemInfo(viewInput.context).nameDetection("rating").size] != "") {
                    when (ItemInfo(viewInput.context).nameDetection("rating")[position % ItemInfo(viewInput.context).nameDetection("rating").size].toFloat().roundToInt()) {
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
                    .downloadAndInto(ItemInfo(viewInput.context).nameDetection("image")[position % ItemInfo(viewInput.context).nameDetection("image").size])

                holder.item.setOnClickListener {
                    val bundle = bundleOf(USERNAME_KEY to myDataset[position], USERNAME_COORDINATE to ItemInfo(viewInput.context).nameDetection("latlng")[position % ItemInfo(viewInput.context).nameDetection("latlng").size], USERNAME_IMAGE to ItemInfo(viewInput.context).nameDetection("image")[position % ItemInfo(viewInput.context).nameDetection("image").size])
                    holder.item.findNavController().navigate(
                        R.id.action_newitemFragment_to_Museumuser,
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

    internal class ItemInfo(val context: Context) {
        fun screenDetection(): String {
            return when (editData(context, "NameOfScreen", "idScreen", "0", "getInt")?.toInt()) {
                0 -> return "SELECT * FROM `museum`"
                1 -> return "SELECT * FROM `park`"
                2 -> return "SELECT * FROM `hotel`"
                3 -> return "SELECT * FROM `restaurant`"
                else -> "SELECT * FROM `museum`"
            }
        }

        fun screenForName(): String {
            return when (editData(context, "NameOfScreen", "idScreen", "0", "getInt")?.toInt()) {
                0 -> return "Museum"
                1 -> return "Park"
                2 -> return "Hotel"
                3 -> return "Restaurant"
                else -> "Museum"
            }
        }

        fun nameDetection(needTable: String): ArrayList<String> {
            when (needTable) {
                "image" -> (run {
                    return when (screenForName()) {
                        "Museum" -> {
                            newAllCounterImageMuseum
                        }
                        "Park" -> {
                            newAllCounterImagePark
                        }
                        "Hotel" -> {
                            newAllCounterImageHotel
                        }
                        "Restaurant" -> {
                            newAllCounterImageRestaurant
                        }
                        else -> {
                            newAllCounterImage
                        }
                    }
                })
                "rating" -> (run {
                    return when (screenForName()) {
                        "Museum" -> {
                            newAllCounterRatingMuseum
                        }
                        "Park" -> {
                            newAllCounterRatingPark
                        }
                        "Hotel" -> {
                            newAllCounterRatingHotel
                        }
                        "Restaurant" -> {
                            newAllCounterRatingRestaurant
                        }
                        else -> {
                            newAllCounterRating
                        }
                    }
                })
                "name" -> (run {
                    return when (screenForName()) {
                        "Museum" -> {
                            newAllCounterNameMuseum
                        }
                        "Park" -> {
                            newAllCounterNamePark
                        }
                        "Hotel" -> {
                            newAllCounterNameHotel
                        }
                        "Restaurant" -> {
                            newAllCounterNameRestaurant
                        }
                        else -> {
                            newAllCounterName
                        }
                    }
                })
                "latlng" -> (run {
                    return when (screenForName()) {
                        "Museum" -> {
                            newAllCounterLatLngMuseum
                        }
                        "Park" -> {
                            newAllCounterLatLngPark
                        }
                        "Hotel" -> {
                            newAllCounterLatLngHotel
                        }
                        "Restaurant" -> {
                            newAllCounterLatLngRestaurant
                        }
                        else -> {
                            newAllCounterLatLng
                        }
                    }
                })
                else -> {
                    return newAllCounterLatLng
                }
            }
        }
    }
}