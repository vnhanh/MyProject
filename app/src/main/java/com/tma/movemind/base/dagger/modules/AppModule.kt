package com.tma.movemind.base.dagger.modules

import android.content.Context
import com.tma.movemind.base.MyApplication
import com.tma.movemind.base.sharedpreferences.SharedPreferencesCustomized
import com.tma.movemind.base.utils.Constant.NAME_SHARED_PREFERENCES_DEFAULT
import dagger.Module
import dagger.Provides
import vn.com.tma.custom_exoplayer.PlayerManager
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(app: MyApplication) : Context = app

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferencesCustomized = SharedPreferencesCustomized(context, NAME_SHARED_PREFERENCES_DEFAULT)

    @Provides
    @Singleton
    fun provideExoPlayer(context: Context) = PlayerManager(context)
}