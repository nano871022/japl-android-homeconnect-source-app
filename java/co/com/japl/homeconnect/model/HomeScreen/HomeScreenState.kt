package co.com.japl.homeconnect.model.HomeScreen

import co.com.japl.homeconnect.core.model.Carousel

data class HomeScreenState(
    val carouselList:List<Carousel> = emptyList()

)
