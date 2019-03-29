package com.tma.movemind.base.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesCustomized constructor(context: Context, name:String){
    private lateinit var preferences: SharedPreferences
    private lateinit var editor:SharedPreferences.Editor

    init {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        editor = preferences.edit()
        editor.apply()
    }

    fun putString(key:String, value:String) = apply { editor.putString(key, value)}

    fun putInt(key:String, value:Int) = apply { editor.putInt(key, value) }

    fun putLong(key:String, value:Long) = apply { editor.putLong(key, value) }

    fun putFloat(key:String, value:Float) = apply { editor.putFloat(key, value) }

    fun putBoolean(key:String, value:Boolean) = apply { editor.putBoolean(key, value) }

    fun getString(key:String, defValue: String) : String =  preferences.getString(key, defValue)

    fun getInt(key:String, defValue:Int) : Int = preferences.getInt(key, defValue)

    fun getLong(key:String, defValue: Long) : Long = preferences.getLong(key, defValue)

    fun getFloat(key:String, defValue: Float) : Float = preferences.getFloat(key, defValue)

    fun getBoolean(key:String, defValue: Boolean) : Boolean = preferences.getBoolean(key, defValue)

    fun save() = apply { editor.apply() }

    fun delete(key:String) = apply { editor.remove(key) }
}