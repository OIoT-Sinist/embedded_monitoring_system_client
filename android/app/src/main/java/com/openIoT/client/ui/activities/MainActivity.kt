package com.openIoT.client.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

lateinit var mainActivityViewModel: MainActivityViewModel;
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        mainActivityViewModel = setViewModel(this)
        setContent{
            MainActivityView(mainActivityViewModel)
        }
    }
}