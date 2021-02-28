package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_blank.view.*
import kotlinx.android.synthetic.main.fragment_item.view.*

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

        // получаем ссылку на NavController (навигационный контроллер)
        val navController = NavHostFragment.findNavController(this)

        // слушатели кнопок, которые передают адрес навигационному контроллеру
        fragmentLayout.buttons.setOnClickListener { navController.navigate(R.id.navigation_home) }

        // возвращаем макет фрагмента
        return fragmentLayout
    }
}