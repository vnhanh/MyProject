package com.tma.movemind.ui.fragment_results_search_videos.recyclerview

object ResultSearchedVideosGenerator {
    fun getList() : ArrayList<ResultSearchedVideo>{
        val list = ArrayList<ResultSearchedVideo>()
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())
        list.add(getItem())

        return list
    }

    private val url = "https://cohesive.net/wp-content/uploads/2016/05/video-icon.png"

    fun getItem(): ResultSearchedVideo = ResultSearchedVideo(
            "id1-241-", url, "YOGA OPEN - MORNING", instructorName = "Alan", duration = 45, collectionName = "MIND / BODY", brandName = "MIND 123"
    )
}