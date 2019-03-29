package com.tma.movemind.ui.fragment_time_table

import com.tma.movemind.base.dagger.scopes.PerFragment
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class TimeTableModule {
    @Binds
    @PerFragment
    abstract fun bindView(fragment:TimeTableFragment) : ITimeTableContract.View

    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerFragment
        fun providePresenter() : ITimeTableContract.Presenter = TimeTablePresenter()
    }
}