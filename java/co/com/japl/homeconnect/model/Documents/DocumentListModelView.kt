package co.com.japl.homeconnect.model.Documents

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import co.com.alameda181.unidadresidencialalameda181.R
import co.com.japl.homeconnect.utils.NetworkUtils
import co.com.japl.homeconnect.core.adapter.ports.inbound.DocumentPort
import co.japl.android.homeconnect.model.models.Document

import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@dagger.hilt.android.lifecycle.HiltViewModel
class DocumentListModelView @Inject constructor(var documentSvc: DocumentPort? ): ViewModel() {

    var preview:Boolean = false
    private val _progress = mutableFloatStateOf(0f)
    private val _loader = mutableStateOf(true)
    private val _list = mutableStateListOf<co.japl.android.homeconnect.model.models.Document>()
    val list get() = _list
    val loader get() = _loader
    val progress get() = _progress

    fun descargar(doc: co.japl.android.homeconnect.model.models.Document, context:Context){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(doc.url))
        context.startActivity(intent).also {
            Toast.makeText(context, context.getString(R.string.download_file_name, doc.name),Toast.LENGTH_SHORT).show()
        }
    }

    fun isNetworkAvailable(context: Context):Boolean{
        if(preview)return true
        return NetworkUtils.isNetworkAvailable(context)
    }

    fun main() = runBlocking {
        _progress.floatValue = 0.1f
        execution()
        _progress.floatValue = 1f
    }



    suspend fun execution(){
        _progress.floatValue = 0.2f
        documentSvc?.getDocuments()?.let {
            _progress.floatValue = 0.7f
            _list.clear()
            _list.addAll(it)
            _progress.floatValue = 0.8f
            _loader.value = false
        }
        _progress.floatValue = 0.9f
    }
}