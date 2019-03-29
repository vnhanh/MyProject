package com.tma.movemind.ui.fragment_search_videos.children.main_options

import com.tma.movemind.ui.fragment_search_videos.helper_installer.SEARCH_INDEX

enum class SEARCH_OPTION(val value:Int) {
    COLLECTION(SEARCH_INDEX.INDEX_COLLECTIONS), DURATION(SEARCH_INDEX.INDEX_DURATIONS), LEVEL(SEARCH_INDEX.INDEX_LEVELS),
    INSTRUCTOR(SEARCH_INDEX.INDEX_INSTRUCTORS), UNKNOWN(SEARCH_INDEX.INDEX_BRANDS - 1);

    fun getText() : String{
        when(value) {
            SEARCH_INDEX.INDEX_COLLECTIONS -> return "COLLECTION"
            SEARCH_INDEX.INDEX_DURATIONS -> return "DURATION"
            SEARCH_INDEX.INDEX_LEVELS -> return "LEVEL"
            SEARCH_INDEX.INDEX_INSTRUCTORS -> return "INSTRUCTORS"
            else -> return ""
        }
    }
}