package com.tma.movemind.ui.fragment_search_videos.helper_installer

import android.content.Context
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import com.tma.movemind.R
import com.tma.movemind.base.customs.ItemDecorationGridVertical
import com.tma.movemind.base.utils.StringUtil
import com.tma.movemind.ui.fragment_search_videos.children.brand.ChoosedBrandsAdapter
import com.tma.movemind.ui.fragment_search_videos.children.collection.ChoosedCollectionsAdapter
import com.tma.movemind.ui.fragment_search_videos.children.duration.ChoosedDurationsAdapter
import com.tma.movemind.ui.fragment_search_videos.children.instructor.ChoosedInstructorsAdapter
import com.tma.movemind.ui.fragment_search_videos.children.level.ChoosedLevelsAdapter
import com.tma.movemind.ui.fragment_search_videos.children.main_options.SearchVideosOptionsAdapter
import java.lang.ref.WeakReference

class SearchProgressInstaller(context: Context?) {
    private val map:HashMap<Int, SearchVideosBundle> = HashMap()
    private var weakContext:WeakReference<Context?>
    private val context:Context?
        get() = weakContext.get()

    var clickItemMainOption: SearchVideosOptionsAdapter.OnItemClickListener?=null
    var clickItemChoosedBrand: ChoosedBrandsAdapter.OnItemClickListener?=null
    var clickItemChoosedCollection: ChoosedCollectionsAdapter.OnItemClickListener?=null
    var clickItemChoosedDuration: ChoosedDurationsAdapter.OnItemClickListener?=null
    var clickItemChoosedInstructor: ChoosedInstructorsAdapter.OnItemClickListener?=null
    var clickItemChoosedLevel: ChoosedLevelsAdapter.OnItemClickListener?=null

    init {
        weakContext = WeakReference(context)
    }

    fun init() {
        val context = context ?: return

        putBundle(
                index = SEARCH_INDEX.INDEX_MAIN_OPTIONS,
                title1 = StringUtil.getString(context, R.string.title_search_video),
                title2 = "",
                titleBackButton = "",
                adapter = getMainOptionsAdapter(),
                alignRecyclerViewMode = Gravity.CENTER_VERTICAL,
                columnSpan = 2,
                dividerDecoration = null)

        putBundle(
                index = SEARCH_INDEX.INDEX_BRANDS,
                title1 = StringUtil.getString(context, R.string.title_collection),
                title2 = StringUtil.getString(context, R.string.title_choose_brand),
                titleBackButton = "",
                adapter = getBrandsAdapter(),
                alignRecyclerViewMode = Gravity.CENTER_VERTICAL,
                columnSpan = 2,
                dividerDecoration = null)

        putBundle(
                index = SEARCH_INDEX.INDEX_COLLECTIONS,
                title1 = StringUtil.getString(context, R.string.title_collection),
                title2 = StringUtil.getString(context, R.string.title_choose_collection_of_),
                titleBackButton = StringUtil.getString(context, R.string.btn_title_back),
                adapter = getCollectionsAdapter(),
                alignRecyclerViewMode = Gravity.FILL_VERTICAL,
                columnSpan = 3,
                dividerDecoration = getGridDivier(3))

        putBundle(
                index = SEARCH_INDEX.INDEX_DURATIONS_OF_COLLECTION,
                title1 = StringUtil.getString(context, R.string.title_collection),
                title2 = StringUtil.getString(context, R.string.title_choose_a_duration_of_collection),
                titleBackButton = StringUtil.getString(context, R.string.btn_title_back),
                adapter = getDurationsAdapter(),
                alignRecyclerViewMode = Gravity.FILL_VERTICAL,
                columnSpan = 3,
                dividerDecoration = getGridDivier(3))

        putBundle(
                index = SEARCH_INDEX.INDEX_INSTRUCTORS_OF_COLLECTION,
                title1 = StringUtil.getString(context, R.string.title_collection),
                title2 = StringUtil.getString(context, R.string.title_choose_instructor_of_collection),
                titleBackButton = StringUtil.getString(context, R.string.btn_title_back),
                adapter = getInstructorsAdapter(),
                alignRecyclerViewMode = Gravity.FILL_VERTICAL,
                columnSpan = 3,
                dividerDecoration = getGridDivier(3))

        putBundle(
                index = SEARCH_INDEX.INDEX_LEVELS_OF_COLLECTION,
                title1 = StringUtil.getString(context, R.string.title_collection),
                title2 = StringUtil.getString(context, R.string.title_choose_a_level_of_collection),
                titleBackButton = StringUtil.getString(context, R.string.btn_title_back),
                adapter = getLevelsAdapter(),
                alignRecyclerViewMode = Gravity.CENTER_VERTICAL,
                columnSpan = 3,
                dividerDecoration = getGridDivier(3))

        putBundle(
                index = SEARCH_INDEX.INDEX_INSTRUCTORS,
                title1 = StringUtil.getString(context, R.string.title_instructor),
                title2 = "",
                titleBackButton = StringUtil.getString(context, R.string.btn_title_back_to_search),
                adapter = getInstructorsAdapter(),
                alignRecyclerViewMode = Gravity.FILL_VERTICAL,
                columnSpan = 3,
                dividerDecoration = getGridDivier(3, R.dimen.size_divider))

        putBundle(
                index = SEARCH_INDEX.INDEX_COLLECTIONS_OF_INSTRUCTOR,
                title1 = StringUtil.getString(context, R.string.title_instructor),
                title2 = StringUtil.getString(context, R.string.title_choose_collection_of_),
                titleBackButton = StringUtil.getString(context, R.string.btn_title_back_to_search),
                adapter = getCollectionsAdapter(),
                alignRecyclerViewMode = Gravity.FILL_VERTICAL,
                columnSpan = 3,
                dividerDecoration = getGridDivier(3))

        putBundle(
                index = SEARCH_INDEX.INDEX_DURATIONS,
                title1 = StringUtil.getString(context, R.string.title_duration),
                title2 = "",
                titleBackButton = StringUtil.getString(context, R.string.btn_title_back_to_search),
                adapter = getDurationsAdapter(),
                alignRecyclerViewMode = Gravity.FILL_VERTICAL,
                columnSpan = 3,
                dividerDecoration = getGridDivier(3))

        putBundle(
                index = SEARCH_INDEX.INDEX_COLLECTIONS_OF_DURATION,
                title1 = StringUtil.getString(context, R.string.title_duration),
                title2 =  StringUtil.getString(context, R.string.title_choose_collection_of_),
                titleBackButton = StringUtil.getString(context, R.string.btn_title_back_to_search),
                adapter = getCollectionsAdapter(),
                alignRecyclerViewMode = Gravity.FILL_VERTICAL,
                columnSpan = 3,
                dividerDecoration = getGridDivier(3))

        putBundle(
                index = SEARCH_INDEX.INDEX_LEVELS,
                title1 = StringUtil.getString(context, R.string.title_level),
                title2 = "",
                titleBackButton = StringUtil.getString(context, R.string.btn_title_back_to_search),
                adapter = getLevelsAdapter(),
                alignRecyclerViewMode = Gravity.CENTER_VERTICAL,
                columnSpan = 3,
                dividerDecoration = getGridDivier(3))

        putBundle(
                index = SEARCH_INDEX.INDEX_COLLECTIONS_OF_LEVEL,
                title1 = StringUtil.getString(context, R.string.title_level),
                title2 =  StringUtil.getString(context, R.string.title_choose_collection_of_),
                titleBackButton = StringUtil.getString(context, R.string.btn_title_back_to_search),
                adapter = getCollectionsAdapter(),
                alignRecyclerViewMode = Gravity.FILL_VERTICAL,
                columnSpan = 3,
                dividerDecoration = getGridDivier(3))
    }

    private fun putBundle(index:Int, title1:String, title2:String, titleBackButton:String,
                          adapter: Any, alignRecyclerViewMode:Int, columnSpan:Int, dividerDecoration: RecyclerView.ItemDecoration?){
        SearchVideosBundle(
                title1, title2, titleBackButton, adapter, alignRecyclerViewMode, columnSpan, dividerDecoration
        ).let {
            map.put(index, it)
        }
    }

    private fun getGridDivier(columnSpan: Int, @DimenRes sizeRes:Int = R.dimen.size_divider, @DrawableRes drawableRes: Int?=null) : ItemDecorationGridVertical? {
        val context = context?: return null
        val dividerDrawable = if(drawableRes!=null) ContextCompat.getDrawable(context, drawableRes) else null
        val size = context.resources.getDimensionPixelSize(sizeRes)
        return ItemDecorationGridVertical(dividerDrawable, size, columnSpan)
    }

    private fun getMainOptionsAdapter() : SearchVideosOptionsAdapter{
        val adapter = SearchVideosOptionsAdapter(clickItemMainOption)
        adapter.setList(ListGenerator.getMainOptions())
        return adapter
    }

    private fun getBrandsAdapter() : ChoosedBrandsAdapter{
        val adapter = ChoosedBrandsAdapter(clickItemChoosedBrand)
        adapter.list = ListGenerator.getBrands()
        return adapter
    }

    private fun getCollectionsAdapter(): ChoosedCollectionsAdapter {
        val adapter = ChoosedCollectionsAdapter(clickItemChoosedCollection)
        adapter.list = ListGenerator.getCollections()
        return adapter
    }

    private fun getDurationsAdapter(): ChoosedDurationsAdapter {
        val adapter = ChoosedDurationsAdapter(clickItemChoosedDuration)
        adapter.list = ListGenerator.getDurations()
        return adapter
    }

    private fun getLevelsAdapter(): ChoosedLevelsAdapter {
        val adapter = ChoosedLevelsAdapter(clickItemChoosedLevel)
        adapter.list = ListGenerator.getLevels()
        return adapter
    }

    private fun getInstructorsAdapter(): ChoosedInstructorsAdapter {
        val adapter = ChoosedInstructorsAdapter(clickItemChoosedInstructor)
        adapter.list = ListGenerator.getInstructors()
        return adapter
    }

    fun getBundle(index:Int) : SearchVideosBundle? = map.get(index)
}