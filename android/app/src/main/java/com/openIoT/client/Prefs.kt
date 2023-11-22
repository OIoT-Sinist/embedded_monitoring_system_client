package com.openIoT.client

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// Define a file name for the SharedPreferences
const val PREFS_FILE = "com.openIoT.client_prefs"

// Define a singleton object class that can access the SharedPreferences
object Prefs {
    // Declare a lazy-initialized property that holds the SharedPreferences instance
    private lateinit var prefs: SharedPreferences

    // Initialize the prefs property with a given context
    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)

    }

    // Define some properties that delegate to the prefs instance
    var userLogged: Boolean by prefs.boolean("user_logged", false)
    var userId: String by prefs.string("user_id", "-1")
    var userName: String by prefs.string("user_name", "default")

    // Define some extension functions that simplify the property delegation
    private fun SharedPreferences.string(key: String, defaultValue: String = "") =
        object : ReadWriteProperty<Any, String> {
            override fun getValue(thisRef: Any, property: KProperty<*>) =
                getString(key, defaultValue) ?: defaultValue

            override fun setValue(thisRef: Any, property: KProperty<*>, value: String) =
                edit().putString(key, value).apply()
        }

    private fun SharedPreferences.int(key: String, defaultValue: Int = 0) =
        object : ReadWriteProperty<Any, Int> {
            override fun getValue(thisRef: Any, property: KProperty<*>) =
                getInt(key, defaultValue)

            override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) =
                edit().putInt(key, value).apply()
        }

    private fun SharedPreferences.float(key: String, defaultValue: Float = 0f) =
        object : ReadWriteProperty<Any, Float> {
            override fun getValue(thisRef: Any, property: KProperty<*>) =
                getFloat(key, defaultValue)

            override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) =
                edit().putFloat(key, value).apply()
        }

    private fun SharedPreferences.boolean(key: String, defaultValue: Boolean = false) =
        object : ReadWriteProperty<Any, Boolean> {
            override fun getValue(thisRef: Any, property: KProperty<*>) =
                getBoolean(key, defaultValue)

            override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) =
                edit().putBoolean(key, value).apply()
        }
}
