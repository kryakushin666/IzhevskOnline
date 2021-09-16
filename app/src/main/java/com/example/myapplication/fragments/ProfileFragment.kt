package com.example.myapplication.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.`interface`.respObjFindPlace
import com.example.myapplication.`interface`.respObjPlace
import com.example.myapplication.activities.MapsActivity
import com.example.myapplication.database.SQLiteHelper
import com.example.myapplication.dialog.ErrRouteDialog
import com.example.myapplication.helpers.FindPlaceHelper
import com.example.myapplication.models.Database
import com.example.myapplication.utilits.editData
import com.google.android.gms.maps.model.MarkerOptions

var profileFragment_AllCounterName: ArrayList<String> = ArrayList()
var profileFragment_AllCounterLatLng: ArrayList<String> = ArrayList()

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
        /*val dbHandler = SQLiteHelper(requireContext(), null)
        profileFragment_AllCounterName.clear()
        profileFragment_AllCounterLatLng.clear()
        val cursor = dbHandler.getAllName()
        cursor!!.moveToFirst()
        //Log.d("SQLLITEIO", cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
        profileFragment_AllCounterName.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
        profileFragment_AllCounterLatLng.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_LATLNG)))
        //tvDisplayName.append((cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME))))
        while (cursor.moveToNext()) {
            //tvDisplayName.append((cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME))))
            profileFragment_AllCounterName.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
            profileFragment_AllCounterLatLng.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_LATLNG)))
            //tvDisplayName.append("\n")
            //Log.d("SQLLITEIO", cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
        }
        cursor.close()*/
        /*val listOfTitle = listOf(
            "Добавить маршрут"
        )*/
        //val viewAdapter = MyAdapter(Array(15) { listOfTitle[it % listOfTitle.size] }, fragmentLayout, requireActivity())

        val recyclerView = fragmentLayout.findViewById<RecyclerView>(R.id.route_lists)
        recyclerView.run {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // specify an viewAdapter (see also next example) Person ${it + 1}
            adapter = viewAdapter(context, fragmentLayout)

        }
        fragmentLayout.findViewById<CardView>(R.id.routeCreate).setOnClickListener {
            routeClick()
        }
        // возвращаем макет фрагмента
        return fragmentLayout

    }
    private fun routeClick() {
        val dbHandler = SQLiteHelper(requireContext(), null)
        val cursor = dbHandler.getAllName()
        cursor!!.moveToFirst()
        val count = cursor.count
        if(cursor != null && count != 0) {
            editData(requireContext(), "LocalExc", "waypoint", "data", "putString")
            editData(requireContext(), "RouteToMap", "RouteToMap", "1", "putInt")
            //requireActivity().finish()
            val intent = Intent(requireContext(), MapsActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            requireContext().startActivity(intent)

        } else {
            ErrRouteDialog {
                routeClick()
            }.show(requireFragmentManager(), "MyCustomFragment")
        }
    }
    private fun viewAdapter(context: Context, view: View): MyAdapter {
        val dbHandler = SQLiteHelper(context, null)
        profileFragment_AllCounterName.clear()
        profileFragment_AllCounterLatLng.clear()
        val cursor = dbHandler.getAllName()
        cursor!!.moveToFirst()
        val count = cursor.count
        if( cursor != null && count != 0 ){
            profileFragment_AllCounterName.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
            profileFragment_AllCounterLatLng.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_LATLNG)))
            while (cursor.moveToNext()) {
                profileFragment_AllCounterName.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
                profileFragment_AllCounterLatLng.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_LATLNG)))
            }
            for (g in cursor.count until 16) {
                profileFragment_AllCounterName.add("Добавить маршрут")
            }
            /* while (cursor.moveToNext()) {
                 cursor.count
                 //tvDisplayName.append((cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME))))
                 profileFragment_AllCounterName.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
                 profileFragment_AllCounterLatLng.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_LATLNG)))
                 //tvDisplayName.append("\n")
                 //Log.d("SQLLITEIO", cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
             }*/
        }
        else {
            for (g in 0 until 16) {
                profileFragment_AllCounterName.add("Добавить маршрут")
            }
        }
        cursor.close()
        return MyAdapter(Array(15) { profileFragment_AllCounterName[it % profileFragment_AllCounterName.size] }, view,
            requireActivity()
        )
    }

    class MyAdapter(private val myDataset: Array<String>, private val view: View, private val activity: Activity) :
            RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        private var m_Text = ""
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
                showDialog(holder)
            }
        }

        private fun showDialog(holder: ViewHolder) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(holder.item.context)
            builder.setTitle("Поиск места")

            // Set up the input
            val input = EditText(holder.item.context)
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.hint = "Введите название"
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("Дальше") { _, _ ->
                // Here you get get input text from the Edittext
                m_Text = input.text.toString()
                FindPlaceHelper {
                    val gps = DoubleArray(2)
                    gps[0] = respObjFindPlace.candidates[0].geometry.location.lat
                    gps[1] = respObjFindPlace.candidates[0].geometry.location.lng
                    val latlng = "${gps[0]}, ${gps[1]}"
                    SQLiteHelper(holder.item.context, null).addInfo(Database(respObjFindPlace.candidates[0].name, latlng))
                    activity.runOnUiThread {
                        view.findViewById<RecyclerView>(R.id.route_lists).adapter = viewAdapter(holder.item.context, view)
                    }
                }.getFindPlace(m_Text)
            }
            builder.setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
            builder.show()
        }

        private fun viewAdapter(context: Context, view: View): MyAdapter {
            val dbHandler = SQLiteHelper(context, null)
            profileFragment_AllCounterName.clear()
            profileFragment_AllCounterLatLng.clear()
            val cursor = dbHandler.getAllName()
            cursor!!.moveToFirst()
            val count = cursor.count
            if( cursor != null && count != 0 ) {
                profileFragment_AllCounterName.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
                profileFragment_AllCounterLatLng.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_LATLNG)))

                while (cursor.moveToNext()) {
                    profileFragment_AllCounterName.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NAME)))
                    profileFragment_AllCounterLatLng.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_LATLNG)))
                }
                for (g in cursor.count until 16) {
                    profileFragment_AllCounterName.add("Добавить маршрут")
                }
            }
            else {
                for (g in 0 until 16) {
                    profileFragment_AllCounterName.add("Добавить маршрут")
                }
            }

            cursor.close()
            return MyAdapter(Array(15) { profileFragment_AllCounterName[it % profileFragment_AllCounterName.size] }, view, activity)
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size

    }
}
