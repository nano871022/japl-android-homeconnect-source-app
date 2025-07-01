package co.com.alameda181.unidadresidencialalameda181

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.JobIntentService.enqueueWork
import androidx.navigation.compose.rememberNavController
import co.com.japl.homeconnect.services.MyJobIntentService
import co.com.japl.homeconnect.ui.composite.navigator.NavigatorSplash
import co.com.japl.ui.theme.MaterialThemeComposeUI
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val JOB_ID = 1001
    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.actionBar?.hide()
        handleNotificationIntent(intent)
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current.applicationContext
            FirebaseMessaging.getInstance().subscribeToTopic(BuildConfig.FMC_TOPIC).addOnCompleteListener{
                task ->
                if(!task.isSuccessful){
                    Log.d(this.javaClass.name,"Failed to subscribe to topic ${BuildConfig.FMC_TOPIC}", task.exception)
                }else{
                    Log.d(this.javaClass.name,"Subscribed to topic ${BuildConfig.FMC_TOPIC}")
                }
            }
            MaterialThemeComposeUI {
                NavigatorSplash(
                    navController = navController,
                    modifier = Modifier.padding(0.dp),
                    context = context
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: Intent){
        val title = intent.getStringExtra("Title")
        val body = intent.getStringExtra("Body")
        if(!title.isNullOrBlank() && !body.isNullOrBlank()){
            val intent = Intent(this, MyJobIntentService::class.java)
            intent.putExtra("body",body)
                .putExtra("title",title)
            enqueueWork(this,MyJobIntentService::class.java, JOB_ID, intent)
        }else{
            this.getSharedPreferences("Notifications", Context.MODE_PRIVATE)?.let{
                val title = it.getString("title","")
                val body = it.getString("body","")
                if(!title.isNullOrBlank() && !body.isNullOrBlank()){
                    val intent = Intent(this, MyJobIntentService::class.java)
                    intent.putExtra("body",body)
                        .putExtra("title",title)
                    enqueueWork(this,MyJobIntentService::class.java, JOB_ID, intent)
                }
            }
        }
    }
}

