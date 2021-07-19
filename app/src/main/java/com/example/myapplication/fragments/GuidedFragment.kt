package com.example.myapplication.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.MapsActivity
import com.example.myapplication.activities.bottomNavigationView
import com.example.myapplication.models.CommonModel
import com.example.myapplication.utilits.NODE_EXCURSION
import com.example.myapplication.utilits.REF_DATABASE_ROOT
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.DatabaseReference

lateinit var mBottomSheetGun: BottomSheetBehavior<*>

class GuidedFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FirebaseRecyclerAdapter<CommonModel, MyAdapter>
    private lateinit var mRefContacts: DatabaseReference
    private lateinit var mRef: DatabaseReference

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_guided, container, false)

        /*fragmentLayout.findViewById<ImageView>(R.id.guncenter).setOnClickListener {
            findNavController().navigate(R.id.action_guided_screen_to_gunFragment)
        }*/
        fragmentLayout.findViewById<ImageView>(R.id.buttonback).setOnClickListener {
            findNavController().popBackStack()
        }
        bottomNavigationView.visibility = View.INVISIBLE
        mBottomSheetGun =
            BottomSheetBehavior.from(fragmentLayout.findViewById(R.id.bottom_sheet_gun))

        initRecyclerView(fragmentLayout)
        // возвращаем макет фрагмента
        return fragmentLayout
    }

    private fun initRecyclerView(view: View) {
        mRecyclerView = view.findViewById(R.id.list_excursion)
        mRefContacts = REF_DATABASE_ROOT.child(NODE_EXCURSION)
        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
                .setQuery(mRefContacts, CommonModel::class.java)
                .build()

        mAdapter = object : FirebaseRecyclerAdapter<CommonModel, MyAdapter>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_view_excursion, parent, false)
                return MyAdapter(view)
            }

            override fun onBindViewHolder(holder: MyAdapter, position: Int, model: CommonModel) {
                //holder.user_name_text.text = model.name
                //holder.itemView.excname.text = model.name
                holder.itemView.findViewById<TextView>(R.id.excname).text = model.name

                //mRef = REF_DATABASE_ROOT.child(NODE_SECONDARY).child(CHILD_TYPE)
                holder.itemView.setOnClickListener {
                    val contextCompats = requireContext().applicationContext
                    view.findViewById<TextView>(R.id.mainname).text = model.name
                    val bm = view.findViewById<ImageView>(R.id.user_avatar_image)
                    val wrappedDrawable: Drawable = DrawableCompat.wrap(ContextCompat.getDrawable(requireContext(), R.drawable.rectbutton)!!)
                    DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#5D8EEF"))
                    DrawableCompat.setTint(DrawableCompat.wrap(ContextCompat.getDrawable(requireContext(), R.drawable.box_route)!!),  Color.parseColor("#5D8EEF"))
                    DrawableCompat.setTint(DrawableCompat.wrap(ContextCompat.getDrawable(requireContext(), R.drawable.ic_car)!!),  Color.parseColor("#5D8EEF"))
                    DrawableCompat.setTint(DrawableCompat.wrap(ContextCompat.getDrawable(requireContext(), R.drawable.main_box)!!),  Color.parseColor("#5D8EEF"))
                    //bm?.background = wrappedDrawable
                    finishAffinity(activity as MapsActivity)
                    val intent = Intent(contextCompats, MapsActivity::class.java)
                    startActivity(intent)
                    //bm.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorGreen))
                    Log.d("Shiza", "da-da")
                    //mBottomSheetGun.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()
    }

    class MyAdapter(view: View) : RecyclerView.ViewHolder(view) {
        //val user_name_text: TextView = view.user_name_text
        val excname: TextView = view.findViewById(R.id.excname)
    }

    override fun onPause() {
        super.onPause()
        mAdapter.stopListening()
    }
}