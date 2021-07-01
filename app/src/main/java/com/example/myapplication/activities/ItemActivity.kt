package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.CommonModel
import com.example.myapplication.utilits.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.yalantis.phoenix.PullToRefreshView
import kotlinx.android.synthetic.main.activity_item.*
import kotlinx.android.synthetic.main.list_view_item.view.*


/**
 * A fragment representing a list of Items.
 */


class ItemActivity : AppCompatActivity() {

    private var idscreen: Int = -1
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FirebaseRecyclerAdapter<CommonModel, MyAdapter>
    private lateinit var mRefContacts: DatabaseReference
    private lateinit var mRef: DatabaseReference
    private var namescreen: String = "null"
    private val REFRESH_DELAY = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        supportActionBar?.hide()
        initMint(this.application)
        initFirebase()
        loadData()
        REF_STORAGE_ROOT.child(FOLDER_OBJECT_IMAGE).downloadUrl.addOnSuccessListener {

        }
        findViewById<ImageView>(R.id.buttons).setOnClickListener {
            finish()
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        findViewById<TextView>(R.id.nameoftitle).text = checkname(idscreen)
        initRecyclerView()
        val mPullToRefreshView = findViewById<View>(R.id.pull_to_refresh) as PullToRefreshView
        mPullToRefreshView.setOnRefreshListener {
            mPullToRefreshView.postDelayed(
                { mPullToRefreshView.setRefreshing(false) }, REFRESH_DELAY.toLong()
            )
        }
    }

    private fun initRecyclerView() {
        mRecyclerView = leaderboard_list
        when (idscreen) {
            0 -> {
                mRefContacts = REF_DATABASE_ROOT.child(NODE_SECONDARY).child(CHILD_MUSEUM)
            }
            1 -> {
                mRefContacts = REF_DATABASE_ROOT.child(NODE_SECONDARY).child(CHILD_PARK)
            }
            2 -> {
                mRefContacts = REF_DATABASE_ROOT.child(NODE_SECONDARY).child(CHILD_HOTELS)
            }
            3 -> {
                mRefContacts = REF_DATABASE_ROOT.child(NODE_SECONDARY).child(CHILD_REST)
            }
            else -> {
                mRefContacts = REF_DATABASE_ROOT.child(NODE_SECONDARY).child(CHILD_MUSEUM)
            }
        }
        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
            .setQuery(mRefContacts, CommonModel::class.java)
            .build()

        mAdapter = object : FirebaseRecyclerAdapter<CommonModel, MyAdapter>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.list_view_item,
                    parent,
                    false
                )
                return MyAdapter(view)
            }

            override fun onBindViewHolder(holder: MyAdapter, position: Int, model: CommonModel) {
                holder.user_name_text.text = model.name


                //mRef = REF_DATABASE_ROOT.child(NODE_SECONDARY).child(CHILD_TYPE)
                holder.itemView.setOnClickListener {
                    val intent = Intent(holder.itemView.context, UserMuseumActivity::class.java)
                    intent.putExtra(USERNAME_KEY, model.name)
                    intent.putExtra(USERNAME_COORDINATE, model.latlng)
                    //intent.putExtra(USERNAME_IMAGE, listOfAvatars[position % listOfAvatars.size])
                    holder.itemView.context.startActivity(intent)
                }

            }

        }
        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()
    }

    class MyAdapter(view: View) : RecyclerView.ViewHolder(view) {
        val user_name_text: TextView = view.user_name_text
    }

    override fun onResume() {
        super.onResume()
        mAdapter.startListening()
    }

    override fun onPause() {
        super.onPause()
        mAdapter.stopListening()
    }

    private fun loadData() {
        val pref = this.getSharedPreferences("NameOfScreen", Context.MODE_PRIVATE)
        val editor = pref?.edit()
        idscreen = pref!!.getInt("idScreen", -1)
        editor?.apply()
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

    companion object {
        const val USERNAME_KEY = "userName"
        const val USERNAME_COORDINATE = "userCoord"
        const val USERNAME_IMAGE = "R.drawable.museum_ak"
    }
}
