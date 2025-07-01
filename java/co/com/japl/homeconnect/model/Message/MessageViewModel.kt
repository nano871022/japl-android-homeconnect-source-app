package co.com.japl.homeconnect.model.Message

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import co.japl.android.homeconnect.model.interfaces.inbound.IMessage
import co.japl.android.homeconnect.model.models.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject  constructor(private val svc:IMessage?) : ViewModel() {

    private val listed = mutableListOf<Message>()
    val list get() = listed
    val loading = mutableStateOf(true)
    val progress = mutableFloatStateOf(0f)

    fun main() = runBlocking {
        execute()
    }

    private suspend fun execute(){
        progress.value = 0.3f
        svc?.getMessage()?.let {
            progress.value = 0.5f
            listed.clear()
            listed.addAll(it)
            progress.value = 0.7f
        }
        loading.value = false
        progress.value = 1f
    }
}