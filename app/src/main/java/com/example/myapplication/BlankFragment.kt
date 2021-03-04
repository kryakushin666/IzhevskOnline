package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_blank.view.*
import kotlinx.android.synthetic.main.fragment_item.view.*

class BlankFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_blank, container, false)

        // получаем ссылку на NavController (навигационный контроллер)
        val navController = NavHostFragment.findNavController(this)

        // слушатели кнопок, которые передают адрес навигационному контроллеру
        fragmentLayout.button.setOnClickListener { navController.navigate(R.id.itemFragment) }
        fragmentLayout.button1.setOnClickListener { navController.navigate(R.id.itemFragment) }
        fragmentLayout.button2.setOnClickListener { navController.navigate(R.id.itemFragment) }
        fragmentLayout.button3.setOnClickListener { navController.navigate(R.id.itemFragment) }

        // возвращаем макет фрагмента
        return fragmentLayout
    }

}