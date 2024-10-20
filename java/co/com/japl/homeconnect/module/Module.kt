package co.com.japl.homeconnect.module

import android.app.Application
import android.content.Context
import co.com.japl.homeconnect.core.adapter.ports.inbound.CarouselPort
import co.com.japl.homeconnect.core.adapter.ports.inbound.DocumentPort
import co.com.japl.homeconnect.core.usercase.Carousel
import co.com.japl.homeconnect.core.usercase.DocumentImpl
import co.com.japl.homeconnect.core.usercase.interfaces.ICarousel
import co.com.japl.homeconnect.core.usercase.interfaces.IDocument
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface Module {

        @Binds
    fun provideInboundICarouselByImpl(inboundCarouselPort:co.com.japl.homeconnect.core.adapter.ports.inbound.CarouselPort):co.com.japl.homeconnect.core.adapter.ports.inbound.interfaces.ICarousel

    @Binds
    fun provideUserCaseICarouselImpl(userCaseCarousel:Carousel):ICarousel

    @Binds
    fun provideUserCaseDocumentImpl(svc:DocumentImpl):IDocument



}