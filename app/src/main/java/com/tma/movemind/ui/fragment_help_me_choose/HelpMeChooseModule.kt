package com.tma.movemind.ui.fragment_help_me_choose

import android.content.Context
import com.tma.movemind.base.dagger.scopes.PerFragment
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class HelpMeChooseModule {
    @Binds
    @PerFragment
    abstract fun bindView(fragment: HelpMeChooseFragment) : IHelpMeChooseContract.View

    @Module
    companion object {
        @JvmStatic
        @PerFragment
        @Provides
        fun providePresenter() : IHelpMeChooseContract.Presenter = HelpMeChoosePresenter()
    }
}