package com.tma.movemind.ui.fragment_search_videos.helper_installer

import com.tma.movemind.base.models.*
import com.tma.movemind.ui.fragment_search_videos.children.main_options.SEARCH_OPTION

object ListGenerator {
    fun getBrands() : ArrayList<Brand>{
        val list = ArrayList<Brand>()
        list.add(Brand("1", "MOVE 123"))
        list.add(Brand("2", "MIND 123"))
        return list
    }

    fun getLevels() : ArrayList<Level>{
        val list = ArrayList<Level>()
        list.add(Level("1","Begin"))
        list.add(Level("1","Intermediate"))
        list.add(Level("1","Advanced"))
        return list
    }

    fun getInstructors() : ArrayList<Instructor>{
        val list = ArrayList<Instructor>()
        list.add(Instructor("1", "Jackie", "Warner"))
        list.add(Instructor("1", "Jackie", "Warner"))
        list.add(Instructor("1", "Jackie", "Warner"))
        list.add(Instructor("1", "Jackie", "Warner"))
        list.add(Instructor("1", "Jackie", "Warner"))
        list.add(Instructor("1", "Jackie", "Warner"))
        list.add(Instructor("1", "Jackie", "Warner"))
        list.add(Instructor("1", "Jackie", "Warner"))
        return list
    }

    fun getDurations() : ArrayList<Duration>{
        val list = ArrayList<Duration>()
        list.add(Duration("1",5))
        list.add(Duration("1",10))
        list.add(Duration("1",20))
        list.add(Duration("1",30))
        return list
    }

    fun getCollections() : ArrayList<GymCollection>{
        val list = ArrayList<GymCollection>()
        list.add(GymCollection("1", "CARDIO"))
        list.add(GymCollection("1", "FIGHT"))
        list.add(GymCollection("1", "DANCE"))
        list.add(GymCollection("1", "YOGA"))
        list.add(GymCollection("1", "PILATES"))
        list.add(GymCollection("1", "MEDITATION"))
        list.add(GymCollection("1", "YOGA"))
        list.add(GymCollection("1", "STRENGTH"))
        list.add(GymCollection("1", "STRECH"))
        return list
    }

    fun getMainOptions(): ArrayList<SEARCH_OPTION> {
        val list = ArrayList<SEARCH_OPTION>()
        list.add(SEARCH_OPTION.INSTRUCTOR)
        list.add(SEARCH_OPTION.DURATION)
        list.add(SEARCH_OPTION.LEVEL)
        list.add(SEARCH_OPTION.COLLECTION)
        return list
    }
}