package co.com.japl.homeconnect.services

import android.util.Log
import androidx.core.app.JobIntentService
import co.com.japl.homeconnect.utils.DrawerRoutes
import co.japl.android.homeconnect.model.interfaces.inbound.IMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyJobIntentService: JobIntentService() {

    @Inject
    lateinit var svc: IMessage

    override fun onHandleWork(intent: android.content.Intent) {
        try {
            Log.d(this::class.java.simpleName, "== onHandleWork START")
            val message = intent.getStringExtra("body")
            val title = intent.getStringExtra("title")
            message?.let {
                svc.addMessage(it).also {
                    intent.putExtra("destinationId", DrawerRoutes.Messages.name)
                    sendBroadcast(intent)
                }
            }
        }catch(e:Exception){
            Log.e(this.javaClass.name,"onHandleWork: ",e)
        }
    }
}