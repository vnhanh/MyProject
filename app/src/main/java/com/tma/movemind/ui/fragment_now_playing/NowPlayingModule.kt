package com.tma.movemind.ui.fragment_now_playing

import com.tma.movemind.base.dagger.scopes.PerFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import vn.com.tma.custom_exoplayer.PlayerManager

@Module
abstract class NowPlayingModule {
    @Binds
    @PerFragment
    abstract fun bindView(fragment: NowPlayingFragment) : INowPlayingConstract.View


    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerFragment
        fun providePresenter(playerManager: PlayerManager) : INowPlayingConstract.Presenter = NowPlayingPresenter(playerManager)
    }
}