package com.tma.movemind.ui.fragment_results_search_videos.mvp

import com.tma.movemind.base.dagger.ui.ILifeCycle

interface IResultsSearchVideosContract {
    interface View : ILifeCycle.View{

    }

    interface Presenter : ILifeCycle.Presenter<View>{

    }
}