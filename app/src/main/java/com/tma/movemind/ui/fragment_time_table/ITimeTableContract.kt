package com.tma.movemind.ui.fragment_time_table

import com.tma.movemind.base.dagger.ui.ILifeCycle

interface ITimeTableContract {
    interface View : ILifeCycle.View{

    }

    interface Presenter : ILifeCycle.Presenter<View>{

    }
}