package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.findNavController
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_blank.view.*
import kotlinx.android.synthetic.main.fragment_item.view.*

/**
 * A fragment representing a list of Items.
 */
class GunFragment : Fragment() {

    private lateinit var button_play: ImageView
    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_gun, container, false)
        val contextCompats = requireContext().applicationContext
        fragmentLayout.findViewById<ImageView>(R.id.buttons).setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
        button_play = fragmentLayout.findViewById(R.id.button_play)
        mp = MediaPlayer.create(contextCompats, R.raw.music)
        mp.isLooping = true
        mp.setVolume(5f, 5f)
        button_play.setOnClickListener {
            playBtnClick()
        }

        // возвращаем макет фрагмента
        return fragmentLayout
    }
    fun playBtnClick() {

        if (mp.isPlaying) {
            // Stop
            mp.pause()

        } else {
            // Start
            mp.start()
        }
    }
}