/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * (c) $Name
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.myapplication.helpers

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.myapplication.`interface`.DatabaseIF
import com.example.myapplication.database.AES

class DatabaseHelper(private val fragmentManager: FragmentManager, private val context: Context, private val value: () -> Unit) :
    DatabaseIF {

    fun getTwoData(data: String) {
        DatabaseIF.GetData(
            getDataURL(AES.encrypt(data, "2V7fCDj7ne5D")), fragmentManager, context) { value() }.execute()
        return
    }

    private fun getDataURL(data: String): String {
        return "https://nativeonline.fvds.ru:8443/api/mysql?user=root&database=izhevskonline&password=0&query=$data"
    }

    
}