package kitttn.voiassignment

import android.app.Application
import kitttn.voiassignment.di.components.AppComponent
import kitttn.voiassignment.di.components.DaggerAppComponent

class VoiApplication : Application(), DaggerComponentProvider {
    override fun onCreate() {
        super.onCreate()
    }

    override val component by lazy {
        DaggerAppComponent
            .builder()
            .applicationContext(this)
            .build()
    }
}

interface DaggerComponentProvider {
    val component: AppComponent
}