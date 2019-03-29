package com.tma.movemind.ui.fragment_time_table

class TimeTablePresenter() : ITimeTableContract.Presenter {
    private var view:ITimeTableContract.View?=null

    override fun onAttach(view: ITimeTableContract.View) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
    }

}