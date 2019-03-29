package com.tma.movemind.base.dagger.modules

import com.tma.movemind.base.dagger.scopes.PerFragment
import com.tma.movemind.ui.fragment_help_me_choose.HelpMeChooseFragment
import com.tma.movemind.ui.fragment_help_me_choose.HelpMeChooseModule
import com.tma.movemind.ui.fragment_now_playing.NowPlayingFragment
import com.tma.movemind.ui.fragment_now_playing.NowPlayingModule
import com.tma.movemind.ui.fragment_search_videos.SearchVideosFragment
import com.tma.movemind.ui.fragment_search_videos.SearchVideosModule
import com.tma.movemind.ui.fragment_results_search_videos.ResultsSearchVideosFragment
import com.tma.movemind.ui.fragment_results_search_videos.ResultsSearchVideosModule
import com.tma.movemind.ui.fragment_time_table.TimeTableFragment
import com.tma.movemind.ui.fragment_time_table.TimeTableModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector(modules = [NowPlayingModule::class])
    @PerFragment
    internal abstract fun contributeNowPlayingFragment() : NowPlayingFragment

    @ContributesAndroidInjector(modules = [TimeTableModule::class])
    @PerFragment
    internal abstract fun contributeTimeTableFragment() : TimeTableFragment

    @ContributesAndroidInjector(modules = [SearchVideosModule::class])
    @PerFragment
    internal abstract fun contributeSearchVideosFragment() : SearchVideosFragment

    @ContributesAndroidInjector(modules = [ResultsSearchVideosModule::class])
    @PerFragment
    internal abstract fun contributeResultsSearchVideosFragment() : ResultsSearchVideosFragment

    @ContributesAndroidInjector(modules = [HelpMeChooseModule::class])
    @PerFragment
    internal abstract fun contributeHelpMeChooseInstaller() : HelpMeChooseFragment
}