package co.com.japl.homeconnect.model.SendEmailPqrs

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SendEmailPqrs constructor() : ViewModel() {

    var emailSend: MutableState<Boolean> = mutableStateOf(false)

    fun sendEmail(context: Context, email: String, subject: String, template: String) {
        val resolveIntent = Intent(Intent.ACTION_SENDTO)
        resolveIntent.apply {
            type = "text/plain"
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            setData(Uri.parse("mailto: "))
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, template)
        }

        val resolveInfoList = context.packageManager.queryIntentActivities(resolveIntent, PackageManager.MATCH_DEFAULT_ONLY)
        val intents = resolveInfoList.mapNotNull { resolveInfo -> context.packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName) }.toMutableList()
        if(intents.isEmpty()){
            Intent(Intent.ACTION_SENDTO).apply {
                type = "text/plain"
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, template)
                context.startActivity(this)
                emailSend.value = true
            }
        }else if(intents.size == 1){
            intents.first().apply {
                context.startActivity(this)
                emailSend.value = true
            }
        }else{
            Intent(Intent.ACTION_CHOOSER).apply {
                putExtra(Intent.EXTRA_INTENT, intents.removeAt(0))
                putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toTypedArray())
                context.startActivity(this)
                emailSend.value = true
            }
        }
    }

}