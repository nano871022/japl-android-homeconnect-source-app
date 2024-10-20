package co.com.japl.homeconnect.module

import co.com.japl.homeconnect.core.adapter.ports.inbound.CarouselPort
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MyEntryPoint {

    fun carouselPort():CarouselPort
}