package com.tma.movemind.ui.fragment_results_search_videos.mvp

class ResultsSearchVideosPresenter() : IResultsSearchVideosContract.Presenter {
    private var view:IResultsSearchVideosContract.View?=null

    override fun onAttach(view: IResultsSearchVideosContract.View) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
    }

}