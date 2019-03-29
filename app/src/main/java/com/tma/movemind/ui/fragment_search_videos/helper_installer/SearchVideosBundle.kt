package com.tma.movemind.ui.fragment_search_videos.helper_installer

import android.support.v7.widget.RecyclerView


class SearchVideosBundle
        (val title1ResId:String,
         var title2:String,
         val titleBackButton:String,
         var adapter:Any, val alignRecyclerViewMode:Int /*value from Gravity class such as Gravity.CENTER_VERTICALL*/,
         val columnSpan:Int, val dividerDecoration: RecyclerView.ItemDecoration?,
         var title2Buffer:String?=null)