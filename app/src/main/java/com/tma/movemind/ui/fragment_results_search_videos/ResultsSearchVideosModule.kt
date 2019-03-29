package com.tma.movemind.ui.fragment_results_search_videos

import com.tma.movemind.base.dagger.scopes.PerFragment
import com.tma.movemind.ui.fragment_results_search_videos.mvp.IResultsSearchVideosContract
import com.tma.movemind.ui.fragment_results_search_videos.mvp.ResultsSearchVideosPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ResultsSearchVideosModule {
    @Binds
    @PerFragment
    abstract fun bindView(fragment:ResultsSearchVideosFragment) : IResultsSearchVideosContract.View

    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerFragment
        fun providePresenter() : IResultsSearchVideosContract.Presenter = ResultsSearchVideosPresenter()
    }
}