package co.com.japl.homeconnect.module

import co.com.japl.db.DbHelper
import co.japl.android.homeconnect.model.interfaces.inbound.ICarousel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MyEntryPoint {

    fun dbHelper():DbHelper

    fun provideInboundICarousel(): ICarousel
}