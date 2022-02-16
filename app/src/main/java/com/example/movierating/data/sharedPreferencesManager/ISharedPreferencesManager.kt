package com.example.movierating.data.sharedPreferencesManager


interface ISharedPreferencesManager {

    fun putString(var1: String?, var2: String?)

    fun putInt(var1: String?, var2: Int)

    fun putLong(var1: String?, var2: Long)

    fun putFloat(var1: String?, var2: Float)

    fun putBoolean(var1: String?, var2: Boolean)

    fun getString(var1: String?, var2: String?)

    fun remove(var1: String?)

    fun clear()

}