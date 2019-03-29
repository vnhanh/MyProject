package com.tma.movemind.ui.fragment_search_videos.mvp

import android.util.Log
import com.tma.movemind.base.models.*
import com.tma.movemind.base.utils.Constant.TAG_LOG
import com.tma.movemind.ui.fragment_search_videos.helper_installer.SEARCH_INDEX
import com.tma.movemind.ui.fragment_search_videos.helper_installer.SearchProgressInstaller
import com.tma.movemind.ui.fragment_search_videos.helper_installer.SearchVideosBundle

class SearchVideosPresenter() : ISearchVideosContract.Presenter {
    private var view:ISearchVideosContract.View?=null
    override lateinit var installer: SearchProgressInstaller

    // vars | choosed items
    var mainOption:Int?=null
    var brand: Brand?=null
    var gymCollection: GymCollection?=null
    var duration: Duration?=null
    var instructor: Instructor?=null
    var level: Level?=null

    var nextChooseIndex = SEARCH_INDEX.INDEX_BRANDS

    override fun onChooseItem(typeItem: Int, value: Any?) {
        if(value == null) return

        when(typeItem){
            SEARCH_INDEX.INDEX_BRANDS -> {
                brand = value as Brand
                nextChooseIndex = SEARCH_INDEX.INDEX_MAIN_OPTIONS
            }
            SEARCH_INDEX.INDEX_MAIN_OPTIONS -> {
                /*COLLECTION, DURATION, INSTRUCTOR, LEVEL*/
                mainOption = (value as Int).also { it ->
                    nextChooseIndex = it
                }
            }
            SEARCH_INDEX.INDEX_COLLECTIONS -> {
                gymCollection = value as GymCollection
                nextChooseIndex++
            }
            SEARCH_INDEX.INDEX_DURATIONS -> {
                duration = value as Duration
                nextChooseIndex++
            }
            SEARCH_INDEX.INDEX_INSTRUCTORS -> {
                instructor = value as Instructor
                nextChooseIndex++
            }
            SEARCH_INDEX.INDEX_LEVELS -> {
                level = value as Level
                nextChooseIndex++
            }
            else -> return
        }

        showUIOnItemClicked()
    }

    private fun showUIOnItemClicked() {
        val title2Buffer = getTitle2BufferOnOptionChoosed()
        val bundle = getBundleOnOptionChoosed()
        showUIOnItemClicked(bundle, title2Buffer)
    }

    private fun getBundleOnOptionChoosed(): SearchVideosBundle? = installer.getBundle(nextChooseIndex)

    private fun getTitle2BufferOnOptionChoosed(): String? {
        when(nextChooseIndex){
            SEARCH_INDEX.INDEX_BRANDS, SEARCH_INDEX.INDEX_MAIN_OPTIONS,
                SEARCH_INDEX.INDEX_DURATIONS, SEARCH_INDEX.INDEX_INSTRUCTORS,
                SEARCH_INDEX.INDEX_LEVELS
                -> return null

            SEARCH_INDEX.INDEX_COLLECTIONS -> return brand?.name
            in SEARCH_INDEX.INDEX_COLLECTIONS+1..(SEARCH_INDEX.INDEX_COLLECTIONS*2-1) -> return gymCollection?.name
            in SEARCH_INDEX.INDEX_DURATIONS+1..(SEARCH_INDEX.INDEX_DURATIONS*2-1) -> return duration?.timeNumber.toString()
            in SEARCH_INDEX.INDEX_LEVELS+1..(SEARCH_INDEX.INDEX_LEVELS*2-1) -> return level?.name
            in SEARCH_INDEX.INDEX_INSTRUCTORS+1..(SEARCH_INDEX.INDEX_INSTRUCTORS*2-1) -> return instructor?.fullName

            else -> return null
        }
    }

    private fun showUIOnItemClicked(bundle:SearchVideosBundle?, title2Buffer:String?=null){
        view?.also {view ->
            bundle?.title2Buffer = title2Buffer
            view.showUIOnItemCLicked(bundle)
        }
    }

    override fun onAttach(view: ISearchVideosContract.View) {
        this.view = view
        showUIOnItemClicked()
    }

    override fun onDetach() {
        this.view = null
    }

    override fun onBackPressed() {
        when(nextChooseIndex){
            SEARCH_INDEX.INDEX_BRANDS, SEARCH_INDEX.INDEX_MAIN_OPTIONS -> {}

            else -> {
                showsBrandOptions()
            }
        }
    }

    private fun showsBrandOptions() {
        nextChooseIndex = SEARCH_INDEX.INDEX_BRANDS
        showUIOnItemClicked()
    }

}