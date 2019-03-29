package com.tma.movemind.ui.fragment_search_videos.mvp

import com.tma.movemind.base.dagger.ui.ILifeCycle
import com.tma.movemind.ui.fragment_search_videos.helper_installer.SearchProgressInstaller
import com.tma.movemind.ui.fragment_search_videos.helper_installer.SearchVideosBundle

interface ISearchVideosContract {
    interface View : ILifeCycle.View{
        fun showUIOnItemCLicked(bundle: SearchVideosBundle?)

    }

    interface Presenter : ILifeCycle.Presenter<View>{
        var installer:SearchProgressInstaller

        fun onChooseItem(typeItem: Int, value: Any?)

        fun onBackPressed()
    }
}