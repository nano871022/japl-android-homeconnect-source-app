package co.com.japl.homeconnect.model.HomeScreen

import android.app.Application
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import co.com.japl.homeconnect.module.MyEntryPoint
import co.com.japl.homeconnect.core.adapter.ports.inbound.interfaces.ICarousel
import dagger.hilt.EntryPoints
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections
import javax.inject.Inject


@RequiresApi(34)
@HiltViewModel
class HomeScreenModel @Inject constructor(private val application:Application): AndroidViewModel(application ) {

    var carouselPort:ICarousel?=EntryPoints.get(application,MyEntryPoint::class.java).carouselPort()
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()

    val isLoader:MutableState<Boolean> = mutableStateOf(true)
     val openState: MutableState<Boolean>  = mutableStateOf(false)
     val openStateName: MutableState<String> = mutableStateOf("")
     val openStateSrc: MutableIntState = mutableIntStateOf(0)
     val openStateUrl: MutableState<String> = mutableStateOf("")

    init{
        viewModelScope.launch {
            val carouselList = carouselPort?.getList() ?: Collections.emptyList()
            _uiState.value = HomeScreenState(carouselList)
            if(carouselList.isNotEmpty()){
                isLoader.value = false
            }


        }
    }

}