package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * A fragment representing a list of Items.
 */
class GunFragment : Fragment() {

    private lateinit var buttonPlay: ImageView
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
        buttonPlay = fragmentLayout.findViewById(R.id.button_play)
        mp = MediaPlayer.create(contextCompats, R.raw.music)
        mp.isLooping = true
        mp.setVolume(5f, 5f)
        buttonPlay.setOnClickListener {
            playBtnClick()
        }

        // возвращаем макет фрагмента
        return fragmentLayout
    }
    private fun playBtnClick() {

        if (mp.isPlaying) {
            // Stop
            mp.pause()

        } else {
            // Start
            mp.start()
        }
    }
}