package com.tma.movemind.ui.fragment_search_videos

import com.tma.movemind.base.dagger.scopes.PerFragment
import com.tma.movemind.ui.fragment_search_videos.mvp.ISearchVideosContract
import com.tma.movemind.ui.fragment_search_videos.mvp.SearchVideosPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class SearchVideosModule {
    @Binds
    @PerFragment
    abstract fun bindView(fragment:SearchVideosFragment) : ISearchVideosContract.View

    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerFragment
        fun providePresenter() : ISearchVideosContract.Presenter = SearchVideosPresenter()
    }
}