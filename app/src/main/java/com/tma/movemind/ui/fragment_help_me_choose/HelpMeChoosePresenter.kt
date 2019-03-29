package com.tma.movemind.ui.fragment_help_me_choose


class HelpMeChoosePresenter() : IHelpMeChooseContract.Presenter {

    private var view:IHelpMeChooseContract.View?=null

    override fun onAttach(view: IHelpMeChooseContract.View) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
    }

}