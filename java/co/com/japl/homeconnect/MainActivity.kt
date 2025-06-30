package co.com.alameda181.unidadresidencialalameda181

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import co.com.japl.homeconnect.ui.composite.navigator.NavigatorSplash
import co.com.japl.ui.theme.MaterialThemeComposeUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.actionBar?.hide()
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current.applicationContext
            MaterialThemeComposeUI {
                NavigatorSplash(
                    navController = navController,
                    modifier = Modifier.padding(0.dp),
                    context = context
                )
            }
        }
    }
}

