/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * (c) $Name
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.myapplication.database

import androidx.fragment.app.FragmentManager
import com.example.myapplication.`interface`.DatabaseIF

class DatabaseHelper(private val fragmentManager: FragmentManager, private val value: () -> Unit): DatabaseIF {
    fun getTwoData(data: String) {
        DatabaseIF.GetData(getDataURL(AES.encrypt(data,"2V7fCDj7ne5D")), fragmentManager
        ) { value() }.execute()
        return
    }

    // Функция получения запроса на пострение маршрута
    private fun getDataURL(data: String): String {
        return "http://83.220.168.173:3000/api/mysql?user=root&database=izhevskonline&password=0&query=$data"
        //return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${dest}&language=ru&alternatives=true&key=$APIMap"
    }
}