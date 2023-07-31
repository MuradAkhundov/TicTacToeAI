package com.muradakhundov.tictactoe.preferencemanager

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(val mContext : Context) {
    private val sharedPreferences : SharedPreferences

    init {
        sharedPreferences = mContext.getSharedPreferences("TicTacToe", Context.MODE_PRIVATE)
    }

    fun setBoolean(key : String , value : Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean(key,value)
        editor.apply()
    }

    fun getBoolean(key : String) : Boolean{
        return sharedPreferences.getBoolean(key,false)
    }



}