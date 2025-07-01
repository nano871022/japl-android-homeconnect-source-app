package co.com.japl.homeconnect.module

import co.com.japl.homeconnect.core.adapter.ports.inbound.CarouselPort
import co.com.japl.homeconnect.core.adapter.ports.inbound.DocumentPort
import co.com.japl.homeconnect.core.adapter.ports.inbound.MessagePort
import co.com.japl.homeconnect.core.adapter.ports.inbound.PqrsPort
import co.com.japl.homeconnect.core.adapter.ports.outbound.GDrivePort
import co.com.japl.homeconnect.core.adapter.ports.outbound.PQRsPort
import co.com.japl.homeconnect.core.interfaces.ICarousel
import co.com.japl.homeconnect.core.usercase.CarouselImpl
import co.japl.android.homeconnect.model.interfaces.inbound.ICarousel as ICarouselInbound
import co.japl.android.homeconnect.model.interfaces.inbound.IDocument as IDocumentInbound
import co.japl.android.homeconnect.model.interfaces.inbound.IMessage as IMessageInbound
import co.japl.android.homeconnect.model.interfaces.inbound.IPQRs
import co.japl.android.homeconnect.model.interfaces.outbound.ICarousel as ICarouselOutbound
import co.japl.android.homeconnect.model.interfaces.outbound.IPQRs as IPQRsOutbound
import co.com.japl.homeconnect.core.adapter.ports.outbound.CarouselPort as CarousePortOutbound
import co.japl.android.homeconnect.model.interfaces.outbound.IMessage as IMessageOutbound
import co.com.japl.homeconnect.core.adapter.ports.outbound.MessagePort as MessagePortOutbound
import co.com.japl.interfaces.services.IMessage as IMessageService
import co.com.japl.homeconnect.core.usercase.DocumentImpl
import co.com.japl.homeconnect.core.interfaces.IDocument
import co.com.japl.homeconnect.core.interfaces.IMessage
import co.com.japl.homeconnect.core.interfaces.IPqrs
import co.com.japl.homeconnect.core.usercase.MessageImpl
import co.com.japl.homeconnect.core.usercase.PqrsImpl
import co.com.japl.services.implement.MessageLocalPort
import co.japl.android.homeconnect.model.interfaces.outbound.IGDrive
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface Module {

    @Binds
    fun provideInboundICarouselByImpl(inboundCarouselPort:CarouselPort): ICarouselInbound

    @Binds
    fun provideInboundIDocumentImpl(inboundCarouselPort:DocumentPort): IDocumentInbound

    @Binds
    fun provideInboundIMessageImpl(inboundCarouselPort:MessagePort): IMessageInbound

    @Binds
    fun provideInboundIPqrsImpl(inboundCarouselPort:PqrsPort): IPQRs

    @Binds
    fun provideUserCaseICarouselImpl(userCaseCarousel:CarouselImpl): ICarousel

    @Binds
    fun provideUserCaseDocumentImpl(svc:DocumentImpl): IDocument

    @Binds
    fun provideUserCaseMessageImpl(svc:MessageImpl): IMessage

    @Binds
    fun provideUserCasePqrsImpl(svc:PqrsImpl): IPqrs

    @Binds
    fun provideOutboundICarouselByImpl(outboundCarouselPort:CarousePortOutbound): ICarouselOutbound

    @Binds
    fun provideOutboundIGDriveImpl(outboundGDrivePort:GDrivePort): IGDrive

    @Binds
    fun provideOutboundIPqrsByImpl(outboundPqrsPort:PQRsPort):IPQRsOutbound

    @Binds
    fun provideOutboundIMessageImpl(outboundMessagePort:MessagePortOutbound): IMessageOutbound

    @Binds
    fun provideServiceIMessageSvc(svc:MessageLocalPort):IMessageService

}