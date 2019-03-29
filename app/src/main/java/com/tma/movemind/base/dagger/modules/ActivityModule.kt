package com.tma.movemind.base.dagger.modules

import android.app.Application
import com.tma.movemind.base.MyApplication
import com.tma.movemind.base.dagger.scopes.PerActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class ActivityModule {
    @Binds
    @Singleton
    abstract fun bindApplication(app: MyApplication) : Application

//    @ContributesAndroidInjector(modules = [HomeScreenModule::class])
//    @PerActivity
//    abstract fun bindMainActivity() : HomeActivity
}