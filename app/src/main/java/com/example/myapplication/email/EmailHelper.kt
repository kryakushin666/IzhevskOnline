package com.example.myapplication.email

import android.content.Context
import com.example.myapplication.`interface`.EmailIF
import com.example.myapplication.`interface`.Weather

class EmailHelper: Weather {

    private fun random(n: Int) = (Math.random() * n).toInt()
    private fun random(from: Int, to: Int) = (Math.random() * (to - from) + from).toInt()
    private fun random(pair: Pair<Int, Int>) = random(pair.first, pair.second)

    fun setVerifyCode(contextCompats: Context, email: String): String {
        val code: String = generateCode()
        EmailIF.EmailTask(getEmailURL(email, code), contextCompats).execute()
        return code
    }

    private fun getEmailURL(email: String, code: String): String {
        return "http://83.220.168.173:3000/api/email?name=IzhevskOnline <sender@izhevskonline.ru>&email=$email&title=Подтверждение адреса электронной почты для регистрации в приложении Izhevsk Online&code=$code&example=0"
    }
    private fun generateCode(): String {
        //return Random().nextInt(100000..999999).toString()
        return random(100000 to 999999).toString()
    }
}