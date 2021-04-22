package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * A fragment representing a list of Items.
 */
class GunFragment : Fragment() {

    private lateinit var buttonPlay: ImageView
    private lateinit var mp: MediaPlayer
    private var mainGuided = "GunCenter"

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
            findNavController().navigate(R.id.guided_screen)
        }
        fragmentLayout.findViewById<TextView>(R.id.mapbutton).setOnClickListener {
            val bundle = bundleOf(OBJECT_GUIDED to mainGuided)
            findNavController().navigate(R.id.action_gunFragment_to_maps_screen, bundle)
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
    companion object {
        const val OBJECT_GUIDED = "objectName"
    }
}