package co.com.japl.homeconnect.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(this.javaClass.name,"== onReceive START")
        val message = intent?.getStringExtra("Body")
        val title = intent?.getStringExtra("Title")
        if( !message.isNullOrBlank() && !title.isNullOrBlank()){
            val sharedPreferences = context?.getSharedPreferences("Notifications", Context.MODE_PRIVATE)
            sharedPreferences
                ?.edit()
                ?.putString("body",message)
                ?.putString("title",title)
                ?.apply()
        }
    }

}