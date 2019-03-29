package com.tma.movemind.base

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.tma.movemind.base.dagger.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {
    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    @Inject lateinit var activityInjector:DispatchingAndroidInjector<Activity>
    @Inject lateinit var fragmentInjector:DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        DaggerAppComponent.builder().application(this).build().inject(this)
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}