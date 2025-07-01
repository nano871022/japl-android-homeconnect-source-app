package co.com.japl.homeconnect.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.homeconnect.worker.MyWorker
import co.japl.android.homeconnect.model.interfaces.inbound.IMessage
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var svc:IMessage

    private val JOB_ID =  1001

    override fun onNewToken(token: String){
        Log.d(this.javaClass.name,"Refreshed token: $token")
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.

        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(this.javaClass.name,"=== onMessageReceived From: ${message.from}")

        // Check if message contains a notification payload.
        message.notification?.let { notif->
            notif.body?.let {
                val intent = Intent(this, MyJobIntentService::class.java)
                intent.putExtra("body",notif.body)
                    .putExtra("title",notif.title)

                val pendingIntent= PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
/*
                val data = Data.Builder()
                    .putString("body",notif.body)
                    .putString("title",notif.title)
                    .build()


                JobIntentService.enqueueWork(this,MyJobIntentService::class.java, JOB_ID, intent)

                val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
                    .setInputData(data)
                    .build()

                Log.d(this.javaClass.name,"Enqueuing work request: $workRequest")

                WorkManager.getInstance(this).enqueue(workRequest)
                */


                svc.addMessage(it)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    val channel = NotificationChannel("allUsers", "allUsers", NotificationManager.IMPORTANCE_HIGH)
                    channel.description = "Notifications from the app to allUsers"

                    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }

                val notificationBuilder = NotificationCompat.Builder(this, "allUsers")
                    .setSmallIcon(R.drawable.favicon)
                    .setContentTitle(notif.title)
                    .setContentText(notif.body)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                val notificacionManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificacionManager.notify((Math.random()*1000).toInt(), notificationBuilder.build())
            }
        }

    }


    private fun sendRegistrationToServer(token:String){
        // TODO: Implement this method to send token to your app server.
        Log.d(this.javaClass.name,"sendRegistrationToServer $token")
    }


}