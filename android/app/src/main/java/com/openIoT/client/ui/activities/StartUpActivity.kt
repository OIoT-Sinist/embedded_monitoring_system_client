package com.openIoT.client.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.openIoT.client.Prefs
import com.openIoT.client.ui.theme.OpenIoTClientTheme

class StartUpActivity : ComponentActivity() {
    // Just for starting a new activity base on login status.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
        if (Prefs.userLogged) {
            val intent = Intent(this, LoginActivity::class.java)
            launcher.launch(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            launcher.launch(intent)
        }
    }
}