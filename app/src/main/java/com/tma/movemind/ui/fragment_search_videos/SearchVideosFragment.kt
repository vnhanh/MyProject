package com.tma.movemind.ui.fragment_search_videos


import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tma.movemind.R
import com.tma.movemind.base.dagger.ui.BaseFragment
import com.tma.movemind.base.models.*
import com.tma.movemind.base.utils.Constant.TAG_LOG
import com.tma.movemind.base.utils.Util
import com.tma.movemind.ui.fragment_results_search_videos.ResultsSearchVideosFragment
import com.tma.movemind.ui.fragment_search_videos.children.brand.ChoosedBrandsAdapter
import com.tma.movemind.ui.fragment_search_videos.children.collection.ChoosedCollectionsAdapter
import com.tma.movemind.ui.fragment_search_videos.children.duration.ChoosedDurationsAdapter
import com.tma.movemind.ui.fragment_search_videos.children.instructor.ChoosedInstructorsAdapter
import com.tma.movemind.ui.fragment_search_videos.children.instructor.InfoInstructorRepresentationDialogFragment
import com.tma.movemind.ui.fragment_search_videos.children.level.ChoosedLevelsAdapter
import com.tma.movemind.ui.fragment_search_videos.children.main_options.SEARCH_OPTION
import com.tma.movemind.ui.fragment_search_videos.children.main_options.SearchVideosOptionsAdapter
import com.tma.movemind.ui.fragment_search_videos.helper_installer.SEARCH_INDEX
import com.tma.movemind.ui.fragment_search_videos.helper_installer.SEARCH_INDEX.INDEX_MAIN_OPTIONS
import com.tma.movemind.ui.fragment_search_videos.helper_installer.SearchProgressInstaller
import com.tma.movemind.ui.fragment_search_videos.helper_installer.SearchVideosBundle
import com.tma.movemind.ui.fragment_search_videos.mvp.ISearchVideosContract
import kotlinx.android.synthetic.main.fragment_search_videos.*
import javax.inject.Inject


class SearchVideosFragment : BaseFragment(), ISearchVideosContract.View {

    @Inject lateinit var presenter:ISearchVideosContract.Presenter

    private var progressIndex = INDEX_MAIN_OPTIONS // default value

    override fun getViewContext(): Context? = context

    override fun showUIOnItemCLicked(bundle: SearchVideosBundle?) {
        if(bundle == null)
            forwardToSearchResultScreen()
        else{
            showUIOnBundle(bundle)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_videos, container, false)
    }

    override fun onStart() {
        super.onStart()

        if(context != null){
            bundleInstaller = SearchProgressInstaller(context!!).also {
                it.clickItemMainOption = clickItemMainOption
                it.clickItemChoosedBrand = clickItemChoosedBrand
                it.clickItemChoosedCollection = clickItemChoosedCollection
                it.clickItemChoosedDuration = clickItemChoosedDuration
                it.clickItemChoosedInstructor = clickItemChoosedInstructor
                it.clickItemChoosedLevel = clickItemChoosedLevel

                it.init()
                presenter.installer = it
            }
        }

        initValues()

        setupUI()
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach(this)
    }

    override fun onPause() {
        presenter.onDetach()
        super.onPause()
    }

    private fun initValues() {
        margin = resources.getDimensionPixelSize(R.dimen.margin)
        marginx2 = resources.getDimensionPixelSize(R.dimen.marginx2)
        marginx3 = resources.getDimensionPixelSize(R.dimen.marginx3)
    }

    private fun setupUI() {

        btn_back_screen.setOnClickListener {
            presenter.onBackPressed()
        }
    }

    private fun stretchRecyclerViewVerticalIfCenteringVertical(){
        if(view is ConstraintLayout){

            val params = recyclerview_search_videos_options.layoutParams as ConstraintLayout.LayoutParams

            if(params.height == ConstraintLayout.LayoutParams.WRAP_CONTENT){
                params.height  = 0
                recyclerview_search_videos_options.requestLayout()
            }
        }
    }

    private fun centerRecyclerViewVerticalIfStretching(){
        if(view is ConstraintLayout){
            val params = recyclerview_search_videos_options.layoutParams as ConstraintLayout.LayoutParams
            if(params.height == 0){
                params.height  = ConstraintLayout.LayoutParams.WRAP_CONTENT
                recyclerview_search_videos_options.requestLayout()
            }
        }
    }

    private var itemDecoration:RecyclerView.ItemDecoration?=null

    private fun showsUIOnItemClicked(index:Int){
        progressIndex = index
        val bundle = bundleInstaller?.getBundle(index) ?: return
        showUIOnBundle(bundle)
     }

    private fun showUIOnBundle(bundle:SearchVideosBundle){

        bundle.title1ResId.let {
            tv_title.text = it
        }

        var title2 = bundle.title2
        if(bundle.title2Buffer != null){
            title2 = String.format(title2, bundle.title2Buffer)
        }

        if("".equals(title2)){
            if(tv_search_videos_title2.visibility != View.GONE)
                tv_search_videos_title2.visibility = View.GONE
        }else{
            if(tv_search_videos_title2.visibility != View.VISIBLE)
                tv_search_videos_title2.visibility = View.VISIBLE
            tv_search_videos_title2.text = title2
        }

        bundle.titleBackButton.let {
            if("".equals(it)){
                btn_back_screen.visibility = View.GONE
            }else{
                btn_back_screen.visibility = View.VISIBLE
                btn_back_screen.text = it
            }
        }

        val alignMode = bundle.alignRecyclerViewMode

        when(alignMode){
            Gravity.CENTER_VERTICAL -> {
                centerRecyclerViewVerticalIfStretching()
            }

            Gravity.FILL_VERTICAL -> {
                stretchRecyclerViewVerticalIfCenteringVertical()
            }
        }

        if(itemDecoration != null)
            recyclerview_search_videos_options.removeItemDecoration(itemDecoration)

        bundle.dividerDecoration?.also {
            itemDecoration = it
            recyclerview_search_videos_options.addItemDecoration(itemDecoration)
        }

        recyclerview_search_videos_options.layoutManager = GridLayoutManager(context, bundle.columnSpan)

        val adapter = bundle.adapter as RecyclerView.Adapter<*>
        recyclerview_search_videos_options.adapter = adapter
    }

    private val clickItemMainOption = object : SearchVideosOptionsAdapter.OnItemClickListener{
        override fun onClick(value: SEARCH_OPTION) {

            presenter.onChooseItem(SEARCH_INDEX.INDEX_MAIN_OPTIONS, value.value)
        }
    }

    private var bundleInstaller: SearchProgressInstaller?=null

    private val clickItemChoosedBrand = object : ChoosedBrandsAdapter.OnItemClickListener{
        override fun onClick(brand: Brand?) {
            presenter.onChooseItem(SEARCH_INDEX.INDEX_BRANDS, brand)
        }
    }

    private val clickItemChoosedCollection = object : ChoosedCollectionsAdapter.OnItemClickListener{
        override fun onClick(value: GymCollection?) {
            presenter.onChooseItem(SEARCH_INDEX.INDEX_COLLECTIONS, value)
        }
    }

    private val clickItemChoosedDuration = object : ChoosedDurationsAdapter.OnItemClickListener{
        override fun onClick(value: Duration?) {
            presenter.onChooseItem(SEARCH_INDEX.INDEX_DURATIONS, value)
        }
    }

    private val clickItemChoosedInstructor = object : ChoosedInstructorsAdapter.OnItemClickListener{
        override fun onClickItem(instructor: Instructor) {

            presenter.onChooseItem(SEARCH_INDEX.INDEX_INSTRUCTORS, instructor)
        }

        override fun onClickShowInfo(instructor: Instructor) {
            val manager = childFragmentManager ?: return
            val ft = manager.beginTransaction()
            val tag = InfoInstructorRepresentationDialogFragment.TAG
            val fragment = manager.findFragmentByTag(tag)
            if(fragment != null){
                ft.remove(fragment)
            }
            ft.commit()
            val newFragment = InfoInstructorRepresentationDialogFragment.getInstance(instructor.fullName)
            newFragment.show(manager, tag)
        }
    }

    private val clickItemChoosedLevel = object : ChoosedLevelsAdapter.OnItemClickListener{
        override fun onClick(level: Level?) {
            presenter.onChooseItem(SEARCH_INDEX.INDEX_LEVELS, level)
        }
    }

    private fun forwardToSearchResultScreen(){
        val currentTag = TAG
        val newTag = ResultsSearchVideosFragment.TAG
        val newFragment = ResultsSearchVideosFragment.getInstance()
        Util.addOrShowFragment(fragmentManager, newFragment, newTag, currentTag, R.id.frame_layout_control)
    }


    companion object {
        val TAG = "SearchVideosFragment"

        var margin = 0
        var marginx2 = 0
        var marginx3 = 0

        private var INSTANCE:SearchVideosFragment?=null

        fun getInstance():SearchVideosFragment{
            if(INSTANCE == null){
                INSTANCE = SearchVideosFragment()
            }

            return INSTANCE!!
        }
    }
}
