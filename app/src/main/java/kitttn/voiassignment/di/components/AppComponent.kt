package kitttn.voiassignment.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import kitttn.di.ApiModule
import kitttn.voiassignment.views.MainActivity

@Component(modules = [ApiModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder
        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
}