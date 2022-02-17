package com.example.movierating.data.sharedPreferencesManager

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor() : ISharedPreferencesManager {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun putString(var1: String?, var2: String?) {
        sharedPreferences.edit().putString(var1, var2).commit()
    }

    override fun putInt(var1: String?, var2: Int) {
        sharedPreferences.edit().putInt(var1, var2).commit()
    }

    override fun putLong(var1: String?, var2: Long) {
        sharedPreferences.edit().putLong(var1, var2).commit()
    }

    override fun putFloat(var1: String?, var2: Float) {
        sharedPreferences.edit().putFloat(var1, var2).commit()
    }

    override fun putBoolean(var1: String?, var2: Boolean) {
        sharedPreferences.edit().putBoolean(var1, var2).commit()
    }

    override fun getString(var1: String?, var2: String?): String? =
        sharedPreferences.getString(var1, var2)


    override fun getInt(var1: String?, var2: Int): Int =
        sharedPreferences.getInt(var1, var2)


    override fun getLong(var1: String?, var2: Long): Long =
        sharedPreferences.getLong(var1, var2)


    override fun getFloat(var1: String?, var2: Float): Float =
        sharedPreferences.getFloat(var1, var2)


    override fun getBoolean(var1: String?, var2: Boolean): Boolean =
        sharedPreferences.getBoolean(var1, var2)


    override fun remove(var1: String?) {
        sharedPreferences.edit().remove(var1).commit()
    }

    override fun clear() {
        sharedPreferences.edit().clear().commit()
    }

    companion object {
        const val REQUEST_TOKEN = "requestToken"
        const val REQUEST_TOKEN_FOR_CREATE_SESSION = "tokenForCreateSession"
        const val SESSION_ID = "sessionId"
        const val EMPTY_FIELD = "emptyField"
    }
}