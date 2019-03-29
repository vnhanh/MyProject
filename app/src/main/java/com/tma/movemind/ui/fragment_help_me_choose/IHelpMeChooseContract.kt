package com.tma.movemind.ui.fragment_help_me_choose

import com.tma.movemind.base.dagger.ui.ILifeCycle

interface IHelpMeChooseContract {
    interface View : ILifeCycle.View {

    }

    interface Presenter : ILifeCycle.Presenter<View>{

    }
}