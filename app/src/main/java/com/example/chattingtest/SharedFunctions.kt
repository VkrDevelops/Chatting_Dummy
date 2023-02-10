package com.example.chattingtest

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences

object SharedFunctions {
    private lateinit var dialog: Dialog
    private lateinit var sharedPref: SharedPreferences

    fun initializeSharedPreference(activity: Activity) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    }
    fun addValueToSharedPref(key:String ,value : String, activity: Activity){
        sharedPref=activity.getPreferences(Context.MODE_PRIVATE)?: return
        with(sharedPref.edit()){
            putString(key,value)
            apply()
            commit()
        }
    }
    fun getValueFromSharedPref(key: String): String? {
        val default=""
        val value=sharedPref.getString(key,default)
        return value
    }
}