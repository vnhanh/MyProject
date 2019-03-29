package com.tma.movemind.base.dagger.components

import com.tma.movemind.base.dagger.modules.ActivityModule
import com.tma.movemind.base.dagger.modules.AppModule
import com.tma.movemind.base.dagger.modules.FragmentModule
import com.tma.movemind.base.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component (modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityModule::class, FragmentModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: MyApplication)
}